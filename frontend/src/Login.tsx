import React from 'react';

const Login: React.FC = () => {
    // 카카오 로그인 버튼 클릭 핸들러
  // const handleLogin = () => {
  //   console.log("Login button clicked");

  //   // Spring Security OAuth2 인증 엔드포인트로 리다이렉트
  //    window.location.href = '/oauth2/authorization/kakao';

  //   // const VITE_KAKAO_CLIENT_ID = import.meta.env.VITE_KAKAO_CLIENT_ID;
  //   // const REDIRECT_URI = "http://localhost:8080/login/oauth2/code/kakao";
  //   // const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${VITE_KAKAO_CLIENT_ID}&redirect_uri=${encodeURIComponent(REDIRECT_URI)}&response_type=code`;
  //   // window.location.href = kakaoURL;
  // };

  const handleLogin = () => {
    const backendUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';
    console.log('Environment variables:', {
        VITE_BACKEND_URL: import.meta.env.VITE_BACKEND_URL,
        backendUrl: backendUrl,
        fullUrl: `${backendUrl}/oauth2/authorization/kakao`
    });
    // window.location.href = `${backendUrl}/oauth2/authorization/kakao`;
    window.location.href = `http://localhost:8080/oauth2/authorization/kakao`;
};
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <div className="p-8 bg-white rounded-lg shadow-md">
        <h1 className="mb-6 text-2xl font-bold text-center text-gray-800">Band Analysis Login</h1>
        <button
          onClick={handleLogin}
          className="flex items-center justify-center w-full px-4 py-2 text-white bg-yellow-400 rounded-md hover:bg-yellow-500 focus:outline-none focus:ring-2 focus:ring-yellow-500 focus:ring-offset-2"
        >
          <img src="/api/placeholder/24/24" alt="Kakao Logo" className="w-6 h-6 mr-2" />
          카카오 로그인
        </button>
      </div>
    </div>
  );
};

export default Login;