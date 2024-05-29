import { getUserData } from '@services/feed/getUserData';

import { useQuery } from '@tanstack/react-query';

export const useUserData = () => {
  const { data, isPending, isError, error } = useQuery({
    queryKey: ['users'],
    queryFn: getUserData,
  });

  return { data, isPending, isError, error };
};
