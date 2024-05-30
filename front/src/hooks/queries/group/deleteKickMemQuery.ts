import { queryClient } from '@hooks/queries/Http';

import { deleteKickMem } from '@services/group/deleteKickMem';

import { useMutation } from '@tanstack/react-query';

export const useDeleteKickMem = () => {
  const { mutate, isPending, isError, error } = useMutation({
    mutationFn: ({
      groupId,
      targetUserId,
    }: {
      groupId: string | undefined;
      targetUserId: string | undefined;
    }) => deleteKickMem(groupId, targetUserId),
    onSuccess: () => {
      // console.log('Query invalidated');
      queryClient.invalidateQueries({ queryKey: ['kickMember'] });
    },
  });

  return { mutate, isPending, isError, error };
};
