# Band Analysis Project

음악 밴드의 연주를 분석하고 AI 피드백을 제공하는 웹 애플리케이션입니다.

## 프로젝트 구조
```
band-analysis/
├── frontend/          # React 프로젝트
├── backend/           # Spring Boot 프로젝트
├── docker/           # Docker 관련 파일들
├── docs/            # 프로젝트 문서
└── README.md
```

## 주요 기능
- OAuth2.0 기반 카카오 로그인
- 밴드 연주 파일 업로드 및 분석
- AI 기반 연주 피드백 제공
- 실시간 연주 분석 결과 시각화

## 기술 스택

### Frontend
- React 18
- TypeScript
- Tailwind CSS
- Vite

### Backend
- Spring Boot 3.x
- Spring Security
- JWT 인증
- PostgreSQL
- Docker

### DevOps
- Docker Compose
- GitHub Actions (예정)

## 시작하기

### 필수 요구사항
- Node.js 18 이상
- Java 17 이상
- Docker & Docker Compose
- PostgreSQL 15 이상

### 개발 환경 설정

1. 저장소 클론
```bash
git clone https://github.com/[your-username]/band-analysis.git
cd band-analysis
```

2. 프론트엔드 설정
```bash
cd frontend
npm install
npm run dev
```

3. 백엔드 설정
```bash
cd backend
./gradlew bootRun
```

4. Docker Compose 실행
```bash
docker-compose up
```

## API 문서
- API 문서는 `/docs/API.md` 참조

## 라이선스
이 프로젝트는 MIT 라이선스를 따릅니다.
