import { getGroupFMonth } from '@services/group/getGroupFMonth';

import { useQuery } from '@tanstack/react-query';

export const useFineMonth = (
  groupId: string | undefined,
  yearMonth: string
) => {
  const { data, isPending, isError, error, refetch } = useQuery({
    queryKey: ['groupFine', groupId, yearMonth],
    queryFn: () =>
      yearMonth ? getGroupFMonth(groupId, yearMonth) : Promise.resolve([]),
  });

  return { data, isPending, isError, error, refetch };
};
