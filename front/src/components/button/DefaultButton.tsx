import { MouseEventHandler, ReactNode } from 'react';

import { IButtonProps } from '@/interfaces/ButtonInterface';
import styled from '@emotion/styled';

interface IButtonActionProps extends IButtonProps {
  children: ReactNode;
  onClick: MouseEventHandler<HTMLButtonElement>;
}

export const DefaultButton = styled.button<IButtonActionProps>`
  width: ${({ width }) => width || 'auto'};
  height: ${({ height }) => height || 'auto'};
  color: ${({ color }) => color || 'inherit'};
  background-color: ${({ backgroundColor }) =>
    backgroundColor || 'transparent'};
  border-radius: 0.25rem;
  padding: 0 ${({ pd }) => pd}px;
  font-size: ${({ fontSize }) => fontSize || '1rem'};
  cursor: pointer;

  &:hover {
    color: ${({ hoverColor }) => hoverColor || 'inherit'};
    background-color: ${({ hoverBackgroundColor }) =>
      hoverBackgroundColor || 'transparent'};
  }

  &:focus {
    outline: none;
    background-color: ${({ hoverBackgroundColor }) =>
      hoverBackgroundColor || 'transparent'};
  }
`;
