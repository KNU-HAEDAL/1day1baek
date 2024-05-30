import { useNavigate } from 'react-router-dom';

import { DefaultButton } from '@components/button/DefaultButton';
import Text from '@components/typography/Text';

import { useMyGroupData } from '@hooks/queries/group/getMyGroupQuery';
import { useGroups } from '@hooks/queries/search/getGroupsQuery';
import { useJoinGroupQuery } from '@hooks/queries/search/postJoinGroupQuery';

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

  &:focus {
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

const GroupType = styled.p`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 5px 0 0;
  font-size: 14px;
  color: var(--color-white);
  width: 60px;
  height: 20px;
  border-radius: 12px;
  background-color: var(--color-red);
  font-size: var(--size-xs);
`;

const GroupTypeLayout = styled.div`
  display: flex;
  flex-direction: column;
`;

const SearchResult = () => {
  const { data, isPending, isError, error } = useGroups();
  const { refetch: refreshGroupData } = useMyGroupData();
  const { mutate: joinGroup } = useJoinGroupQuery();
  const navigate = useNavigate();

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

  const filteredGroups = data.filter((group: IGroupProps) => !group.isMember);

  return (
    <InputResultLayout>
      {filteredGroups.length === 0 ? (
        <Text size='var(--size-sm)' weight='700' color='var(--color-black)'>
          조건에 맞는 검색 결과가 없습니다.
        </Text>
      ) : (
        filteredGroups.map((group: IGroupProps) => (
          <SearchResultLayout key={group.id}>
            <GroupTypeLayout>
              <Text
                size='var(--size-sm)'
                weight='700'
                color='var(--color-black)'
              >
                {group.name}
              </Text>
              <GroupType>{group.isPrivate ? 'Private' : 'Public'}</GroupType>
            </GroupTypeLayout>
            <PButton
              onClick={() => {
                if (group.isPrivate) {
                  const password = prompt('비밀번호를 입력해주세요');
                  if (password) {
                    joinGroup(
                      { groupId: group.id.toString(), password },
                      {
                        onSuccess: () => {
                          alert('참여하기를 완료하였습니다.');
                          refreshGroupData();
                          navigate('/group');
                        },
                      }
                    );
                  }
                } else {
                  console.log(group.id);
                  joinGroup(
                    { groupId: group.id.toString(), password: '' },
                    {
                      onSuccess: () => {
                        alert('참여하기를 완료하였습니다.');
                        refreshGroupData();
                        navigate('/group');
                      },
                    }
                  );
                }
              }}
            >
              참여하기
            </PButton>
          </SearchResultLayout>
        ))
      )}
    </InputResultLayout>
  );
};

export default SearchResult;
