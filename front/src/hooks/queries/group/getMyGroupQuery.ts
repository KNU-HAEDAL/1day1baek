import { getMyGroup } from '@services/group/getMyGroups';

import { useQuery } from '@tanstack/react-query';

export const useMyGroupData = () => {
  const { data, isPending, isError, error, refetch } = useQuery({
    queryKey: ['users-group'],
    queryFn: getMyGroup,
  });

  return { data, isPending, isError, error, refetch };
};
