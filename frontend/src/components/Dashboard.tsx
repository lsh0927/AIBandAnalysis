// import React from 'react';

import { useNavigate } from "react-router-dom";
import { useAuth } from "../AuthContext";


// Dashboard.tsx에 로그아웃 버튼 추가
const Dashboard: React.FC = () => {
  const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      // 백엔드 로그아웃 엔드포인트 호출 (선택적)
      await fetch('/api/auth/logout', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      
      // 프론트엔드 로그아웃 처리
      logout();
      navigate('/login');
    } catch (error) {
      console.error('Logout failed:', error);
      // 에러가 발생해도 프론트엔드 로그아웃은 수행
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
    </div>
  );
};

export default Dashboard;