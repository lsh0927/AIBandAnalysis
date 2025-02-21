// // import React, { useState, ChangeEvent, FormEvent } from 'react';
// // import { useAuth } from '../AuthContext';
// // import { useNavigate } from 'react-router-dom';

// // const ProjectForm: React.FC = () => {
// //   const { token } = useAuth();
// //   const navigate = useNavigate();

// //   // 프로젝트 관련 상태
// //   const [title, setTitle] = useState('');
// //   const [genre, setGenre] = useState('');
// //   // 파일 선택 상태
// //   const [referenceFile, setReferenceFile] = useState<File | null>(null);
// //   const [performanceFile, setPerformanceFile] = useState<File | null>(null);
// //   const [error, setError] = useState<string | null>(null);

// //   // 예시: localStorage나 다른 방식으로 userId를 가져옴.
// //   // 실제 애플리케이션에서는 인증 정보를 통해 userId를 가져와야 함.
// //   const userId = localStorage.getItem('userId') || '1';

// //   // 파일 input 핸들러
// //   const handleFileChange = (
// //     e: ChangeEvent<HTMLInputElement>,
// //     setFile: React.Dispatch<React.SetStateAction<File | null>>
// //   ) => {
// //     if (e.target.files && e.target.files.length > 0) {
// //       setFile(e.target.files[0]);
// //     }
// //   };

// //   // 폼 제출 핸들러
// //   const handleSubmit = async (e: FormEvent) => {
// //     e.preventDefault();
// //     setError(null);

// //     try {
// //       // 1. 프로젝트 생성 API 호출 (userId 쿼리 파라미터 포함)
// //       const projectResponse = await fetch(`http://localhost:8080/api/project?userId=${userId}`, {
// //         method: 'POST',
// //         headers: {
// //           'Content-Type': 'application/json',
// //           'Authorization': `Bearer ${token}`
// //         },
// //         body: JSON.stringify({ title, genre })
// //       });

// //       if (!projectResponse.ok) {
// //         throw new Error('프로젝트 생성 실패');
// //       }
// //       const projectData = await projectResponse.json();
// //       const projectId = projectData.id;

// //       // 2. 선택한 오디오 파일 업로드 (API: /api/audio/upload)
// //       const uploadFile = async (file: File, fileType: 'reference' | 'performance') => {
// //         const formData = new FormData();
// //         formData.append('file', file);
// //         formData.append('type', fileType);

// //         const response = await fetch('http://localhost:8080/api/audio/upload', {
// //           method: 'POST',
// //           headers: {
// //             'Authorization': `Bearer ${token}`
// //             // 'Content-Type'은 FormData 사용 시 브라우저가 자동 설정
// //           },
// //           body: formData
// //         });
// //         if (!response.ok) {
// //           throw new Error(`${fileType} 파일 업로드 실패`);
// //         }
// //         return await response.json(); // 업로드된 AudioFile 객체 반환
// //       };

// //       let referenceAudio: any, performanceAudio: any;
// //       if (referenceFile) {
// //         referenceAudio = await uploadFile(referenceFile, 'reference');
// //       }
// //       if (performanceFile) {
// //         performanceAudio = await uploadFile(performanceFile, 'performance');
// //       }

// //       // 3. (선택 사항) 파일을 프로젝트에 연결하는 추가 API 호출

// //       // 최종적으로 대시보드로 이동
// //       navigate('/dashboard');
// //     } catch (err: any) {
// //       setError(err.message);
// //       console.error('Error during project creation:', err);
// //     }
// //   };

// //   return (
// //     <div className="max-w-xl mx-auto p-4">
// //       <h2 className="text-xl font-bold mb-4">프로젝트 생성</h2>
// //       {error && <p className="text-red-500 mb-2">{error}</p>}
// //       <form onSubmit={handleSubmit}>
// //         <div className="mb-4">
// //           <label className="block mb-1 font-medium">프로젝트 제목</label>
// //           <input
// //             type="text"
// //             value={title}
// //             onChange={(e) => setTitle(e.target.value)}
// //             className="w-full border border-gray-300 p-2 rounded"
// //             required
// //           />
// //         </div>
// //         <div className="mb-4">
// //           <label className="block mb-1 font-medium">장르</label>
// //           <input
// //             type="text"
// //             value={genre}
// //             onChange={(e) => setGenre(e.target.value)}
// //             className="w-full border border-gray-300 p-2 rounded"
// //             required
// //           />
// //         </div>
// //         <div className="mb-4">
// //           <label className="block mb-1 font-medium">레퍼런스 파일 (원곡)</label>
// //           <input
// //             type="file"
// //             accept="audio/*"
// //             onChange={(e) => handleFileChange(e, setReferenceFile)}
// //             className="w-full"
// //             required
// //           />
// //         </div>
// //         <div className="mb-4">
// //           <label className="block mb-1 font-medium">퍼포먼스 파일 (연주)</label>
// //           <input
// //             type="file"
// //             accept="audio/*"
// //             onChange={(e) => handleFileChange(e, setPerformanceFile)}
// //             className="w-full"
// //             required
// //           />
// //         </div>
// //         <button
// //           type="submit"
// //           className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
// //         >
// //           프로젝트 생성 및 파일 등록
// //         </button>
// //       </form>
// //     </div>
// //   );
// // };

// // export default ProjectForm;


// // // ProjectForm.tsx
// // import React, { useState, ChangeEvent, FormEvent } from 'react';
// // import { useAuth } from '../AuthContext';
// // import { useNavigate } from 'react-router-dom';

// // const ProjectForm: React.FC = () => {
// //   const { token, user } = useAuth();
// //   const navigate = useNavigate();

// //   // 프로젝트 관련 상태
// //   const [title, setTitle] = useState('');
// //   const [genre, setGenre] = useState('');
// //   // 파일 선택 상태
// //   const [referenceFile, setReferenceFile] = useState<File | null>(null);
// //   const [performanceFile, setPerformanceFile] = useState<File | null>(null);
// //   const [error, setError] = useState<string | null>(null);

// //   // userId는 AuthContext의 user에서 가져옵니다.
// //   const userId = user ? user.id : null;

// //   // 파일 input 핸들러
// //   const handleFileChange = (
// //     e: ChangeEvent<HTMLInputElement>,
// //     setFile: React.Dispatch<React.SetStateAction<File | null>>
// //   ) => {
// //     if (e.target.files && e.target.files.length > 0) {
// //       setFile(e.target.files[0]);
// //     }
// //   };

// //   // 폼 제출 핸들러
// //   const handleSubmit = async (e: FormEvent) => {
// //     e.preventDefault();
// //     setError(null);

// //     if (!userId) {
// //       setError('사용자 정보가 없습니다.');
// //       return;
// //     }

// //     try {
// //       // 1. 프로젝트 생성 API 호출 (userId 쿼리 파라미터 포함)
// //       const projectResponse = await fetch(`http://localhost:8080/api/project?userId=${userId}`, {
// //         method: 'POST',
// //         headers: {
// //           'Content-Type': 'application/json',
// //           'Authorization': `Bearer ${token}`
// //         },
// //         body: JSON.stringify({ title, genre })
// //       });

// //       if (!projectResponse.ok) {
// //         throw new Error('프로젝트 생성 실패');
// //       }
// //       const projectData = await projectResponse.json();
// //       const projectId = projectData.id;

// //       // 2. 선택한 오디오 파일 업로드 (API: /api/audio/upload)
// //       const uploadFile = async (file: File, fileType: 'reference' | 'performance') => {
// //         const formData = new FormData();
// //         formData.append('file', file);
// //         formData.append('type', fileType);

// //         const response = await fetch('http://localhost:8080/api/audio/upload', {
// //           method: 'POST',
// //           headers: {
// //             'Authorization': `Bearer ${token}`
// //             // 'Content-Type'은 FormData 사용 시 브라우저가 자동 설정
// //           },
// //           body: formData
// //         });
// //         if (!response.ok) {
// //           throw new Error(`${fileType} 파일 업로드 실패`);
// //         }
// //         return await response.json(); // 업로드된 AudioFile 객체 반환
// //       };

// //       let referenceAudio: any, performanceAudio: any;
// //       if (referenceFile) {
// //         referenceAudio = await uploadFile(referenceFile, 'reference');
// //       }
// //       if (performanceFile) {
// //         performanceAudio = await uploadFile(performanceFile, 'performance');
// //       }

// //       // 3. (선택 사항) 프로젝트에 업로드된 파일 연결 API 호출
// //       // 예를 들어, PATCH 요청으로 파일 정보를 프로젝트에 업데이트할 수 있습니다.

// //       // 최종적으로 대시보드로 이동
// //       navigate('/dashboard');
// //     } catch (err: any) {
// //       setError(err.message);
// //       console.error('Error during project creation:', err);
// //     }
// //   };

// //   return (
// //     <div className="max-w-xl mx-auto p-4">
// //       <h2 className="text-xl font-bold mb-4">프로젝트 생성</h2>
// //       {error && <p className="text-red-500 mb-2">{error}</p>}
// //       <form onSubmit={handleSubmit}>
// //         <div className="mb-4">
// //           <label className="block mb-1 font-medium">프로젝트 제목</label>
// //           <input
// //             type="text"
// //             value={title}
// //             onChange={(e) => setTitle(e.target.value)}
// //             className="w-full border border-gray-300 p-2 rounded"
// //             required
// //           />
// //         </div>
// //         <div className="mb-4">
// //           <label className="block mb-1 font-medium">장르</label>
// //           <input
// //             type="text"
// //             value={genre}
// //             onChange={(e) => setGenre(e.target.value)}
// //             className="w-full border border-gray-300 p-2 rounded"
// //             required
// //           />
// //         </div>
// //         <div className="mb-4">
// //           <label className="block mb-1 font-medium">레퍼런스 파일 (원곡)</label>
// //           <input
// //             type="file"
// //             accept="audio/*"
// //             onChange={(e) => handleFileChange(e, setReferenceFile)}
// //             className="w-full"
// //             required
// //           />
// //         </div>
// //         <div className="mb-4">
// //           <label className="block mb-1 font-medium">퍼포먼스 파일 (연주)</label>
// //           <input
// //             type="file"
// //             accept="audio/*"
// //             onChange={(e) => handleFileChange(e, setPerformanceFile)}
// //             className="w-full"
// //             required
// //           />
// //         </div>
// //         <button
// //           type="submit"
// //           className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
// //         >
// //           프로젝트 생성 및 파일 등록
// //         </button>
// //       </form>
// //     </div>
// //   );
// // };

// // export default ProjectForm;

// // ProjectForm.tsx
// import React, { useState, ChangeEvent, FormEvent } from 'react';
// import { useAuth } from '../AuthContext';
// import { useNavigate } from 'react-router-dom';

// const ProjectForm: React.FC = () => {
//   const { token, user } = useAuth();
//   const navigate = useNavigate();

//   // 프로젝트 관련 상태
//   const [title, setTitle] = useState('');
//   const [genre, setGenre] = useState('');
//   // 파일 선택 상태
//   const [referenceFile, setReferenceFile] = useState<File | null>(null);
//   const [performanceFile, setPerformanceFile] = useState<File | null>(null);
//   const [error, setError] = useState<string | null>(null);

//   // AuthContext에서 user 정보를 사용
//   const userId = user ? user.id : null;

//   const handleFileChange = (
//     e: ChangeEvent<HTMLInputElement>,
//     setFile: React.Dispatch<React.SetStateAction<File | null>>
//   ) => {
//     if (e.target.files && e.target.files.length > 0) {
//       setFile(e.target.files[0]);
//     }
//   };

//   const handleSubmit = async (e: FormEvent) => {
//     e.preventDefault();
//     setError(null);

//     if (!userId) {
//       setError('사용자 정보가 없습니다.');
//       return;
//     }

//     try {
//       // 1. 프로젝트 생성 API 호출 (userId를 쿼리 파라미터로 포함)
//       const projectResponse = await fetch(`http://localhost:8080/api/project?userId=${userId}`, {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json',
//           'Authorization': `Bearer ${token}`
//         },
//         body: JSON.stringify({ title, genre })
//       });

//       if (!projectResponse.ok) {
//         throw new Error('프로젝트 생성 실패');
//       }
//       const projectData = await projectResponse.json();
//       const projectId = projectData.id;

//       // 2. 파일 업로드 로직 (생략: 이미 구현한 코드 그대로 사용)

//       // 3. 프로젝트 생성 성공 후 프로젝트 상세 페이지로 이동
//       navigate(`/project/${projectId}`);
//     } catch (err: any) {
//       setError(err.message);
//       console.error('Error during project creation:', err);
//     }
//   };

//   return (
//     <div className="max-w-xl mx-auto p-4">
//       <h2 className="text-xl font-bold mb-4">프로젝트 생성</h2>
//       {error && <p className="text-red-500 mb-2">{error}</p>}
//       <form onSubmit={handleSubmit}>
//         {/* 프로젝트 제목, 장르, 파일 선택 입력 필드 */}
//         <div className="mb-4">
//           <label className="block mb-1 font-medium">프로젝트 제목</label>
//           <input
//             type="text"
//             value={title}
//             onChange={(e) => setTitle(e.target.value)}
//             className="w-full border border-gray-300 p-2 rounded"
//             required
//           />
//         </div>
//         <div className="mb-4">
//           <label className="block mb-1 font-medium">장르</label>
//           <input
//             type="text"
//             value={genre}
//             onChange={(e) => setGenre(e.target.value)}
//             className="w-full border border-gray-300 p-2 rounded"
//             required
//           />
//         </div>
//         <div className="mb-4">
//           <label className="block mb-1 font-medium">레퍼런스 파일 (원곡)</label>
//           <input
//             type="file"
//             accept="audio/*"
//             onChange={(e) => handleFileChange(e, setReferenceFile)}
//             className="w-full"
//             required
//           />
//         </div>
//         <div className="mb-4">
//           <label className="block mb-1 font-medium">퍼포먼스 파일 (연주)</label>
//           <input
//             type="file"
//             accept="audio/*"
//             onChange={(e) => handleFileChange(e, setPerformanceFile)}
//             className="w-full"
//             required
//           />
//         </div>
//         <button
//           type="submit"
//           className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
//         >
//           프로젝트 생성 및 파일 등록
//         </button>
//       </form>
//     </div>
//   );
// };

// export default ProjectForm;

// ProjectForm.tsx 예시

import React, { useState, ChangeEvent, FormEvent } from 'react';
import { useAuth } from '../AuthContext';
import { useNavigate } from 'react-router-dom';

const ProjectForm: React.FC = () => {
  const { token, user } = useAuth();
  const navigate = useNavigate();

  // 프로젝트 관련 상태
  const [title, setTitle] = useState('');
  const [genre, setGenre] = useState('');
  // 파일 선택 상태
  const [referenceFile, setReferenceFile] = useState<File | null>(null);
  const [performanceFile, setPerformanceFile] = useState<File | null>(null);
  const [error, setError] = useState<string | null>(null);

  // AuthContext에서 user 정보를 사용
  const userId = user ? user.id : null;

  // API 기본 URL: 도커 내부에서는 backend라는 호스트명을 사용합니다.
  const backendUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';

  const handleFileChange = (
    e: ChangeEvent<HTMLInputElement>,
    setFile: React.Dispatch<React.SetStateAction<File | null>>
  ) => {
    if (e.target.files && e.target.files.length > 0) {
      setFile(e.target.files[0]);
    }
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError(null);

    if (!userId) {
      setError('사용자 정보가 없습니다.');
      return;
    }

    try {
      console.log('Submitting project with token:', token, 'and userId:', userId);

      // 1. 프로젝트 생성 API 호출 (userId를 쿼리 파라미터로 포함)
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
      console.log('Project created with id:', projectId);

      // 2. 파일 업로드 함수
      const uploadFile = async (file: File, fileType: 'reference' | 'performance') => {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('type', fileType);

        const response = await fetch(`${backendUrl}/api/audio/upload`, {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}`
          },
          body: formData
        });
        if (!response.ok) {
          throw new Error(`${fileType} 파일 업로드 실패`);
        }
        return await response.json();
      };

      let referenceAudio: any, performanceAudio: any;
      if (referenceFile) {
        referenceAudio = await uploadFile(referenceFile, 'reference');
      }
      if (performanceFile) {
        performanceAudio = await uploadFile(performanceFile, 'performance');
      }

      // 3. 프로젝트 생성 성공 후 프로젝트 상세 페이지로 이동
      navigate(`/project/${projectId}`);
    } catch (err: any) {
      setError(err.message);
      console.error('Error during project creation:', err);
    }
  };

  return (
    <div className="max-w-xl mx-auto p-4">
      <h2 className="text-xl font-bold mb-4">프로젝트 생성</h2>
      {error && <p className="text-red-500 mb-2">{error}</p>}
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label className="block mb-1 font-medium">프로젝트 제목</label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="w-full border border-gray-300 p-2 rounded"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block mb-1 font-medium">장르</label>
          <input
            type="text"
            value={genre}
            onChange={(e) => setGenre(e.target.value)}
            className="w-full border border-gray-300 p-2 rounded"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block mb-1 font-medium">레퍼런스 파일 (원곡)</label>
          <input
            type="file"
            accept="audio/*"
            onChange={(e) => handleFileChange(e, setReferenceFile)}
            className="w-full"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block mb-1 font-medium">퍼포먼스 파일 (연주)</label>
          <input
            type="file"
            accept="audio/*"
            onChange={(e) => handleFileChange(e, setPerformanceFile)}
            className="w-full"
            required
          />
        </div>
        <button
          type="submit"
          className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
        >
          프로젝트 생성 및 파일 등록
        </button>
      </form>
    </div>
  );
};

export default ProjectForm;
