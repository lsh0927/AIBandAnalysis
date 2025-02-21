// ProjectDetail.tsx
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useAuth } from '../AuthContext';

interface Project {
  id: number;
  title: string;
  genre: string;
  // 필요한 다른 필드도 추가
}

const ProjectDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const { token } = useAuth();
  const [project, setProject] = useState<Project | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchProject = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/project/${id}`, {
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
    </div>
  );
};

export default ProjectDetail;
