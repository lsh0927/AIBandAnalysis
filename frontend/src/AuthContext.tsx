// import { createContext, useContext, useState, ReactNode } from 'react';

// //인증 상태 관리를 위한 코드

// type AuthContextType = {
//   isAuthenticated: boolean; //인증 여부
//   token: string | null; // Jwt 토큰
//   login: (token: string) => void; //로그인 함수
//   logout: () => void; //로그아웃 함수
// };

// export const AuthContext = createContext<AuthContextType | undefined>(undefined); 

// export const AuthProvider = ({ children }: { children: ReactNode }) => {
//   const [isAuthenticated, setIsAuthenticated] = useState(false);
//   const [token, setToken] = useState<string | null>(null);

//   //로그인: 토큰을 저장하고 인증 상태를 true로 설정
  
//   const login = (token: string) => {
//     console.log('Setting token in AuthContext:', token);
//     localStorage.setItem('token', token); //브라우저 저장소에 토큰을 저장
//     setToken(token); //상태에 토큰 저장 
//     setIsAuthenticated(true); // 인증 상태 true로 설정
//     console.log('Authentication state:', true);
//   };
  

//   // 토큰을 제거하고 인증 상태를 false로 설정
//   const logout = () => {
//     localStorage.removeItem('token'); //브라우저 저장소에서 토큰 제거
//     setToken(null); //토큰 상태 초기화
//     setIsAuthenticated(false); //인증상태 false로 설정
//   };

//   //Context Provider로 상태와 함수들을 하위 컴포넌트에 제공
//   return (
//     <AuthContext.Provider value={{ isAuthenticated, token, login, logout }}>
//       {children}
//     </AuthContext.Provider>
//   );
// };

// export const useAuth = () => {
//   const context = useContext(AuthContext);
//   if (context === undefined) {
//     throw new Error('useAuth must be used within an AuthProvider');
//   }
//   return context;
// };


// // AuthContext.tsx
// import { createContext, useContext, useState, ReactNode } from 'react';

// export interface User {
//   id: number;
//   name: string;
//   email: string;
//   // 필요한 다른 필드도 추가
// }

// type AuthContextType = {
//   isAuthenticated: boolean;
//   token: string | null;
//   user: User | null;
//   login: (token: string, user: User) => void;
//   logout: () => void;
// };

// export const AuthContext = createContext<AuthContextType | undefined>(undefined);

// export const AuthProvider = ({ children }: { children: ReactNode }) => {
//   const [isAuthenticated, setIsAuthenticated] = useState(false);
//   const [token, setToken] = useState<string | null>(null);
//   const [user, setUser] = useState<User | null>(null);

//   const login = (token: string, user: User) => {
//     console.log('Setting token and user in AuthContext:', token, user);
//     localStorage.setItem('token', token);
//     localStorage.setItem('userId', String(user.id));
//     setToken(token);
//     setUser(user);
//     setIsAuthenticated(true);
//     console.log('Authentication state:', true);
//   };

//   const logout = () => {
//     localStorage.removeItem('token');
//     localStorage.removeItem('userId');
//     setToken(null);
//     setUser(null);
//     setIsAuthenticated(false);
//   };

//   return (
//     <AuthContext.Provider value={{ isAuthenticated, token, user, login, logout }}>
//       {children}
//     </AuthContext.Provider>
//   );
// };

// export const useAuth = () => {
//   const context = useContext(AuthContext);
//   if (context === undefined) {
//     throw new Error('useAuth must be used within an AuthProvider');
//   }
//   return context;
// };

// AuthContext.tsx
import React, { createContext, useContext, useState, ReactNode } from 'react';
import { jwtDecode } from 'jwt-decode';

// JWT payload에 대한 인터페이스 정의 (필요한 클레임에 맞게 수정)
interface DecodedToken {
  sub: string; // 보통 사용자 ID가 이 필드에 담깁니다.
  // 추가 클레임이 있다면 여기에 정의
}

interface User {
  id: string;
  // 추가 사용자 정보가 있다면 여기에 정의
}

type AuthContextType = {
  isAuthenticated: boolean;
  token: string | null;
  user: User | null;
  login: (token: string) => void;
  logout: () => void;
};

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [token, setToken] = useState<string | null>(null);
  const [user, setUser] = useState<User | null>(null);

  const login = (token: string) => {
    // 토큰 디코딩
    const decoded: DecodedToken = jwtDecode(token);
    const userId = decoded.sub; // 'sub' 필드에서 사용자 ID 추출
    console.log('Decoded userId:', userId);

    // user 객체를 구성해서 상태에 저장
    setUser({ id: userId });
    localStorage.setItem('token', token);
    setToken(token);
    setIsAuthenticated(true);
    console.log('Authentication state set. User:', { id: userId });
  };

  const logout = () => {
    localStorage.removeItem('token');
    setToken(null);
    setUser(null);
    setIsAuthenticated(false);
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, token, user, login, logout }}>
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
