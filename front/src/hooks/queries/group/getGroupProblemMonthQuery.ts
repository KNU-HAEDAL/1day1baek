import { getGroupProblemMonth } from '@services/group/getGroupProblemMonth';

import { useQuery } from '@tanstack/react-query';

export const useGroupProblemMonth = (
  groupId: string | undefined,
  yearMonth: string | null
) => {
  const { data, isPending, isError, error, refetch } = useQuery({
    queryKey: ['userProblemsMonth', groupId, yearMonth],
    queryFn: () =>
      yearMonth
        ? getGroupProblemMonth(groupId, yearMonth)
        : Promise.resolve([]),
  });

  return { data, isPending, isError, error, refetch };
};
