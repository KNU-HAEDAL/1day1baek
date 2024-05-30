import 'react-calendar/dist/Calendar.css';

import dayjs from 'dayjs';

import Text from '@components/typography/Text';

import { StyleCalendar } from '@pages/feed/components/Calendar';
import ProblemList from '@pages/feed/components/ProblemList';
import Profile from '@pages/feed/components/Profile';

import { useProblem } from '@hooks/queries/feed/getProblems';

import { useFeedStore } from '@stores/useFeedStore';
import { useTokenStore } from '@stores/useTokenStore';

import { IProblem } from '@interfaces/ProblemInterface';

import { Layout, DisplayLayout, LoginLayout } from '@styles/Layout';

import { useProblemMonth } from '@/hooks/queries/feed/getProblemMonth';
import styled from '@emotion/styled';
import 'dayjs/locale/ko';

// const problemRecords: IProblem[] = [
//   {
//     solvedDate: '2024-05-20',
//     commitUrl: 'https://github.com/gidskql6671',
//     title: '토마토',
//     rank: 'gold4',
//   },
//   {
//     solvedDate: '2024-05-20',
//     commitUrl: 'https://github.com/gidskql6671',
//     title: '사과',
//     rank: 'gold2',
//   },
//   {
//     solvedDate: '2024-05-20',
//     commitUrl: 'https://github.com/gidskql6671',
//     title: '감자',
//     rank: 'gold4',
//   },
//   {
//     solvedDate: '2024-05-20',
//     commitUrl: 'https://github.com/gidskql6671',
//     title: '감자',
//     rank: 'gold4',
//   },
//   {
//     solvedDate: '2024-05-20',
//     commitUrl: 'https://github.com/gidskql6671',
//     title: '감자',
//     rank: 'gold4',
//   },
//   {
//     solvedDate: '2024-05-20',
//     commitUrl: 'https://github.com/gidskql6671',
//     title: '감자',
//     rank: 'gold4',
//   },
// ];

const FeedLayout = styled.div`
  margin-top: 62px;
  display: grid;
  grid-template-columns: 1fr;
  column-gap: 15px;
  row-gap: 20px;

  @media (min-width: 800px) {
    grid-template-columns: 1fr 1fr;
    grid-template-rows: auto;
  }
`;

const CalendarLayout = styled.div`
  display: flex;
  height: 473px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const ProfileWrapper = styled.div`
  display: flex;
  justify-content: left;
  align-items: left;
  margin-bottom: 20px;
`;

const FeedPage = () => {
  const { isAccessToken } = useTokenStore();
  const { selectedDate, setSelectedDate } = useFeedStore();
  const formattedDate = selectedDate
    ? dayjs(selectedDate).format('YYYY-MM-DD')
    : dayjs().format('YYYY-MM-DD');

  const formattedMonth = selectedDate
    ? dayjs(selectedDate).format('YYYY-MM')
    : dayjs().format('YYYY-MM');
  const {
    data: problemData,
    isPending: problemPending,
    isError: problemError,
  } = useProblem(formattedDate);
  const { data: problemDataMonth } = useProblemMonth(formattedMonth);

  const formatDay = (_locale: string | undefined, date: Date) =>
    dayjs(date).format('D');

  const handleDateClick = (date: Date) => {
    setSelectedDate(date);
  };

  return (
    <Layout>
      <DisplayLayout>
        {isAccessToken ? (
          <FeedLayout>
            {' '}
            <CalendarLayout>
              <ProfileWrapper>
                <Profile />
              </ProfileWrapper>
              <StyleCalendar
                formatDay={formatDay}
                calendarType='iso8601'
                onClickDay={handleDateClick}
                tileContent={({ date, view }) =>
                  view === 'month' &&
                  problemDataMonth &&
                  problemDataMonth.length > 0 &&
                  problemDataMonth.some(
                    (problem: IProblem) =>
                      problem.solvedDate === dayjs(date).format('YYYY-MM-DD')
                  ) ? (
                    <div className='react-calendar__tile--dot'></div>
                  ) : null
                }
              />
            </CalendarLayout>
            {selectedDate && problemData && !problemPending && !problemError ? (
              <ProblemList
                problems={problemData}
                formattedDate={formattedDate}
              />
            ) : (
              <div style={{ width: 470 }}></div>
            )}
          </FeedLayout>
        ) : (
          <LoginLayout>
            <Text size='var(--size-lg)' weight='700'>
              환영합니다! Guest님! 로그인 후 이용해주세요.
            </Text>
          </LoginLayout>
        )}
      </DisplayLayout>
    </Layout>
  );
};

export default FeedPage;
