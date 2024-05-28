import { ReactNode } from 'react';
import { Toaster, ToastBar } from 'react-hot-toast';
import { BiCheckCircle, BiErrorCircle } from 'react-icons/bi';

import styled from '@emotion/styled';

interface IToastProviderProps {
  children: ReactNode;
}

const StyledToastBar = styled(ToastBar)`
  background-color: #ffffff;
  color: #333333;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-top: 8px;
`;

const ToastProvider = ({ children }: IToastProviderProps) => {
  return (
    <>
      <Toaster
        position='top-center'
        reverseOrder={false}
        gutter={8}
        toastOptions={{
          success: {
            style: {
              background: 'var(--color-green)',
              color: 'white',
              boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
              fontSize: 'var(--size-sm)',
            },
            icon: <BiCheckCircle size={20} color='var(--color-white)' />,
          },
          error: {
            style: {
              background: 'var(--color-red)',
              color: 'var(--color-white)',
              boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
              border: '1px solid var(--color-red)',
              fontSize: 'var(--size-sm)',
            },
            icon: <BiErrorCircle size={20} color='var(--color-white)' />,
          },
        }}
      >
        {(t) => <StyledToastBar toast={t} />}
      </Toaster>
      {children}
    </>
  );
};

export default ToastProvider;
