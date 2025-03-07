
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useAuth } from '../AuthContext';

interface Project {
  id: number;
  title: string;
  genre: string;

  // 백엔드에서 추가하도록 한 필드들:
  referenceFileName?: string;
  performanceFileName?: string;
  referenceFileUrl?: string;
  performanceFileUrl?: string;
}

const ProjectDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const { token } = useAuth();
  const [project, setProject] = useState<Project | null>(null);
  const [error, setError] = useState<string | null>(null);
  const backendUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';

  useEffect(() => {
    const fetchProject = async () => {
      try {
        const response = await fetch(`${backendUrl}/api/project/${id}`, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        if (!response.ok) {
          throw new Error('프로젝트 정보를 가져올 수 없습니다.');
        }
        const data = await response.json();
        setProject(data);
      } catch (err: any) {
        setError(err.message);
      }
    };

    fetchProject();
  }, [id, token, backendUrl]);

  const handleAnalyze = async () => {
    try {
      const response = await fetch(`${backendUrl}/api/analyze/${id}`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      if (!response.ok) {
        throw new Error('분석 실패');
      }
      alert('분석 요청이 성공적으로 처리되었습니다.');
    } catch (err: any) {
      alert(err.message);
    }
  };

  const handleViewResults = () => {
    window.location.href = `/project/${id}/results`;
  };

  if (error) {
    return <div className="p-4 text-red-500">{error}</div>;
  }

  if (!project) {
    return <div className="p-4">Loading...</div>;
  }

  return (
    <div className="container mx-auto p-8 bg-white rounded-lg shadow-lg animate-fadeInUp">
      <h2 className="text-3xl font-bold mb-4 text-black">프로젝트 상세 정보</h2>
      <p className="mb-2"><strong>제목:</strong> {project.title}</p>
      <p className="mb-2"><strong>장르:</strong> {project.genre}</p>

      {/* 원본 파일 정보 */}
      <div className="mb-4">
        <p>
          <strong>원본 파일 이름:</strong> {project.referenceFileName || '미등록'}
        </p>
        {project.referenceFileUrl ? (
          <audio controls src={project.referenceFileUrl}>
            {/* 오디오 태그를 지원하지 않는 브라우저 대비 */}
            Your browser does not support the audio element.
          </audio>
        ) : (
          <p className="text-gray-500">오디오 파일이 없습니다.</p>
        )}
      </div>

      {/* 합주 파일 정보 */}
      <div className="mb-4">
        <p>
          <strong>합주 파일 이름:</strong> {project.performanceFileName || '미등록'}
        </p>
        {project.performanceFileUrl ? (
          <audio controls src={project.performanceFileUrl}>
            Your browser does not support the audio element.
          </audio>
        ) : (
          <p className="text-gray-500">오디오 파일이 없습니다.</p>
        )}
      </div>

      <div className="flex space-x-4">
        <button
          onClick={handleAnalyze}
          className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700 transition"
        >
          분석하기
        </button>
        <button
          onClick={handleViewResults}
          className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
        >
          결과 보기
        </button>
      </div>
    </div>
  );
};

export default ProjectDetail;
