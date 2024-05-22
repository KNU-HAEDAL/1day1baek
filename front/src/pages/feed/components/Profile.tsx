import { useEffect } from 'react';

import axios from 'axios';

import Text from '@components/typography/Text';

import DefaultProfileImg from '@assets/HaedalProfile.png';

import { useUserDataStore } from '@stores/useUserDataStore';

import styled from '@emotion/styled';

const ProfileLayout = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

const StyledProfileImg = styled.div<{ imgUrl: string }>`
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
  border: 1px solid var(--color-gray);
  background-image: url(${(props) => props.imgUrl});
  background-size: cover;
  background-position: center;
`;

const ProfileTextLayout = styled.div`
  display: flex;
  flex-direction: column;
`;

const Profile = () => {
  const {
    setUsername,
    getUsername,
    setProfileImg,
    getProfileImg,
    setCommit,
    getCommit,
  } = useUserDataStore();
  const username = getUsername();
  const commitNum = getCommit();
  const profileImg = getProfileImg();

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const res = await axios.get('/api/profile');
        setUsername(res.data.name);
        setProfileImg(res.data.url);
        console.log(res.data.url);
      } catch (e) {
        console.error('Error fetching profile:', e);
      }
    };

    fetchProfile();
  }, [setUsername, setProfileImg]);

  useEffect(() => {
    const fetchCommit = async () => {
      try {
        const res = await axios.get('/api/commit');
        setCommit(res.data.commit);
      } catch (e) {
        console.error('Error fetching commit:', e);
      }
    };

    fetchCommit();
  }, [setCommit]);

  return (
    <>
      <ProfileLayout>
        <StyledProfileImg imgUrl={profileImg || DefaultProfileImg} />
        <ProfileTextLayout>
          <Text size='var(--size-sm)' weight='600'>
            {username}님 반갑습니다!
          </Text>
          <Text size='var(--size-xs)' weight='600'>
            오늘의 커밋 개수는 {commitNum}개 입니다.
          </Text>
        </ProfileTextLayout>
      </ProfileLayout>
    </>
  );
};

export default Profile;
