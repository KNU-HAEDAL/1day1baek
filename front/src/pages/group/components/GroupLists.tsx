import { useNavigate } from 'react-router-dom';

import { DefaultButton } from '@components/button/DefaultButton';

import styled from '@emotion/styled';

const MyGroupLists = [
  {
    id: 1,
    name: '해달 파이팅',
    isPrivate: false,
  },
  {
    id: 2,
    name: '해달 고고',
    isPrivate: false,
  },
];

const GroupContainer = styled.div`
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
  height: 80px;
  width: 100%;
  margin-bottom: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const GroupTypeLayout = styled.div`
  display: flex;
  flex-direction: column;
`;

const GroupButtonLayout = styled.div`
  display: flex;
  flex-direction: row;
`;

const GroupName = styled.h3`
  margin: 0;
  font-size: 18px;
  color: #333;
  font-size: var(--size-sm);
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

const GoButton = styled(DefaultButton)`
  width: 90px;
  height: 30px;
  background-color: var(--color-red);
  color: var(--color-white);
  font-size: var(--size-sm);
  border-radius: 20px;
  padding: 0;
  margin-right: 10px;
  &:hover {
    color: var(--color-black);
    background-color: var(--color-byellow);
  }
  &:focus {
    background-color: var(--color-red);
    color: var(--color-white);
  }
`;

const GroupLists = () => {
  const navigate = useNavigate();

  const onClickLeave = () => {
    confirm('정말로 나가시겠습니까?');
  };

  return (
    <>
      {MyGroupLists.map((group) => (
        <GroupContainer key={group.id}>
          <GroupTypeLayout>
            <GroupName>{group.name}</GroupName>
            <GroupType>{group.isPrivate ? 'Private' : 'Public'}</GroupType>
          </GroupTypeLayout>
          <GroupButtonLayout>
            <GoButton
              onClick={() => {
                navigate(`/group/${group.id}`);
              }}
            >
              이동하기
            </GoButton>
            <GoButton onClick={onClickLeave}>나가기</GoButton>
          </GroupButtonLayout>
        </GroupContainer>
      ))}
    </>
  );
};

export default GroupLists;
