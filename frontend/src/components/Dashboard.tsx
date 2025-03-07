
// Dashboard.tsx// Dashboard.tsx
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../AuthContext';  // 위에서 만든 useAuth
import ProjectForm from './ProjectForm';
import ProjectList from './ProjectList';

const Dashboard: React.FC = () => {
  const { logout, user } = useAuth();
  const navigate = useNavigate();
  const [refreshList, setRefreshList] = React.useState(false);

  // 로그아웃 버튼 클릭 시
  const handleLogout = async () => {
    try {
      // 백엔드 로그아웃 API 호출
      await fetch('/api/auth/logout', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      // 클라이언트 측 로그아웃 처리
      logout();
      // 로그인 페이지로 이동
      navigate('/login');
    } catch (error) {
      console.error('Logout failed:', error);
      // 실패 시에도 일단 클라이언트 로그아웃 처리
      logout();
      navigate('/login');
    }
  };

  return (
    <div className="container mx-auto p-8 min-h-screen bg-gray-100 rounded-lg shadow">
      <header className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold text-black">
          {user ? `${user.name}님, 안녕하세요!` : "Dashboard"}
        </h1>
        <button
          onClick={handleLogout}
          className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600 transition"
        >
          로그아웃
        </button>
      </header>

      <div className="space-y-8">
        <p className="text-xl text-gray-700">Band Analysis에 오신 것을 환영합니다!</p>
        <ProjectForm onProjectCreated={() => setRefreshList(!refreshList)} />
        <ProjectList refreshTrigger={refreshList} />
      </div>
    </div>
  );
};

export default Dashboard;
