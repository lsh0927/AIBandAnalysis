import { createContext, useContext, useState, ReactNode } from 'react';

//인증 상태 관리를 위한 코드

type AuthContextType = {
  isAuthenticated: boolean; //인증 여부
  token: string | null; // Jwt 토큰
  login: (token: string) => void; //로그인 함수
  logout: () => void; //로그아웃 함수
};

export const AuthContext = createContext<AuthContextType | undefined>(undefined); 

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [token, setToken] = useState<string | null>(null);

  //로그인: 토큰을 저장하고 인증 상태를 true로 설정
  
  const login = (token: string) => {
    console.log('Setting token in AuthContext:', token);
    localStorage.setItem('token', token); //브라우저 저장소에 토큰을 저장
    setToken(token); //상태에 토큰 저장 
    setIsAuthenticated(true); // 인증 상태 true로 설정
    console.log('Authentication state:', true);
  };
  

  // 토큰을 제거하고 인증 상태를 false로 설정
  const logout = () => {
    localStorage.removeItem('token'); //브라우저 저장소에서 토큰 제거
    setToken(null); //토큰 상태 초기화
    setIsAuthenticated(false); //인증상태 false로 설정
  };

  //Context Provider로 상태와 함수들을 하위 컴포넌트에 제공
  return (
    <AuthContext.Provider value={{ isAuthenticated, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};