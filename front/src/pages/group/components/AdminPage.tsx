import Text from '@components/typography/Text';

import CodeLayout from '@pages/group/components/CodeLayout';

import { ISelectGroupProps } from '@interfaces/GroupInterface';

import { TitleLayout, PublicToken, ContentLayout } from '@styles/GroupLayout';

import AdminTeamProfile from './AdminTeamProfile';

const AdminPage = ({ groupData }: { groupData: ISelectGroupProps }) => {
  // console.log(groupData);

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
        <AdminTeamProfile groupData={groupData} />
      </ContentLayout>
    </>
  );
};

export default AdminPage;
