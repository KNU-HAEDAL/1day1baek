import { MouseEventHandler, ReactNode } from 'react';

import { IButtonProps } from '@/interfaces/ButtonInterface';
import styled from '@emotion/styled';

interface IButtonActionProps extends IButtonProps {
  children: ReactNode;
  onClick: MouseEventHandler<HTMLSpanElement>;
  text?: string;
}

const StyledButton = styled.button<IButtonActionProps>`
  width: ${({ width }) => (width === '100%' ? '100%' : `${width}`)};
  height: ${({ height }) => (height === '100%' ? '100%' : `${height}`)};
  color: ${({ color }) => color || 'inherit'};
  background-color: ${({ backgroundColor }) =>
    backgroundColor || 'transparent'};
  opacity: ${({ blur }) => blur || '0'}%;
  border-radius: 0.25rem;
  padding: 0 ${({ pd }) => pd}px;
  cursor: pointer;

  &:hover {
    color: ${({ hoverColor }) => hoverColor || 'inherit'};
    background-color: ${({ hoverBackgroundColor }) =>
      hoverBackgroundColor || 'transparent'};
  }
`;

const DefaultButton = ({
  children,
  color,
  hoverColor,
  hoverBackgroundColor,
  width,
  height,
  pd,
  blur,
  onClick,
  text,
}: IButtonActionProps) => {
  return (
    <StyledButton
      onClick={onClick}
      color={color}
      hoverColor={hoverColor}
      hoverBackgroundColor={hoverBackgroundColor}
      width={width}
      height={height}
      pd={pd}
      blur={blur}
    >
      {text || children}
    </StyledButton>
  );
};

export default DefaultButton;
