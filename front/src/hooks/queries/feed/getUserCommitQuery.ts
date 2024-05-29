import { getUserCommit } from '@services/feed/getUserCommit';

import { useQuery } from '@tanstack/react-query';

export const useCommits = () => {
  const { data, isPending, isError, error } = useQuery({
    queryKey: ['user_commit'],
    queryFn: getUserCommit,
    refetchOnWindowFocus: false,
    refetchOnMount: false,
    refetchOnReconnect: false,
  });

  return { data, isPending, isError, error };
};
