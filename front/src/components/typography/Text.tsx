import { ReactNode } from 'react';

import { ITextProps } from '@interfaces/TextInterface';

import styled from '@emotion/styled';

interface ITextContentProps extends ITextProps {
  children?: ReactNode;
}

const StyledText = styled.p<ITextProps>`
  font-size: ${({ size }) => size || '1rem'};
  font-weight: ${({ weight }) => weight || 'normal'};
  color: ${({ color }) => color || 'inherit'};
  margin: 0;
`;

const Text = ({
  children,
  size,
  weight,
  color,
  ...rest
}: ITextContentProps) => {
  return (
    <StyledText size={size} weight={weight} color={color} {...rest}>
      {children}
    </StyledText>
  );
};

export default Text;
