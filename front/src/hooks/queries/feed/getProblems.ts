import { getUserProblems } from '@services/feed/getProblems';

import { useQuery } from '@tanstack/react-query';

export const useProblem = () => {
  const { data, isPending, isError, error } = useQuery({
    queryKey: ['userProblems'],
    queryFn: getUserProblems,
    refetchOnMount: false,
  });

  return { data, isPending, isError, error };
};
