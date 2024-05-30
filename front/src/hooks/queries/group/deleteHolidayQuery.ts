import { queryClient } from '@hooks/queries/Http';

import { deleteHoliday } from '@services/group/deleteHoliday';

import { IGroupHolidayProps } from '@interfaces/GroupInterface';

import { useMutation } from '@tanstack/react-query';

export const useDeleteHoliday = () => {
  const { mutate, isPending, isError, error } = useMutation({
    mutationFn: ({
      groupId,
      data,
    }: {
      groupId: string | undefined;
      data: IGroupHolidayProps;
    }) => deleteHoliday(groupId, data),
    onSuccess: () => {
      // console.log('Query invalidated');
      queryClient.invalidateQueries({ queryKey: ['groupHolidays'] });
    },
  });

  return { mutate, isPending, isError, error };
};
