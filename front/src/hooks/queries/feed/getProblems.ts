import { getUserProblems } from '@services/feed/getProblems';

import { useQuery } from '@tanstack/react-query';

export const useProblem = (date: string | null) => {
  const { data, isPending, isError, error } = useQuery({
    queryKey: ['userProblems', date],
    queryFn: () => (date ? getUserProblems(date) : Promise.resolve([])),
    refetchOnMount: true,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
  });

  return { data, isPending, isError, error };
};
