import { useForm } from 'react-hook-form';

import { useMyGroupData } from '@hooks/queries/group/getMyGroupQuery';
import { useCreateGroup } from '@hooks/queries/group/postCreateGroupQuery';
import { useGroups } from '@hooks/queries/search/getGroupsQuery';

import {
  CreateGroupSchema,
  CreateGroupSchemaType,
} from '@utils/CreateGroupSchema';

import { ICreateGroupModalProps } from '@interfaces/GroupInterface';

import {
  ModalContainer,
  ModalTitle,
  FormGroup,
  InputLabel,
  InputField,
  Error,
  Button,
  CloseButton,
} from '@styles/ModalLayout';

import styled from '@emotion/styled';
import { ErrorMessage } from '@hookform/error-message';
import { zodResolver } from '@hookform/resolvers/zod';

const CheckboxLabel = styled.label`
  display: flex;
  align-items: center;
  margin-top: 5px;
  font-size: var(--size-sm);
`;

const CheckboxInput = styled.input`
  margin-right: 5px;
`;

const CreateModal = ({ closeModal }: { closeModal: () => void }) => {
  const {
    register,
    formState: { errors },
    handleSubmit,
    watch,
  } = useForm<CreateGroupSchemaType>({
    resolver: zodResolver(CreateGroupSchema),
  });

  const watchIsPrivate = watch('isPrivate', false);

  const { mutate: createGroup } = useCreateGroup();
  const { refetch: refreshGroupData } = useMyGroupData();
  const { refetch: refreshGroupsData } = useGroups();

  const onSubmit = (data: ICreateGroupModalProps) => {
    const isConfirmed = window.confirm('그룹 생성을 완료하시겠습니까?');
    if (isConfirmed) {
      createGroup(data, {
        onSuccess: () => {
          closeModal();
          refreshGroupData();
          refreshGroupsData();
        },
      });
    }
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
        {watchIsPrivate && (
          <FormGroup>
            <InputLabel>Password</InputLabel>
            <InputField
              type='password'
              placeholder='password를 입력해주세요'
              {...register('password')}
            />
            <ErrorMessage
              errors={errors}
              name='password'
              render={({ message }: { message: string }) => (
                <Error role='alert'>{message}</Error>
              )}
            />
          </FormGroup>
        )}
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
        <Button type='submit'>그룹 만들기</Button>
      </form>
    </ModalContainer>
  );
};

export default CreateModal;
