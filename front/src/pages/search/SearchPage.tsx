import Text from '@components/typography/Text';

import SearchResult from '@pages/search/components/SearchResult';

import { useTokenStore } from '@stores/useTokenStore';

import { Layout, DisplayLayout, LoginLayout } from '@styles/Layout';

import styled from '@emotion/styled';

const SearchLayout = styled(DisplayLayout)`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 45px;
  height: calc(100vh - 90px);
`;

const SearchPage = () => {
  const { isAccessToken } = useTokenStore();

  return (
    <Layout>
      {isAccessToken ? (
        <SearchLayout>
          <Text weight='700' size='var(--size-md)'>
            현재 그룹 목록
          </Text>
          <SearchResult />
        </SearchLayout>
      ) : (
        <LoginLayout>
          <Text size='var(--size-lg)' weight='700'>
            둘러보기 기능은 로그인 후 이용 가능합니다.
          </Text>
        </LoginLayout>
      )}
    </Layout>
  );
};

export default SearchPage;
