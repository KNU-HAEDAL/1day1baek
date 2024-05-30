import { getSelectGroup } from '@services/group/getSelectGroup';

import { useQuery } from '@tanstack/react-query';

export const useSelectGroupData = (groupId: string | undefined) => {
  const { data, isPending, isError, error, refetch } = useQuery({
    queryKey: ['select-group'],
    queryFn: () => getSelectGroup(groupId),
  });

  return { data, isPending, isError, error, refetch };
};
