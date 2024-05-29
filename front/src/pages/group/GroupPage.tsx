import { useParams } from 'react-router-dom';

import AdminPage from '@pages/group/components/AdminPage';
import UserPage from '@pages/group/components/UserPage';

import { useUserData } from '@hooks/queries/feed/getUserDataQuery';
import { useSelectGroupData } from '@hooks/queries/group/getSelectGroupQuery';

import { Layout, DisplayLayout } from '@styles/Layout';

import styled from '@emotion/styled';

const GroupLayout = styled(DisplayLayout)`
  height: calc(100vh - 45px);
  display: flex;
  flex-direction: column;
`;

const GroupPage = () => {
  const { groupId } = useParams();
  const { data: groupData } = useSelectGroupData(groupId);
  const { data: userData } = useUserData();

  if (!groupData || !userData) {
    return null;
  }

  if (userData.loginId === groupData.owner.loginId) {
    return (
      <Layout>
        <GroupLayout>
          <AdminPage groupData={groupData} />
        </GroupLayout>
      </Layout>
    );
  } else {
    return (
      <Layout>
        <GroupLayout>
          <UserPage groupData={groupData} />
        </GroupLayout>
      </Layout>
    );
  }
};

export default GroupPage;
