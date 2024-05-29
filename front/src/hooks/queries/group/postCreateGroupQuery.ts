import { queryClient } from '@hooks/queries/Http';

import { postCreateGroup } from '@services/group/postCreateGroup';

import { useMutation } from '@tanstack/react-query';

export const useCreateGroup = () => {
  const { mutate, isPending, isError, error } = useMutation({
    mutationFn: postCreateGroup,
    onSuccess: () => {
      console.log('Query invalidated');
      queryClient.invalidateQueries({ queryKey: ['users-group'] });
    },
  });

  return { mutate, isPending, isError, error };
};
