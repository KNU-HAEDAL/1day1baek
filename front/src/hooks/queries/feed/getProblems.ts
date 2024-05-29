import { getUserProblems } from '@services/feed/getProblems';

import { useQuery } from '@tanstack/react-query';

export const useProblem = (date: string | null) => {
  const { data, isPending, isError, error, refetch } = useQuery({
    queryKey: ['userProblems', date],
    queryFn: () => (date ? getUserProblems(date) : Promise.resolve([])),
  });

  return { data, isPending, isError, error, refetch };
};
