import { queryClient } from '@hooks/queries/Http';

import { postHoliday } from '@services/group/postHoliday';

import { IGroupHolidayProps } from '@interfaces/GroupInterface';

import { useMutation } from '@tanstack/react-query';

export const useCreateHoliday = () => {
  const { mutate, isPending, isError, error } = useMutation({
    mutationFn: ({
      groupId,
      data,
    }: {
      groupId: string | undefined;
      data: IGroupHolidayProps;
    }) => postHoliday(groupId, data),
    onSuccess: () => {
      // console.log('Query invalidated');
      queryClient.invalidateQueries({ queryKey: ['groupHolidays'] });
    },
  });

  return { mutate, isPending, isError, error };
};
