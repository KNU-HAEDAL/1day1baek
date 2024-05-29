import { useParams } from 'react-router-dom';

import dayjs from 'dayjs';

import Text from '@components/typography/Text';

import { StyleCalendar } from '@pages/feed/components/Calendar';
import CodeProblemList from '@pages/group/components/CodeProblemList';

import { useGroupProblem } from '@hooks/queries/group/getGroupProblem';

import { useFeedStore } from '@stores/useFeedStore';

import {
  IGroupProblemProps,
  IGroupTeamProps,
} from '@interfaces/GroupInterface';

import { useGroupProblemMonth } from '@/hooks/queries/group/getGroupProblemMonthQuery';
import styled from '@emotion/styled';
import 'dayjs/locale/ko';

const ProfileLayout = styled.div`
  height: 100%;
  width: calc(100% - 150px);
  border: 1px solid var(--color-gray);
  overflow: hidden;
`;

const CalendarLayout = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
`;

const HolidayLayout = styled.div`
  width: 330px;
  height: 100%;
`;

const CodeLayout = () => {
  const { selectedDate, setSelectedDate } = useFeedStore();
  const { groupId } = useParams();

  const formattedMonth = selectedDate
    ? dayjs(selectedDate).format('YYYY-MM')
    : dayjs().format('YYYY-MM');

  const formattedDate = selectedDate
    ? dayjs(selectedDate).format('YYYY-MM-DD')
    : dayjs().format('YYYY-MM-DD');

  const { data: groupProblemMonth } = useGroupProblemMonth(
    groupId,
    formattedMonth
  );
  const { data: groupProblem } = useGroupProblem(groupId, formattedDate);
  // console.log(groupProblemMonth);

  const formatDay = (_locale: string | undefined, date: Date) =>
    dayjs(date).format('D');

  const handleDateClick = (date: Date) => {
    setSelectedDate(date);
  };

  return (
    <ProfileLayout>
      <CalendarLayout>
        <StyleCalendar
          formatDay={formatDay}
          calendarType='iso8601'
          onClickDay={handleDateClick}
          tileContent={({ date, view }) => {
            if (view !== 'month' || !groupProblemMonth) {
              return null;
            }

            const dateString = dayjs(date).format('YYYY-MM-DD');
            const hasProblemOnDate = groupProblemMonth.some(
              (user: IGroupTeamProps) =>
                user.problems.some(
                  (problem: IGroupProblemProps) =>
                    problem.solvedDate === dateString
                )
            );

            return hasProblemOnDate ? (
              <div className='react-calendar__tile--dot'></div>
            ) : null;
          }}
        />
        <HolidayLayout>
          <Text size='var(--size-md)' color='var(--color-black)' weight='700'>
            지정 휴일
          </Text>
        </HolidayLayout>
      </CalendarLayout>
      {selectedDate && groupProblem ? (
        <CodeProblemList
          problems={groupProblem}
          formattedDate={formattedDate}
        />
      ) : null}
    </ProfileLayout>
  );
};

export default CodeLayout;
