import styled from '@emotion/styled';

export const ModalContainer = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 400px;
`;

export const ModalTitle = styled.h2`
  font-size: var(--size-md);
  margin-bottom: 20px;
`;

export const FormGroup = styled.div`
  margin-bottom: 20px;
`;

export const InputLabel = styled.label`
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  font-size: var(--size-xs);
`;

export const InputField = styled.input`
  width: calc(100% - 16px);
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-top: 5px;
`;

export const Error = styled.small`
  color: var(--color-red);
  font-size: var(--size-xs);
  margin: 0px 0px 12px 6px;
`;

export const Button = styled.button`
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  background-color: var(--color-blue);
  color: white;
  cursor: pointer;
`;

export const CloseButton = styled.button`
  position: absolute;
  top: 10px;
  right: 10px;
  padding: 5px;
  border: none;
  border-radius: 50%;
  background-color: transparent;
  cursor: pointer;
`;
