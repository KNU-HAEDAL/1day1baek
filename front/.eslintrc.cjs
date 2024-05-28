module.exports = {
  root: true,
  env: { browser: true, es2020: true },
  extends: [
    'eslint:recommended',
    'plugin:react/recommended',
    'plugin:react-hooks/recommended',
    'plugin:react/jsx-runtime',
    'plugin:@tanstack/eslint-plugin-query/recommended',
    'plugin:jsx-a11y/recommended',
    'prettier',
    '@rushstack/eslint-config/profile/web-app',
  ],
  settings: {
    react: { version: '18.2' },
  },
  ignorePatterns: [
    'dist',
    '.eslintrc.cjs',
    'vite.config.ts',
    'public/mockServiceWorker.js',
  ],
  parser: '@typescript-eslint/parser',
  parserOptions: {
    project: true,
    tsconfigRootDir: __dirname,
  },
  plugins: ['react-refresh', 'prettier', 'no-relative-import-paths'],

  rules: {
    'react-refresh/only-export-components': [
      'warn',
      { allowConstantExport: true },
    ],
    'prettier/prettier': 'error',
    '@typescript-eslint/explicit-function-return-type': 'off',
    'no-relative-import-paths/no-relative-import-paths': [
      'error',
      { allowSameFolder: true, rootDir: 'src' },
    ],
    'jsx-a11y/alt-text': [
      'warn',
      {
        elements: ['img'],
      },
    ],

    '@typescript-eslint/no-floating-promises': 'off',
    // 유효한 aria-* 속성만 사용
    'jsx-a11y/aria-props': 'warn',
    // 유효한 aria-* 상태/값만 사용
    'jsx-a11y/aria-proptypes': 'warn',
    // DOM에서 지원되는 role, ARIA만 사용
    'jsx-a11y/aria-unsupported-elements': 'warn',
    // 필수 ARIA 속성이 빠져있는지 체크
    'jsx-a11y/role-has-required-aria-props': 'warn',
    // ARIA 속성은 지원되는 role에서만 사용
    'jsx-a11y/role-supports-aria-props': 'warn',
    // DOM에 정의되지 않은 속성을 사용했는지 체크 (emotion css 속성 등 예외 케이스가 있으므로 기본은 off)
    'react/no-unknown-property': 'off',
    // 정의한 props 중에 빠진게 있는지 체크 (NextPage 등 일부 추상화 컴포넌트에서 복잡해지므로 기본은 off)
    'react/prop-types': 'off',
    // typedef-var 규칙 비활성화
    '@rushstack/typedef-var': 'off',
    // null 사용은 레거시 API를 설명할 때에만 허용되며, 이 경우를 제외하고는 "undefined"를 사용해야 합니다.
    '@rushstack/no-new-null': 'off',
  },
};

/*
 * @rushstack/eslint-patch는 공유 컨피그 패키지에 ESLint 플러그인을 포함시켜줍니다.
 *
 * https://www.npmjs.com/package/@rushstack/eslint-patch
 */
require('@rushstack/eslint-patch/modern-module-resolution');
