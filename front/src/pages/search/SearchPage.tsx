import Text from '@components/typography/Text';

import SearchResult from '@pages/search/components/SearchResult';

import { Layout, DisplayLayout } from '@styles/Layout';

import styled from '@emotion/styled';

const SearchLayout = styled(DisplayLayout)`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 45px;
  height: calc(100vh - 90px);
`;

const SearchPage = () => {
  return (
    <Layout>
      <SearchLayout>
        <Text weight='700' size='var(--size-md)'>
          현재 그룹 목록
        </Text>
        <SearchResult />
      </SearchLayout>
    </Layout>
  );
};

export default SearchPage;
