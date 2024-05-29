import { queryClient } from '@hooks/queries/Http';

import { postLeaveMyGroup } from '@services/group/postLeaveGroup';

import { useMutation } from '@tanstack/react-query';

export const postLeaveMyGroupQuery = () => {
  const { mutate, isPending, isError, error } = useMutation({
    mutationFn: postLeaveMyGroup,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['leave-group'] });
    },
  });

  return { mutate, isPending, isError, error };
};
