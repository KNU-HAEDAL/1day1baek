import Text from '@components/typography/Text';

import GithubButton from '@pages/login/components/GithubButton';

import { DisplayLayout, Layout } from '@styles/Layout';

import styled from '@emotion/styled';

const LoginDisplayLayout = styled(DisplayLayout)`
  align-items: center;
  flex-direction: column;
  height: calc(100vh - 45px);
`;

const LoginText = styled(Text)`
  margin-bottom: 20px;
`;

const LoginPage = () => {
  return (
    <Layout>
      <LoginDisplayLayout>
        <LoginText
          color='var(--color-black)'
          size='var(--size-md)'
          weight='600'
        >
          로그인 후 서비스 이용이 가능하십니다.
        </LoginText>
        <GithubButton />
      </LoginDisplayLayout>
    </Layout>
  );
};

export default LoginPage;
