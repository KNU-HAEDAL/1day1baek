import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';

import { useMyGroupData } from '@hooks/queries/group/getMyGroupQuery';
import { useInviteGroup } from '@hooks/queries/header/postInviteGroupQuery';

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

import { ErrorMessage } from '@hookform/error-message';

const InviteCodeModal = ({ closeModal }: { closeModal: () => void }) => {
  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm();

  const navigate = useNavigate();

  const { mutate: inviteGroup } = useInviteGroup();
  const { refetch: refreshGroupData } = useMyGroupData();

  const onSubmit = (data: any) => {
    // console.log(data);
    const isConfirmed = window.confirm(
      '그룹 초대코드 입력을 완료하시겠습니까?'
    );
    if (isConfirmed) {
      inviteGroup(data, {
        onSuccess: () => {
          alert('그룹 초대코드 입력이 완료되었습니다.');
          closeModal();
          refreshGroupData();
          navigate('/group');
        },
      });
    }
  };

  return (
    <ModalContainer>
      <ModalTitle>초대 코드 입력</ModalTitle>
      <CloseButton onClick={closeModal}>X</CloseButton>
      <form onSubmit={handleSubmit(onSubmit)}>
        <FormGroup>
          <InputLabel>Invite Code</InputLabel>
          <InputField
            type='text'
            placeholder='초대 코드를 입력해주세요'
            {...register('inviteCode')}
          />
          <ErrorMessage
            errors={errors}
            name='inviteCode'
            render={({ message }: { message: string }) => (
              <Error role='alert'>{message}</Error>
            )}
          />
        </FormGroup>
        <Button type='submit'>Submit</Button>
      </form>
    </ModalContainer>
  );
};

export default InviteCodeModal;
