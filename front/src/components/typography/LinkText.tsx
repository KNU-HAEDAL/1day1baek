import { MouseEventHandler } from 'react';
import { ReactNode } from 'react';

import { ITextProps } from '@/interfaces/TextInterface';
import styled from '@emotion/styled';

interface ILinkTextContentProps extends ITextProps {
  children: ReactNode;
  onClick: MouseEventHandler<HTMLSpanElement>;
  text?: string;
}

const LinkTextContainer = styled.a<ILinkTextContentProps>`
  font-size: ${({ size }) => size || '1rem'};
  font-weight: ${({ weight }) => weight || 'normal'};
  color: ${({ color }) => color || 'inherit'};
  cursor: pointer;
  text-decoration: none;
  &:hover {
    text-decoration: none;
  }
`;

const LinkText = ({
  children,
  text,
  onClick,
  size,
  weight,
  color = 'var(--color-blue)',
  ...rest
}: ILinkTextContentProps) => {
  return (
    <LinkTextContainer
      onClick={onClick}
      size={size}
      weight={weight}
      color={color}
      {...rest}
    >
      {text || children}
    </LinkTextContainer>
  );
};

export default LinkText;
