// OAuth2RedirectHandler.tsx
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext';

const OAuth2RedirectHandler = () => {
    const navigate = useNavigate();
    const { login, isAuthenticated } = useAuth();  // isAuthenticated 추가
  
    useEffect(() => {
      // 이미 인증된 상태면 리다이렉트 처리 중단
      if (isAuthenticated) {
        navigate('/dashboard', { replace: true });
        return;
      }
      // URL에서 토큰 파라미터 추출
      const params = new URLSearchParams(window.location.search);
      const token = params.get('token');
      
      if (token) {
        login(token); // AuthContext의 login 함수로 토큰 저장
        navigate('/dashboard', { replace: true });  // 대시보드로 이동
      } else {
        navigate('/login', { replace: true });  // 토큰이 없으면 로그인 페이지로
      }
    }, [navigate, login, isAuthenticated]);
  
    return <div>Loading...</div>; // 리다이렉트 처리 중 표시
  };

export default OAuth2RedirectHandler;

