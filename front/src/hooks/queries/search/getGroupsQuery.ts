import { getGroups } from '@services/search/getGroups';

import { useQuery } from '@tanstack/react-query';

export const useGroups = () => {
  const { data, isPending, isError, error, refetch } = useQuery({
    queryKey: ['users-groups'],
    queryFn: getGroups,
  });

  return { data, isPending, isError, error, refetch };
};
