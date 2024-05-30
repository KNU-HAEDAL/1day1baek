import { getGroupHMonth } from '@services/group/getGroupHMonth';

import { useQuery } from '@tanstack/react-query';

export const useHolidayMonth = (
  groupId: string | undefined,
  yearMonth: string
) => {
  const { data, isPending, isError, error, refetch } = useQuery({
    queryKey: ['groupHoliday', groupId, yearMonth],
    queryFn: () =>
      yearMonth ? getGroupHMonth(groupId, yearMonth) : Promise.resolve([]),
  });

  return { data, isPending, isError, error, refetch };
};
