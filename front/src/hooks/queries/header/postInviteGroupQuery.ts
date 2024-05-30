import { queryClient } from '@hooks/queries/Http';

import { postInviteGroup } from '@services/header/postInviteGroup';

import { useMutation } from '@tanstack/react-query';

export const useInviteGroup = () => {
  const { mutate, isPending, isError, error } = useMutation({
    mutationFn: postInviteGroup,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['leave-group'] });
    },
  });

  return { mutate, isPending, isError, error };
};
