import { css } from '@emotion/react';

//크기 및 많이 사용하는 사이즈 정의
const sizeXxl = '2.5rem'; //40px
const sizeMxl = '2.25rem'; // 36px
const sizeXl = '1.5rem'; // 24px
const sizeLg = '1.25rem'; // 20px
const sizeMd = '1rem'; // 16px
const sizeSm = '0.875rem'; // 14px
const sizeXs = '0.75rem'; // 12px

//color 정의
const colorWhite = '#FFFFFF';
const colorBlack = '#000000';
const colorGray = '#d9d9d9';
const colorBlue = '#4285F4';
const colorRed = '#ff6f61';
const colorLightYellow = '#fff5e6';
const colorBorderYellow = '#ffebcd';
const colorSpacePrimary = '#04094f';
const colorSpaceSecondary = '#2f199b';
const colorSpaceTertiary = '#4d179b';

export const GlobalStyle = css`
  @import url('https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wdth,wght@0,62.5..100,100..900;1,62.5..100,100..900&display=swap');

  :root {
    --size-xxl: ${sizeXxl};
    --size-mxl: ${sizeMxl};
    --size-xl: ${sizeXl};
    --size-lg: ${sizeLg};
    --size-md: ${sizeMd};
    --size-sm: ${sizeSm};
    --size-xs: ${sizeXs};

    --color-white: ${colorWhite};
    --color-black: ${colorBlack};
    --color-gray: ${colorGray};
    --color-blue: ${colorBlue};
    --color-red: ${colorRed};
    --color-lyellow: ${colorLightYellow};
    --color-byellow: ${colorBorderYellow};
    --color-space-primary: ${colorSpacePrimary};
    --color-space-secondary: ${colorSpaceSecondary};
    --color-space-tertiary: ${colorSpaceTertiary};
  }

  * {
    box-sizing: border-box;
  }

  body {
    margin: 0;
    padding: 0;
    width: 100vw;
    font-family: 'Noto+Sans', monospace;
    background-color: var(--color-white);
    color: var(--color-black);
  }

  input,
  textarea {
    -moz-user-select: auto;
    -webkit-user-select: auto;
    -ms-user-select: auto;
    user-select: auto;
    resize: none;
  }

  input:focus {
    outline: none;
  }
  button {
    border: none;
    background: none;
    padding: 0;
    cursor: pointer;
  }
`;
