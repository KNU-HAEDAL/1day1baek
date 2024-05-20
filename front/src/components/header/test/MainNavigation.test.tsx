import { MemoryRouter } from 'react-router-dom';

import { describe, expect, test } from 'vitest';

import MainNavigation from '@components/header/MainNavigation';

import { render, screen } from '@testing-library/react';

describe('MainNavigation', () => {
  test('renders the navigation bar', () => {
    render(
      <MemoryRouter>
        <MainNavigation />
      </MemoryRouter>
    );

    // 기본적인 네비게이션 바가 렌더링되는지 확인합니다.
    expect(screen.getByAltText('logo')).toBeInTheDocument();
    expect(screen.getByText('피드')).toBeInTheDocument();
    expect(screen.getByText('둘러보기')).toBeInTheDocument();
    expect(screen.getByText('그룹관리')).toBeInTheDocument();
  });

  test('renders logout icon when access token is present', () => {
    // 테스트 전에 로컬 스토리지에 access token을 설정합니다.
    localStorage.setItem('accessToken', 'fake-token');

    render(
      <MemoryRouter>
        <MainNavigation />
      </MemoryRouter>
    );

    // 로그아웃 아이콘이 렌더링 되는지 확인합니다.
    expect(screen.getByLabelText('logout')).toBeInTheDocument();

    // 테스트 후에 로컬 스토리지에서 access token을 제거합니다.
    localStorage.removeItem('accessToken');
  });

  test('renders login icon when access token is not present', () => {
    render(
      <MemoryRouter>
        <MainNavigation />
      </MemoryRouter>
    );

    // 로그인 아이콘이 렌더링 되는지 확인합니다.
    expect(screen.getByLabelText('login')).toBeInTheDocument();
  });
});
