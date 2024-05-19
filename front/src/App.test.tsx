import { describe, expect, it } from 'vitest';

import App from './App';
import { render, screen } from '@testing-library/react';

describe('App component', () => {
  it('renders "Hello World!" text', () => {
    render(<App />);
    const helloWorldText = screen.getByText(/Hello World!/i);
    expect(helloWorldText).toBeInTheDocument();
  });
});
