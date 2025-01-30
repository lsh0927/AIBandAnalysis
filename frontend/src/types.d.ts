declare global {
    interface Window {
      Kakao?: {
        isInitialized(): any;
        init: (apiKey: string) => void;
        Auth: {
          authorize: (settings: {
            redirectUri: string;
            scope?: string;
          }) => void;
        };
      }
    }
  }

  export {} // 이 줄을 추가해주세요