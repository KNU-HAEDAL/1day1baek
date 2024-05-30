import { useForm } from 'react-hook-form';
import { useParams } from 'react-router-dom';

import dayjs from 'dayjs';

import { useDeleteHoliday } from '@hooks/queries/group/deleteHolidayQuery';

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

import { useHolidayMonth } from '@/hooks/queries/group/getHolidayMonthQuery';
import { useFeedStore } from '@/stores/useFeedStore';
import { ErrorMessage } from '@hookform/error-message';

const HolidayDeleteModal = ({ closeModal }: { closeModal: () => void }) => {
  const { selectedDate } = useFeedStore();
  const formattedMonth = selectedDate
    ? dayjs(selectedDate).format('YYYY-MM')
    : dayjs().format('YYYY-MM');
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const { groupId } = useParams<string>();

  const {
    mutate: deleteHoliday,
    isPending,
    isError,
    error,
  } = useDeleteHoliday();
  const { refetch: refreshMonth } = useHolidayMonth(groupId, formattedMonth);

  const onSubmit = (data: any) => {
    const isConfirmed = window.confirm('휴일을 삭제하시겠습니까?');
    if (isConfirmed && typeof groupId === 'string') {
      const transformedData = {
        groupId: groupId,
        data: {
          holidays: [data.holiday],
        },
      };
      deleteHoliday(transformedData, {
        onSuccess: () => {
          closeModal();
          refreshMonth();
        },
      });
    }
  };

  return (
    <ModalContainer>
      <ModalTitle>휴일 추가</ModalTitle>
      <CloseButton onClick={closeModal}>X</CloseButton>
      <form onSubmit={handleSubmit(onSubmit)}>
        <FormGroup>
          <InputLabel>휴일 선택</InputLabel>
          <InputField
            type='date'
            {...register('holiday', { required: '휴일을 선택해주세요.' })}
          />
          <ErrorMessage
            errors={errors}
            name='holiday'
            render={({ message }: { message: string }) => (
              <Error role='alert'>{message}</Error>
            )}
          />
        </FormGroup>
        <Button type='submit' disabled={isPending}>
          {isPending ? '추가 중...' : '휴일 추가'}
        </Button>
        {isError && (
          <Error role='alert'>에러가 발생했습니다: {error?.message}</Error>
        )}
      </form>
    </ModalContainer>
  );
};

export default HolidayDeleteModal;
