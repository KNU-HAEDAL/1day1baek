import { useEffect } from 'react';
import toast from 'react-hot-toast';
import { useSearchParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

import { useTokenStore } from '@stores/useTokenStore';

const LoginRedirectPage = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const { setIsAccessToken } = useTokenStore();

  useEffect(() => {
    const accessToken = searchParams.get('access');
    const refreshToken = searchParams.get('refresh');

    if (accessToken) {
      localStorage.setItem('aId', accessToken);
    }
    if (refreshToken) {
      localStorage.setItem('rId', refreshToken);
    }

    setIsAccessToken(localStorage.getItem('aId') !== null);
    navigate('/');
    toast.success('환영합니다! 김규회님!');
  }, [searchParams, navigate, setIsAccessToken]);

  return <></>;
};

export default LoginRedirectPage;
