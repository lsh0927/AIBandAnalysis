import React, { useState, ChangeEvent, FormEvent } from 'react';
import toast from 'react-hot-toast';
import { useAuth } from '../AuthContext';

interface ProjectFormProps {
  onProjectCreated?: () => void; 
}

const ProjectForm: React.FC<ProjectFormProps> = ({ onProjectCreated }) => {
  const { token, user } = useAuth();

  // 입력 상태
  const [title, setTitle] = useState('');
  const [genre, setGenre] = useState('');

  // 파일 선택 상태
  const [referenceFile, setReferenceFile] = useState<File | null>(null);
  const [performanceFile, setPerformanceFile] = useState<File | null>(null);

  // 에러 메시지
  const [error, setError] = useState<string | null>(null);

  // 현재 로그인된 사용자 정보
  const userId = user ? user.id : null;

  // 백엔드 URL (도커 환경 등에서 VITE_BACKEND_URL 사용)
  const backendUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';

  // 파일 선택 핸들러

  const handleFileChange = (
    e: ChangeEvent<HTMLInputElement>,
    setFile: React.Dispatch<React.SetStateAction<File | null>>
  ) => {
    if (e.target.files && e.target.files.length > 0) {
      setFile(e.target.files[0]);
    }
  };

  // 파일 업로드 함수 (프로젝트 ID와 파일 타입을 함께 받음)
  const uploadFile = async (file: File, fileType: 'reference' | 'performance', projectId: number) => {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('type', fileType);
    // ★ projectId를 함께 전송해야 백엔드가 인식합니다.
    formData.append('projectId', String(projectId));

    const response = await fetch(`${backendUrl}/api/audio/upload`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`
        // FormData 사용 시 Content-Type은 자동으로 설정되므로 직접 지정하지 않습니다.
      },
      body: formData
    });

    if (!response.ok) {
      throw new Error(`${fileType} 파일 업로드 실패`);
    }
    return await response.json();
  };

  // 폼 제출 핸들러
  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError(null);

    if (!userId) {
      setError('사용자 정보가 없습니다.');
      return;
    }

    try {
      // 1. 프로젝트 생성 API
      const projectResponse = await fetch(`${backendUrl}/api/project?userId=${userId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ title, genre })
      });

      if (!projectResponse.ok) {
        throw new Error('프로젝트 생성 실패');
      }
      const projectData = await projectResponse.json();
      const projectId = projectData.id;

      // 프로젝트 생성 성공 알림
      toast.success("프로젝트가 성공적으로 생성되었습니다.");

      // 2. 파일 업로드 (선택된 파일이 있을 경우)
      if (referenceFile) {
        await uploadFile(referenceFile, 'reference', projectId);
      }
      if (performanceFile) {
        await uploadFile(performanceFile, 'performance', projectId);
      }

      // 폼 리셋
      setTitle('');
      setGenre('');
      setReferenceFile(null);
      setPerformanceFile(null);

      // 부모 컴포넌트에 알림 (목록 재조회 등)
      if (onProjectCreated) {
        onProjectCreated();
      }
    } catch (err: any) {
      setError(err.message);
      toast.error(err.message);
      console.error('Error during project creation:', err);
    }
  };

  return (
    <div className="bg-white rounded-lg shadow-lg p-6 mb-8 animate-fadeInUp">
      <h2 className="text-2xl font-bold mb-4 text-black">프로젝트 생성</h2>
      {error && <p className="text-red-500 mb-2">{error}</p>}

      <form onSubmit={handleSubmit}>
        {/* 프로젝트 제목 */}
        <div className="mb-4">
          <label className="block text-gray-700 font-medium mb-1">프로젝트 제목</label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="w-full border border-gray-300 p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
            required
          />
        </div>

        {/* 장르 */}
        <div className="mb-4">
          <label className="block text-gray-700 font-medium mb-1">장르</label>
          <input
            type="text"
            value={genre}
            onChange={(e) => setGenre(e.target.value)}
            className="w-full border border-gray-300 p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
            required
          />
        </div>

        {/* 레퍼런스 파일 (원곡) */}
        <div className="mb-4">
          <label className="block text-gray-700 font-medium mb-1">레퍼런스 파일 (원곡)</label>
          <input
            type="file"
            accept="audio/*"
            onChange={(e) => handleFileChange(e, setReferenceFile)}
            className="w-full"
            required
          />
          {referenceFile && (
            <p className="mt-1 text-sm text-gray-500">선택된 파일: {referenceFile.name}</p>
          )}
        </div>

        {/* 퍼포먼스 파일 (연주) */}
        <div className="mb-6">
          <label className="block text-gray-700 font-medium mb-1">퍼포먼스 파일 (연주)</label>
          <input
            type="file"
            accept="audio/*"
            onChange={(e) => handleFileChange(e, setPerformanceFile)}
            className="w-full"
            required
          />
          {performanceFile && (
            <p className="mt-1 text-sm text-gray-500">선택된 파일: {performanceFile.name}</p>
          )}
        </div>

        {/* 프로젝트 생성 버튼 */}
        <button
          type="submit"
          className="w-full py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
        >
          프로젝트 생성 및 파일 등록
        </button>
      </form>
    </div>
  );
};

export default ProjectForm;
