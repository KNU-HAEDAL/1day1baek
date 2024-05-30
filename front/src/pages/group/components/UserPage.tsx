import Text from '@components/typography/Text';

import CodeLayout from '@pages/group/components/CodeLayout';
import TeamProfile from '@pages/group/components/TeamProfile';

import { ISelectGroupProps } from '@interfaces/GroupInterface';

import { TitleLayout, PublicToken, ContentLayout } from '@styles/GroupLayout';

const UserPage = ({ groupData }: { groupData: ISelectGroupProps }) => {
  return (
    <>
      <TitleLayout>
        <Text weight='600' size='var(--size-md)'>
          {groupData.name}
        </Text>
        <PublicToken>
          {groupData.isPrivate ? (
            <Text weight='600' size='var(--size-xxs)'>
              Private
            </Text>
          ) : (
            <Text weight='600' size='var(--size-xxs)'>
              Public
            </Text>
          )}
        </PublicToken>
      </TitleLayout>
      <ContentLayout>
        <CodeLayout />
        <TeamProfile groupData={groupData} />
      </ContentLayout>
    </>
  );
};

export default UserPage;
