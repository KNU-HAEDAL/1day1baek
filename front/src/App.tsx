import { RouterProvider } from 'react-router-dom';

import { GlobalStyle } from '@styles/GlobalStyles';

import { Router } from './Router';
import { Global } from '@emotion/react';

function App() {
  return (
    <>
      <Global styles={GlobalStyle} />
      <RouterProvider router={Router} />
    </>
  );
}

export default App;
