// src/components/Dashboard.tsx
import React from 'react';
import { useNavigate } from "react-router-dom";
import { useAuth } from "../AuthContext";
import ProjectForm from './ProjectForm'; // 새로 만든 ProjectForm 컴포넌트 import

const Dashboard: React.FC = () => {
  const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await fetch('/api/auth/logout', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      logout();
      navigate('/login');
    } catch (error) {
      console.error('Logout failed:', error);
      logout();
      navigate('/login');
    }
  };

  return (
    <div className="p-8">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold mb-4">Dashboard</h1>
        <button 
          onClick={handleLogout}
          className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
        >
          로그아웃
        </button>
      </div>
      <p>Welcome to Band Analysis!</p>
      {/* 여기에 프로젝트 생성 폼 추가 */}
      <ProjectForm />
    </div>
  );
};

export default Dashboard;
