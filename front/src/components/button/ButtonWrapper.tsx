import { IButtonWrapperProps } from '@interfaces/ButtonInterface';

import styled from '@emotion/styled';

export const ButtonWrapper = styled.div<IButtonWrapperProps>`
  display: ${({ display }) => {
    switch (display) {
      case 'flex':
        return 'flex';
      case 'block':
        return 'block';
      case 'inline-block':
        return 'inline-block';
      case 'inline':
        return 'inline';
      case 'none':
        return 'none';
      default:
        return;
    }
  }};

  flex-direction: ${({ flexDirection }) => {
    switch (flexDirection) {
      case 'row':
        return 'row';
      case 'column':
        return 'column';
      case 'row-reverse':
        return 'row-reverse';
      case 'column-reverse':
        return 'column-reverse';
      default:
        return;
    }
  }};

  justify-content: ${({ justifyContent }) => {
    switch (justifyContent) {
      case 'center':
        return 'center';
      case 'flex-start':
        return 'flex-start';
      case 'flex-end':
        return 'flex-end';
      case 'space-between':
        return 'space-between';
      case 'space-around':
        return 'space-around';
      default:
        return;
    }
  }};

  align-items: ${({ alignItems }) => {
    switch (alignItems) {
      case 'center':
        return 'center';
      case 'flex-start':
        return 'flex-start';
      case 'flex-end':
        return 'flex-end';
      case 'stretch':
        return 'stretch';
      case 'baseline':
        return 'baseline';
      default:
        return;
    }
  }};
`;
