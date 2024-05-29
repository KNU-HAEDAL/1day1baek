import { BiAddToQueue } from 'react-icons/bi';

import Text from '@components/typography/Text';

import CreateModal from '@pages/group/components/CreateModal';
import GroupLists from '@pages/group/components/GroupLists';

import { useTokenStore } from '@stores/useTokenStore';

import { Layout, DisplayLayout, LoginLayout } from '@styles/Layout';

import { useCreateGroupModalStore } from '@/stores/useCreateGroupModal';
import styled from '@emotion/styled';

const SLayout = styled(DisplayLayout)`
  height: calc(100vh - 45px);
  display: flex;
  flex-direction: column;
  position: relative;
`;

const SelectionTitleLayout = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 45px;
  width: 100%;
  height: 40px;
`;

const TeamLayout = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ButtonLayout = styled.div`
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 60px;
  height: 60px;
  bottom: 0;
  right: 0;
  margin-bottom: 60px;
  margin-right: 60px;
  border-radius: 50%;
  background-color: var(--color-red);
  cursor: pointer;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.4);
`;

const GroupSelectionPage = () => {
  const { isAccessToken } = useTokenStore();
  const { isModalOpen, toggleModal } = useCreateGroupModalStore();

  const changeStateModal = () => {
    toggleModal();
  };

  return (
    <Layout>
      {isAccessToken ? (
        <SLayout>
          <SelectionTitleLayout>
            <Text weight='600' size='var(--size-md)'>
              현재 참여하고 있는 팀
            </Text>
          </SelectionTitleLayout>
          <TeamLayout>
            <GroupLists />
          </TeamLayout>
          <ButtonLayout onClick={changeStateModal}>
            <BiAddToQueue
              size={30}
              cursor='pointer'
              color='var(--color-white)'
            />
          </ButtonLayout>
          {isModalOpen && <CreateModal closeModal={changeStateModal} />}
        </SLayout>
      ) : (
        <LoginLayout>
          <Text size='var(--size-lg)' weight='700'>
            로그인후 사용해주세요
          </Text>
        </LoginLayout>
      )}
    </Layout>
  );
};

export default GroupSelectionPage;
