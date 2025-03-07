import React from 'react';

const Login: React.FC = () => {
  const handleLogin = () => {
    // 백엔드 환경변수에서 URL을 읽어올 수 있도록 구성
    const backendUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';
    window.location.href = `${backendUrl}/oauth2/authorization/kakao`;
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-br from-yellow-100 to-yellow-300 p-4">
      <div className="bg-white rounded-lg shadow-xl p-8 w-full max-w-md transform transition-all hover:scale-105 duration-300">
        <h1 className="text-3xl font-bold text-center text-gray-800 mb-8">
          Band Analysis Login
        </h1>
        
        <button
          onClick={handleLogin}
          className="flex items-center justify-center w-full py-3 px-4 rounded-md bg-yellow-400 hover:bg-yellow-500 text-gray-800 font-medium text-lg shadow-md transition-colors duration-200"
        >
          <img 
            src="/kakao-icon.png" 
            alt="Kakao Logo" 
            className="w-6 h-6 mr-2"
            onError={(e) => {
              // 이미지 로드 실패 시 대체 텍스트 표시
              e.currentTarget.style.display = 'none';
            }}
          />
          카카오 로그인
        </button>
        
        <p className="mt-6 text-sm text-center text-gray-500">
          로그인하여 밴드 분석 서비스를 이용해보세요
        </p>
      </div>
    </div>
  );
};

export default Login;