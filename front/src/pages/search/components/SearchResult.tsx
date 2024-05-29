import { DefaultButton } from '@components/button/DefaultButton';
import Text from '@components/typography/Text';

import { useGroups } from '@hooks/queries/search/getGroupsQuery';

import { IGroupProps } from '@interfaces/GroupInterface';

import styled from '@emotion/styled';

const SearchResultLayout = styled.div`
  width: 100%;
  height: 70px;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const PButton = styled(DefaultButton)`
  width: 90px;
  height: 30px;
  background-color: var(--color-red);
  color: var(--color-white);
  font-size: var(--size-sm);
  border-radius: 20px;
  padding: 0;
  margin: 0;
  &:hover {
    color: var(--color-black);
    background-color: var(--color-byellow);
  }
`;

const InputResultLayout = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 auto;
  width: 100%;
  height: 100%;
  overflow: auto;
  margin-top: 20px;
  padding: 0.5rem 1rem;
`;

const SearchResult = () => {
  const { data, isPending, isError, error } = useGroups();

  if (isPending) {
    return <InputResultLayout>로딩 중...</InputResultLayout>;
  }

  if (isError) {
    return (
      <InputResultLayout>
        데이터를 불러오는 중 오류가 발생했습니다: {error?.message}
      </InputResultLayout>
    );
  }

  if (data.length === 0) {
    return <InputResultLayout>검색 결과가 없습니다.</InputResultLayout>;
  }

  const onClickNav = () => {};

  return (
    <InputResultLayout>
      {data.map((group: IGroupProps) => (
        <SearchResultLayout key={group.id}>
          <Text size='var(--size-sm)' weight='700' color='var(--color-black)'>
            {group.name}
          </Text>
          <PButton onClick={onClickNav}>참여하기</PButton>
        </SearchResultLayout>
      ))}
    </InputResultLayout>
  );
};

export default SearchResult;
