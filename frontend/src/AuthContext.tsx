<<<<<<< HEAD
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
=======

// // AuthContext.tsx
// import React, { createContext, useContext, useState, ReactNode } from 'react';
// import { jwtDecode, JwtPayload } from 'jwt-decode';

// // JwtPayload를 확장한 커스텀 인터페이스 생성
// interface CustomJwtPayload extends JwtPayload {
//   name?: string;
//   preferred_username?: string;
//   given_name?: string;
//   user_name?: string;
//   // 추가로 가능한 필드들
// }

// interface User {
//   id: string;
//   name: string;
>>>>>>> 61ded68 (again)
// }

// type AuthContextType = {
//   isAuthenticated: boolean;
//   token: string | null;
//   user: User | null;
<<<<<<< HEAD
//   login: (token: string, user: User) => void;
=======
//   login: (token: string) => void;
>>>>>>> 61ded68 (again)
//   logout: () => void;
// };

// export const AuthContext = createContext<AuthContextType | undefined>(undefined);

// export const AuthProvider = ({ children }: { children: ReactNode }) => {
//   const [isAuthenticated, setIsAuthenticated] = useState(false);
//   const [token, setToken] = useState<string | null>(null);
//   const [user, setUser] = useState<User | null>(null);

<<<<<<< HEAD
//   const login = (token: string, user: User) => {
//     console.log('Setting token and user in AuthContext:', token, user);
//     localStorage.setItem('token', token);
//     localStorage.setItem('userId', String(user.id));
//     setToken(token);
//     setUser(user);
//     setIsAuthenticated(true);
//     console.log('Authentication state:', true);
=======
//   const login = (token: string) => {
//     try {
//       // CustomJwtPayload 인터페이스 사용
//       const decoded = jwtDecode<CustomJwtPayload>(token);
//       console.log('전체 JWT 페이로드:', decoded);
      
//       const userId = decoded.sub || '';
      
//       // 이름이 어디에 있는지 확인
//       let username;
//       if (decoded.name) username = decoded.name;
//       else if (decoded.preferred_username) username = decoded.preferred_username;
//       else if (decoded.given_name) username = decoded.given_name;
//       else if (decoded.user_name) username = decoded.user_name;
//       else username = "사용자"; // 기본값
      
//       console.log('추출된 userId:', userId, '추출된 username:', username);
      
//       setUser({ id: userId, name: username });
//       localStorage.setItem('token', token);
//       setToken(token);
//       setIsAuthenticated(true);
//     } catch (error) {
//       console.error('JWT 디코딩 오류:', error);
//       // 에러 처리
//     }
>>>>>>> 61ded68 (again)
//   };

//   const logout = () => {
//     localStorage.removeItem('token');
<<<<<<< HEAD
//     localStorage.removeItem('userId');
=======
>>>>>>> 61ded68 (again)
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

<<<<<<< HEAD
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

=======
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
>>>>>>> 61ded68 (again)
type AuthContextType = {
  isAuthenticated: boolean;
  token: string | null;
  user: User | null;
<<<<<<< HEAD
  login: (token: string) => void;
  logout: () => void;
};

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

=======
  login: (token: string) => Promise<void>;
  logout: () => void;
};

// 실제 Context 생성
export const AuthContext = createContext<AuthContextType | undefined>(undefined);

// Provider 컴포넌트
>>>>>>> 61ded68 (again)
export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [token, setToken] = useState<string | null>(null);
  const [user, setUser] = useState<User | null>(null);

<<<<<<< HEAD
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

=======
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
>>>>>>> 61ded68 (again)
  return (
    <AuthContext.Provider value={{ isAuthenticated, token, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

<<<<<<< HEAD
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
=======
// AuthContext를 쉽게 가져다 쓰기 위한 custom hook
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
>>>>>>> 61ded68 (again)
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
