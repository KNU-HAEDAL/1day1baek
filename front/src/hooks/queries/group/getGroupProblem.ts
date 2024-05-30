import { getGroupProblem } from '@services/group/getGroupProblem';

import { useQuery } from '@tanstack/react-query';

export const useGroupProblem = (
  groupId: string | undefined,
  date: string | null
) => {
  const { data, isPending, isError, error, refetch } = useQuery({
    queryKey: ['userProblems', groupId, date],
    queryFn: () =>
      date ? getGroupProblem(groupId, date) : Promise.resolve([]),
  });

  return { data, isPending, isError, error, refetch };
};
