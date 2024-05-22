import dayjs from 'dayjs';
import { describe, it, expect } from 'vitest';

import FeedPage from '@pages/feed/FeedPage';

import { render, fireEvent, screen } from '@testing-library/react';

describe('FeedPage', () => {
  it('should render without crashing', () => {
    render(<FeedPage />);
  });

  it('should display problems and their difficulties when a date with records is clicked', () => {
    const { getByText } = render(<FeedPage />);
    const testDate = new Date('2024-05-20');

    // 테스트 날짜에 해당하는 캘린더 타일을 찾아 클릭합니다
    const formattedDate = dayjs(testDate).format('D');
    const dateTile = getByText(formattedDate);
    fireEvent.click(dateTile);

    // 클릭된 날짜에 대한 문제가 표시되는지 확인합니다
    expect(screen.getByText('토마토')).toBeInTheDocument();
    expect(screen.getAllByText('gold4').length).toBeGreaterThanOrEqual(4);
  });

  it('should not display problems when a date without records is clicked', () => {
    const { getByText } = render(<FeedPage />);
    const testDate = new Date('2024-05-21');

    // 테스트 날짜에 해당하는 캘린더 타일을 찾아 클릭합니다
    const formattedDate = dayjs(testDate).format('D');
    const dateTile = getByText(formattedDate);
    fireEvent.click(dateTile);

    // 클릭된 날짜에 대한 문제가 표시되는지 확인합니다
    expect(screen.queryByText('토마토')).toBeNull();
    expect(screen.queryByText('gold4')).toBeNull();
  });
});
