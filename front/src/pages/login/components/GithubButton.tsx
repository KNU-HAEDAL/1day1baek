import { BiLogoGithub } from 'react-icons/bi';

import { DefaultButton } from '@components/button/DefaultButton';

import { BASE_URI } from '@constants/URI';

import styled from '@emotion/styled';

const GithubStyledButton = styled(DefaultButton)`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 420px;
  height: 40px;
  border-radius: 12px;
  background-color: var(--color-black);
  color: var(--color-white);
  gap: 8px;

  &:hover {
    background-color: var(--color-black);
    color: var(--color-white);
  }

  &:focus {
    outline: none;
    background-color: var(--color-black);
  }
`;

const GithubButton = () => {
  const onClickLogin = () => {
    window.location.href = `${BASE_URI}/oauth2/authorization/github`;
  };

  return (
    <GithubStyledButton onClick={onClickLogin}>
      <BiLogoGithub size={24} />
      github로 로그인하기
    </GithubStyledButton>
  );
};

export default GithubButton;
