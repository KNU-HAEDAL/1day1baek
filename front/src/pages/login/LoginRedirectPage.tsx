import { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';

const LoginRedirectPage = () => {
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const accessToken = searchParams.get('access');
    const refreshToken = searchParams.get('refresh');

    if (accessToken) {
      localStorage.setItem('aId', accessToken);
    }
    if (refreshToken) {
      localStorage.setItem('rId', refreshToken);
    }
  }, [searchParams]);

  return <></>;
};

export default LoginRedirectPage;
