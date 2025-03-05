<<<<<<< HEAD
=======

// // components/ProjectDetail.tsx
// import React, { useEffect, useState } from 'react';
// import { useParams } from 'react-router-dom';
// import { useAuth } from '../AuthContext';

// interface Project {
//   id: number;
//   title: string;
//   genre: string;
//   referenceFileUrl?: string;
//   performanceFileUrl?: string;
// }

// const ProjectDetail: React.FC = () => {
//   const { id } = useParams<{ id: string }>();
//   const { token } = useAuth();
//   const [project, setProject] = useState<Project | null>(null);
//   const [error, setError] = useState<string | null>(null);
//   const backendUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';

//   // URL에서 파일 이름 추출 함수
//   const extractFileName = (url?: string): string => {
//     if (!url) return '미등록';
//     const parts = url.split('/');
//     return parts[parts.length - 1];
//   };

//   useEffect(() => {
//     const fetchProject = async () => {
//       try {
//         const response = await fetch(`${backendUrl}/api/project/${id}`, {
//           headers: {
//             'Authorization': `Bearer ${token}`
//           }
//         });
//         if (!response.ok) {
//           throw new Error('프로젝트 정보를 가져올 수 없습니다.');
//         }
//         const data = await response.json();
//         setProject(data);
//       } catch (err: any) {
//         setError(err.message);
//       }
//     };

//     fetchProject();
//   }, [id, token, backendUrl]);

//   const handleAnalyze = async () => {
//     try {
//       const response = await fetch(`${backendUrl}/api/analyze/${id}`, {
//         method: 'POST',
//         headers: {
//           'Authorization': `Bearer ${token}`
//         }
//       });
//       if (!response.ok) {
//         throw new Error('분석 실패');
//       }
//       alert('분석 요청이 성공적으로 처리되었습니다.');
//     } catch (err: any) {
//       alert(err.message);
//     }
//   };

//   const handleViewResults = () => {
//     window.location.href = `/project/${id}/results`;
//   };

//   if (error) {
//     return <div className="p-4 text-red-500">{error}</div>;
//   }

//   if (!project) {
//     return <div className="p-4">Loading...</div>;
//   }

//   return (
//     <div className="container mx-auto p-8 bg-white rounded-lg shadow-lg animate-fadeInUp">
//       <h2 className="text-3xl font-bold mb-4 text-black">프로젝트 상세 정보</h2>
//       <p className="mb-2"><strong>제목:</strong> {project.title}</p>
//       <p className="mb-2"><strong>장르:</strong> {project.genre}</p>
//       <p className="mb-2">
//         <strong>원본 파일:</strong> {extractFileName(project.referenceFileUrl)}
//       </p>
//       <p className="mb-4">
//         <strong>합주 파일:</strong> {extractFileName(project.performanceFileUrl)}
//       </p>
      
//       <div className="flex space-x-4">
//         <button
//           onClick={handleAnalyze}
//           className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700 transition"
//         >
//           분석하기
//         </button>
//         <button
//           onClick={handleViewResults}
//           className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
//         >
//           결과 보기
//         </button>
//       </div>
//     </div>
//   );
// };

// export default ProjectDetail;
>>>>>>> 61ded68 (again)
// ProjectDetail.tsx
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useAuth } from '../AuthContext';

interface Project {
  id: number;
  title: string;
  genre: string;
<<<<<<< HEAD
  // 필요한 다른 필드도 추가
=======

  // 백엔드에서 추가하도록 한 필드들:
  referenceFileName?: string;
  performanceFileName?: string;
  referenceFileUrl?: string;
  performanceFileUrl?: string;
>>>>>>> 61ded68 (again)
}

const ProjectDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const { token } = useAuth();
  const [project, setProject] = useState<Project | null>(null);
  const [error, setError] = useState<string | null>(null);
<<<<<<< HEAD
=======
  const backendUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';
>>>>>>> 61ded68 (again)

  useEffect(() => {
    const fetchProject = async () => {
      try {
<<<<<<< HEAD
        const response = await fetch(`http://localhost:8080/api/project/${id}`, {
=======
        const response = await fetch(`${backendUrl}/api/project/${id}`, {
>>>>>>> 61ded68 (again)
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
<<<<<<< HEAD
  }, [id, token]);

  if (error) {
    return <div className="text-red-500">{error}</div>;
  }

  if (!project) {
    return <div>Loading...</div>;
  }

  return (
    <div className="max-w-xl mx-auto p-4">
      <h2 className="text-2xl font-bold mb-4">프로젝트 상세 정보</h2>
      <p><strong>제목:</strong> {project.title}</p>
      <p><strong>장르:</strong> {project.genre}</p>
      {/* 추가 메타데이터 표시 */}
=======
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
>>>>>>> 61ded68 (again)
    </div>
  );
};

export default ProjectDetail;
