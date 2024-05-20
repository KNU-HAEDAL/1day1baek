import { Outlet, useNavigation } from 'react-router-dom';

import MainNavigation from '@/components/header/MainNavigation';
import styled from '@emotion/styled';

const MainContent = styled.main`
  width: 100%;
  height: auto;
  overflow: hidden;
`;

const RootPage = () => {
  const navigation = useNavigation();

  return (
    <>
      <MainNavigation />
      <MainContent>
        {navigation.state === 'loading' && <p>Loading...</p>}
        <Outlet />
      </MainContent>
    </>
  );
};

export default RootPage;
