# 1 Day 1 Baekjoon Frontend

1 Day 1Baekjoon 프론트엔드 프로젝트

## 실행 방법

이 프로젝트는 Vite + TypeScript + SWC로 이루어진 프로젝트입니다.

**pnpm이 없을 경우**

```
npm i -g pnpm
```

**프로젝트 시작할 때**

```
pnpm install
```

```
pnpm run dev
```

- Port는 5173입니다.
- 배포된 서버나 로컬에서 작업할 경우 파일 최상단에 .env파일을 만들어서 돌려야
  됩니다.
  - .env파일 생성할 경우 아래와 같이 둘 것.
  ```
  VITE_BASE_URL="http://localhost:8080"
  ```

**프로젝트내 라이브러리 및 script 명령어**

- 라이브러리가 설치가 되지 않았다는 에러가 뜰 시 아래를 참고하여 설치하시면
  됩니다.

```
{
  "name": "1day1baek",
  "private": true,
  "version": "0.0.0",
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "tsc && vite build",
    "lint": "eslint . --ext ts,tsx --report-unused-disable-directives --max-warnings 0",
    "preview": "vite preview",
    "format": "prettier --write .",
    "test": "vitest"
  },
  "dependencies": {
    "@emotion/react": "^11.11.4",
    "@emotion/styled": "^11.11.5",
    "@hookform/error-message": "^2.0.1",
    "@hookform/resolvers": "^3.4.2",
    "@tanstack/react-query": "^5.40.0",
    "axios": "^1.7.2",
    "dayjs": "^1.11.11",
    "react": "^18.3.1",
    "react-calendar": "^5.0.0",
    "react-dom": "^18.3.1",
    "react-hook-form": "^7.51.5",
    "react-hot-toast": "^2.4.1",
    "react-icons": "^5.2.1",
    "react-router-dom": "^6.23.1",
    "zod": "^3.23.8",
    "zustand": "^4.5.2"
  },
  "devDependencies": {
    "@rushstack/eslint-config": "^3.7.0",
    "@rushstack/eslint-patch": "^1.10.3",
    "@tanstack/eslint-plugin-query": "^5.35.6",
    "@tanstack/react-query-devtools": "^5.40.0",
    "@testing-library/jest-dom": "^6.4.5",
    "@testing-library/react": "^15.0.7",
    "@trivago/prettier-plugin-sort-imports": "^4.3.0",
    "@types/react": "^18.3.3",
    "@types/react-dom": "^18.3.0",
    "@typescript-eslint/eslint-plugin": "6.19.1",
    "@typescript-eslint/parser": "^7.11.0",
    "@vitejs/plugin-react": "^4.3.0",
    "@vitejs/plugin-react-swc": "^3.7.0",
    "eslint": "^8.57.0",
    "eslint-config-prettier": "^9.1.0",
    "eslint-plugin-jsx-a11y": "^6.8.0",
    "eslint-plugin-no-relative-import-paths": "^1.5.4",
    "eslint-plugin-prettier": "^5.1.3",
    "eslint-plugin-react-hooks": "^4.6.2",
    "eslint-plugin-react-refresh": "^0.4.7",
    "jsdom": "^24.1.0",
    "msw": "^2.3.0",
    "prettier": "^3.2.5",
    "typescript": "^5.4.5",
    "vite": "^5.2.12",
    "vitest": "^1.6.0"
  },
  "msw": {
    "workerDirectory": [
      "public"
    ]
  }
}

```
