import { useForm } from 'react-hook-form';

import {
  CreateGroupSchema,
  CreateGroupSchemaType,
} from '@utils/CreateGroupSchema';

import { ICreateGroupModalProps } from '@interfaces/GroupInterface';

import styled from '@emotion/styled';
import { ErrorMessage } from '@hookform/error-message';
import { zodResolver } from '@hookform/resolvers/zod';

const ModalContainer = styled.div`
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

const ModalTitle = styled.h2`
  font-size: var(--size-md);
  margin-bottom: 20px;
`;

const Error = styled.small`
  color: var(--color-red);
  font-size: var(--size-xs);
  margin: 0px 0px 12px 6px;
`;

const FormGroup = styled.div`
  margin-bottom: 20px;
`;

const InputLabel = styled.label`
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  font-size: var(--size-xs);
`;

const InputField = styled.input`
  width: calc(100% - 16px);
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-top: 5px;
`;

const CheckboxLabel = styled.label`
  display: flex;
  align-items: center;
  margin-top: 5px;
  font-size: var(--size-sm);
`;

const CheckboxInput = styled.input`
  margin-right: 5px;
`;

const Button = styled.button`
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  background-color: var(--color-blue);
  color: white;
  cursor: pointer;
`;

const CloseButton = styled.button`
  position: absolute;
  top: 10px;
  right: 10px;
  padding: 5px;
  border: none;
  border-radius: 50%;
  background-color: transparent;
  cursor: pointer;
`;

const CreateModal = ({ closeModal }: { closeModal: () => void }) => {
  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm<CreateGroupSchemaType>({
    resolver: zodResolver(CreateGroupSchema),
  });
  const onSubmit = (data: ICreateGroupModalProps) => {
    const isConfirmed = window.confirm('회원가입을 완료하시겠습니까?');
    if (isConfirmed) {
      console.log(data);
    }
    // console.log(data);
    closeModal();
  };

  return (
    <ModalContainer>
      <ModalTitle>Create Group</ModalTitle>
      <CloseButton onClick={closeModal}>X</CloseButton>
      <form onSubmit={handleSubmit(onSubmit)}>
        <FormGroup>
          <InputLabel>Group Name</InputLabel>
          <InputField
            type='text'
            placeholder='그룹명을 입력해주세요'
            {...register('name')}
          />
          <ErrorMessage
            errors={errors}
            name='name'
            render={({ message }: { message: string }) => (
              <Error role='alert'>{message}</Error>
            )}
          />
        </FormGroup>
        <FormGroup>
          <CheckboxLabel>
            <CheckboxInput type='checkbox' {...register('isPrivate')} />
            Private
          </CheckboxLabel>
        </FormGroup>
        <FormGroup>
          <InputLabel>Password</InputLabel>
          <InputField
            type='password'
            placeholder='password를 입력해주세요'
            {...register('password')}
          />
        </FormGroup>
        <FormGroup>
          <InputLabel>Goal Solve Count</InputLabel>
          <InputField
            type='text'
            placeholder='숫자 형식만 입력가능합니다.'
            {...register('goalSolveCount', { valueAsNumber: true })}
          />
          <ErrorMessage
            errors={errors}
            name='goalSolveCount'
            render={({ message }: { message: string }) => (
              <Error role='alert'>{message}</Error>
            )}
          />
        </FormGroup>
        <FormGroup>
          <InputLabel>Fine</InputLabel>
          <InputField
            type='text'
            placeholder='숫자 형식만 입력가능합니다.'
            {...register('fine', { valueAsNumber: true })}
          />
          <ErrorMessage
            errors={errors}
            name='fine'
            render={({ message }: { message: string }) => (
              <Error role='alert'>{message}</Error>
            )}
          />
        </FormGroup>
        <Button type='submit'>Save</Button>
      </form>
    </ModalContainer>
  );
};

export default CreateModal;
