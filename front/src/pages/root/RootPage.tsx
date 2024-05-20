import { Outlet, useNavigation } from 'react-router-dom';

import styled from '@emotion/styled';

const MainContent = styled.main`
  padding-top: 45px;
  width: 100%;
  height: calc(auto - 45px);
  overflow: hidden;
`;

const RootPage = () => {
  const navigation = useNavigation();

  return (
    <>
      {/* navigation component와야함 */}
      <MainContent>
        {navigation.state === 'loading' && <p>Loading...</p>}
        <Outlet />
      </MainContent>
    </>
  );
};

export default RootPage;
