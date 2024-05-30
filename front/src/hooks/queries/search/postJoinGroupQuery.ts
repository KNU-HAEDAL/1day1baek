import { queryClient } from '@hooks/queries/Http';

import { postJoinGroup } from '@services/search/postJoinGroup';

import { useMutation } from '@tanstack/react-query';

export const useJoinGroupQuery = () => {
  const { mutate, isPending, isError, error } = useMutation({
    mutationFn: (data: { groupId: string; password: string }) =>
      postJoinGroup(data.groupId, data.password),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['join-group'] });
    },
  });

  return { mutate, isPending, isError, error };
};
