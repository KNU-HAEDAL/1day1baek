import toast from 'react-hot-toast';
import { BiMailSend, BiLogIn, BiLogOut } from 'react-icons/bi';

import InviteCodeModal from '@components/header/components/InviteCodeModal';
import LinkText from '@components/typography/LinkText';

import HaedalText from '@assets/HaedalText.png';

import { useInviteModalStore } from '@stores/useInviteModal';
import { useTokenStore } from '@stores/useTokenStore';

import styled from '@emotion/styled';

const MainNavigateDiv = styled.div`
  width: 100%;
  height: 45px;
  display: flex;
  justify-content: center;
  border-bottom: 2px solid var(--color-gray);
`;

const MainNavigateContainer = styled.nav`
  max-width: 1024px;
  min-width: 568px;
  width: calc(100% - 124px);
  height: 100%;
  padding: 0 62px 0 62px;
  display: flex;
  justify-content: space-between;
`;

const MainNavigateList = styled.ul`
  display: flex;
  flex-direction: row;
  align-items: center;
  list-style: none;
  margin: 0;
  padding: 0;
`;

const MainNavigateLogo = styled.li`
  margin-right: 10px;
`;

const MainNavigateItem = styled.li`
  margin-right: 10px;
  cursor: pointer;
`;

const LogoImg = styled.img`
  width: 45px;
`;

const MainNavigation = () => {
  const { isAccessToken, setIsAccessToken } = useTokenStore();
  const { isModalOpen, toggleModal } = useInviteModalStore();

  const changeStateModal = () => {
    toggleModal();
  };

  const onClickLogout = () => {
    localStorage.removeItem('aId');
    localStorage.removeItem('rId');
    setIsAccessToken(false);
    toast.success('로그아웃 되었습니다.');
  };

  return (
    <MainNavigateDiv>
      <MainNavigateContainer>
        <MainNavigateList>
          <MainNavigateLogo>
            <LogoImg src={HaedalText} alt='logo' />
          </MainNavigateLogo>
          <MainNavigateItem>
            <LinkText
              to='/'
              size='var(--size-md)'
              color='var(--color-black)'
              weight='600'
            >
              피드
            </LinkText>
          </MainNavigateItem>
          <MainNavigateItem>
            <LinkText
              to='/search'
              size='var(--size-md)'
              color='var(--color-black)'
              weight='600'
            >
              둘러보기
            </LinkText>
          </MainNavigateItem>
          <MainNavigateItem>
            <LinkText
              to='/group'
              size='var(--size-md)'
              color='var(--color-black)'
              weight='600'
            >
              그룹관리
            </LinkText>
          </MainNavigateItem>
        </MainNavigateList>
        <MainNavigateList>
          <MainNavigateItem>
            <BiMailSend
              size={20}
              aria-label='notifications'
              onClick={changeStateModal}
            />
          </MainNavigateItem>
          {isAccessToken ? (
            <MainNavigateItem>
              <BiLogOut size={21} aria-label='logout' onClick={onClickLogout} />
            </MainNavigateItem>
          ) : (
            <MainNavigateItem>
              <LinkText to='/login' color='var(--color-black)'>
                <BiLogIn
                  size={21}
                  aria-label='login'
                  data-testid='logout-icon'
                />
              </LinkText>
            </MainNavigateItem>
          )}
        </MainNavigateList>
      </MainNavigateContainer>
      {isModalOpen && <InviteCodeModal closeModal={changeStateModal} />}
    </MainNavigateDiv>
  );
};

export default MainNavigation;
