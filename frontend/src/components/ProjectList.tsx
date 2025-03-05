
// components/ProjectList.tsx
import React, { useEffect, useState } from 'react';
import { useAuth } from '../AuthContext';
import { Link } from 'react-router-dom';

interface Project {
  id: number;
  title: string;
  genre: string;
  // 필요한 경우 추가 필드를 정의합니다.
}

interface ProjectListProps {
  refreshTrigger?: boolean;  // 외부에서 목록 재조회 트리거
}

const ProjectList: React.FC<ProjectListProps> = ({ refreshTrigger }) => {
  const { token, user } = useAuth();
  const [projects, setProjects] = useState<Project[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  // 'all': 전체 프로젝트, 'mine': 내 프로젝트
  const [filter, setFilter] = useState<'all' | 'mine'>('all');

  const backendUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';

  useEffect(() => {
    const fetchProjects = async () => {
      setIsLoading(true);
      setError(null);

      if (filter === 'mine' && !user) {
        setError('로그인이 필요합니다.');
        setIsLoading(false);
        return;
      }

      try {
        const queryParam = filter === 'mine' ? `?userId=${user?.id}` : '';
        const response = await fetch(`${backendUrl}/api/project${queryParam}`, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        if (!response.ok) {
          throw new Error('프로젝트 목록 조회 실패');
        }
        const data = await response.json();
        setProjects(data);
      } catch (err: any) {
        setError(err.message);
      } finally {
        setIsLoading(false);
      }
    };

    fetchProjects();
  }, [filter, user, token, backendUrl, refreshTrigger]);

  return (
    <div className="bg-white p-4 rounded shadow">
      <h2 className="text-xl font-bold mb-4">프로젝트 목록</h2>

      {/* 필터 선택 */}
      <div className="mb-4">
        <label htmlFor="filterSelect" className="mr-2">프로젝트 필터:</label>
        <select
          id="filterSelect"
          value={filter}
          onChange={(e) => setFilter(e.target.value as 'all' | 'mine')}
          className="border border-gray-300 rounded p-1"
        >
          <option value="all">전체 프로젝트</option>
          <option value="mine">내 프로젝트</option>
        </select>
      </div>

      {isLoading && <p>로딩 중...</p>}
      {error && <p className="text-red-500">{error}</p>}

      <ul className="space-y-2">
        {projects.map((project) => (
          <li key={project.id} className="p-2 border rounded hover:bg-gray-50">
            <span className="font-semibold">{project.title}</span>
            {' '}(장르: {project.genre})
            {/* "자세히 보기" 링크 */}
            <Link to={`/project/${project.id}`} className="ml-2 text-blue-600 underline">
              자세히 보기
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ProjectList;
