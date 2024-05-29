import { getUserProblemMonth } from '@services/feed/getProblemMonth';

import { useQuery } from '@tanstack/react-query';

export const useProblemMonth = (yearMonth: string | null) => {
  const { data, isPending, isError, error, refetch } = useQuery({
    queryKey: ['userProblemsMonth', yearMonth],
    queryFn: () =>
      yearMonth ? getUserProblemMonth(yearMonth) : Promise.resolve([]),
  });

  return { data, isPending, isError, error, refetch };
};
