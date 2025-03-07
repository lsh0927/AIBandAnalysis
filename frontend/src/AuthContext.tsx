

import React, { createContext, useContext, useState, ReactNode } from 'react';
import { jwtDecode } from 'jwt-decode';


export interface JwtPayload {
  // 표준 필드
  sub?: string;          // 보통 '사용자 식별자'가 들어감
  iat?: number;
  exp?: number;
  
  // 아래는 카카오 로그인 시 필요한 커스텀 필드(예시)
  providerId?: string;   // 백엔드가 토큰 발행 시 넣어줄 수도 있는 커스텀 필드
  kakao_account?: {
    profile?: {
      nickname?: string;
    }
    email?: string;
    // 필요에 따라 더 확장
  };
}

// 실제 우리 애플리케이션에서 사용할 User 인터페이스
interface User {
  id: string;   // DB에서 식별자로 쓸 값 (provider_id 또는 PK)
  name: string; // 화면에 표시할 유저명
}

// Context가 제공할 값들의 타입
type AuthContextType = {
  isAuthenticated: boolean;
  token: string | null;
  user: User | null;
  login: (token: string) => Promise<void>;
  logout: () => void;
};

// 실제 Context 생성
export const AuthContext = createContext<AuthContextType | undefined>(undefined);

// Provider 컴포넌트
export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [token, setToken] = useState<string | null>(null);
  const [user, setUser] = useState<User | null>(null);

  // 토큰으로 로그인 처리
  const login = async (receivedToken: string) => {
    try {
      // 1) 토큰 디코딩
      const decoded = jwtDecode<JwtPayload>(receivedToken);
      console.log('전체 JWT 페이로드:', decoded);

      // 2) sub(또는 providerId) 꺼내기
      const userIdFromToken = decoded.sub || '';
      const providerIdFromToken = decoded.providerId || userIdFromToken;

      // 3) 닉네임이 토큰에도 있을 경우 추출
      const kakaoNickname = decoded.kakao_account?.profile?.nickname;

      // 4) 백엔드에서 사용자 정보 조회 (DB)
      // const response = await fetch(`/api/user/${providerIdFromToken}`, {
      //   headers: {
      //     'Authorization': `Bearer ${receivedToken}`
      //   }
      // });
      const response = await fetch(`http://localhost:8080/api/user/${providerIdFromToken}`, {
        headers: {
          'Authorization': `Bearer ${receivedToken}`
        }
      });
      
      if (!response.ok) {
        throw new Error('사용자 정보 조회 실패');
      }

      // 5) DB 사용자 정보
      const userData = await response.json();
      console.log('DB에서 가져온 사용자 정보:', userData);

      // 6) user state 세팅
      //    - 토큰에 닉네임이 있다면 그걸 우선 사용, 없으면 DB의 name 사용
      setUser({
        id: providerIdFromToken,
        name: kakaoNickname || userData.name
      });
      setToken(receivedToken);
      localStorage.setItem('token', receivedToken);
      setIsAuthenticated(true);

    } catch (error) {
      console.error('로그인 처리 오류:', error);
      // 필요 시 에러 처리 로직
    }
  };

  // 로그아웃 처리
  const logout = () => {
    localStorage.removeItem('token');
    setToken(null);
    setIsAuthenticated(false);
    setUser(null);
  };

  // AuthContext.Provider로 값 전달
  return (
    <AuthContext.Provider value={{ isAuthenticated, token, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

// AuthContext를 쉽게 가져다 쓰기 위한 custom hook
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
