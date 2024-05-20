import { ReactNode } from 'react';
import { NavLink } from 'react-router-dom';

import { ITextProps } from '@/interfaces/TextInterface';
import styled from '@emotion/styled';

interface ILinkTextContentProps extends ITextProps {
  children: ReactNode;
  text?: string;
  to: string;
}

const LinkTextContainer = styled(NavLink)<ILinkTextContentProps>`
  font-size: ${({ size }) => size || '1rem'};
  font-weight: ${({ weight }) => weight || 'normal'};
  color: ${({ color }) => color || 'inherit'};
  text-decoration: none;
  cursor: pointer;
`;

const LinkText = ({
  children,
  text,
  size,
  weight,
  to,
  color = 'var(--color-blue)',
  ...rest
}: ILinkTextContentProps) => {
  return (
    <LinkTextContainer
      to={to}
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
