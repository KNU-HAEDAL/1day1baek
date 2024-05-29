import { BiBookmark, BiMoney, BiSolidCrown } from 'react-icons/bi';
import { IoIosClose } from 'react-icons/io';

import Text from '@components/typography/Text';

import { ISelectGroupProps } from '@interfaces/GroupInterface';

import styled from '@emotion/styled';

const TeamLayout = styled.div`
  height: 100%;
  width: 300px;
`;

const ExplainLayout = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: left;
  width: 100%;
  height: 70px;
  padding: 8px 8px;
  border-bottom: 1px solid var(--color-gray);
`;

const TextLayout = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: left;
  align-items: center;
  margin-top: 12px;
`;

const StyledBiBookmark = styled(BiBookmark)`
  margin-right: 4px;
  color: var(--color-red);
`;

const StyledBiMoney = styled(BiMoney)`
  margin-right: 4px;
  color: var(--color-green);
`;

const StyledBiCrown = styled(BiSolidCrown)`
  margin-right: 4px;
  color: var(--color-yellow);
`;

const ProfileLayout = styled.div`
  height: 100%;
  width: 100%;
  padding: 8px 8px;
  overflow: auto;
  display: flex;
  flex-direction: column;
  align-items: left;
`;

const ContributorLayout = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 32px;
  margin-bottom: 8px;
  width: 100%;
`;

const ProfileImg = styled.div<{ imgUrl: string }>`
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 8px;
  background-image: url(${(props) => props.imgUrl});
  background-size: cover;
  border: 1px solid var(--color-gray);
`;

const ProfileTextLayout = styled.div`
  margin-right: 8px;
`;

const ListLayout = styled.div`
  display: flex;
  flex-direction: column;
`;

const AdminTeamProfile = ({ groupData }: { groupData: ISelectGroupProps }) => {
  // console.log(groupData);

  return (
    <TeamLayout>
      <ExplainLayout>
        <Text color='var(--color-black)' size='var(--size-md)' weight='700'>
          Our Team Lead
        </Text>
        <TextLayout>
          <StyledBiCrown />
          <Text color='var(--color-black)' size='var(--size-xs)' weight='500'>
            {groupData.owner.name}
          </Text>
        </TextLayout>
      </ExplainLayout>
      <ExplainLayout>
        <Text color='var(--color-black)' size='var(--size-md)' weight='700'>
          목표 문제 개수
        </Text>
        <TextLayout>
          <StyledBiBookmark />
          <Text color='var(--color-black)' size='var(--size-xs)' weight='500'>
            {groupData.goalSolveCount}개
          </Text>
        </TextLayout>
      </ExplainLayout>
      <ExplainLayout>
        <Text color='var(--color-black)' size='var(--size-md)' weight='700'>
          어길 시 벌금
        </Text>
        <TextLayout>
          <StyledBiMoney />
          <Text color='var(--color-black)' size='var(--size-xs)' weight='500'>
            {groupData.fine}원
          </Text>
        </TextLayout>
      </ExplainLayout>
      <ProfileLayout>
        <Text color='var(--color-black)' size='var(--size-md)' weight='700'>
          Contributors
        </Text>
        <TextLayout>
          <ListLayout>
            {groupData.users.map((contributor) => (
              <ContributorLayout key={contributor.loginId}>
                <ProfileImg imgUrl={contributor.profileUrl} />
                <ProfileTextLayout>
                  <Text
                    color='var(--color-black)'
                    size='var(--size-xs)'
                    weight='700'
                  >
                    {contributor.loginId}
                  </Text>
                </ProfileTextLayout>
                <Text color='#333' size='var(--size-xs)' weight='600'>
                  {contributor.name}
                </Text>
                <IoIosClose
                  size={24}
                  onClick={() => {
                    confirm('내보내시겠습니까?');
                  }}
                />
              </ContributorLayout>
            ))}
          </ListLayout>
        </TextLayout>
      </ProfileLayout>
    </TeamLayout>
  );
};

export default AdminTeamProfile;
