import { describe, it, expect } from 'vitest';

import FeedPage from '@pages/feed/FeedPage';

import { render, fireEvent } from '@testing-library/react';

describe('FeedPage', () => {
  it('should render without crashing', () => {
    render(<FeedPage />);
  });

  // date가 20인 날짜를 클릭하면 문제와 난이도가 표시되어야 합니다.
  it('should display problem and difficulty when a date with a problem is clicked', () => {
    const { getByText } = render(<FeedPage />);
    const dateWithProblem = getByText('20');
    fireEvent.click(dateWithProblem);
    expect(getByText('문제: 토마토 난이도: gold4')).toBeInTheDocument();
  });

  // 문제와 난이도를 클릭하면 코드가 표시되어야 합니다.
  it('should display code when the problem and difficulty are clicked', () => {
    const { getByText } = render(<FeedPage />);
    const dateWithProblem = getByText('20');
    fireEvent.click(dateWithProblem);
    const problemAndDifficulty = getByText('문제: 토마토 난이도: gold4');
    fireEvent.click(problemAndDifficulty);
    expect(getByText('해결한 코드')).toBeInTheDocument();
  });
});
