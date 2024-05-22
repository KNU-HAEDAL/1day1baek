import 'react-calendar/dist/Calendar.css';

import dayjs from 'dayjs';

import { StyleCalendar } from '@pages/feed/components/Calendar';
import ProblemList from '@pages/feed/components/ProblemList';
import Profile from '@pages/feed/components/Profile';

import { useFeedStore } from '@stores/useFeedStore';

import { Layout, DisplayLayout } from '@styles/Layout';

import styled from '@emotion/styled';
import 'dayjs/locale/ko';

interface IProblem {
  date: string;
  problem: string;
  difficulty: string;
}

const problemRecords: IProblem[] = [
  {
    date: '2024-05-20',
    problem: '토마토',
    difficulty: 'gold4',
  },
  {
    date: '2024-05-20',
    problem: '사과',
    difficulty: 'gold2',
  },
  {
    date: '2024-05-20',
    problem: '감자',
    difficulty: 'gold4',
  },
  {
    date: '2024-05-20',
    problem: '감자',
    difficulty: 'gold4',
  },
  {
    date: '2024-05-20',
    problem: '감자',
    difficulty: 'gold4',
  },
  {
    date: '2024-05-20',
    problem: '감자',
    difficulty: 'gold4',
  },
];

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
  const { selectedDate, setSelectedDate } = useFeedStore();

  // eslint-disable-next-line @typescript-eslint/naming-convention
  const formatDay = (_locale: string | undefined, date: Date) =>
    dayjs(date).format('D');

  const handleDateClick = (date: Date) => {
    setSelectedDate(date);
  };

  const formattedDate = selectedDate
    ? dayjs(selectedDate).format('YYYY-MM-DD')
    : '';

  return (
    <Layout>
      <DisplayLayout>
        <FeedLayout>
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
                problemRecords.find(
                  (problem) => problem.date === dayjs(date).format('YYYY-MM-DD')
                ) ? (
                  <div className='react-calendar__tile--dot'></div>
                ) : null
              }
            />
          </CalendarLayout>
          {selectedDate &&
          problemRecords.find((problem) => problem.date === formattedDate) ? (
            <ProblemList
              problems={problemRecords}
              formattedDate={formattedDate}
            />
          ) : (
            <div style={{ width: '470px' }} />
          )}
        </FeedLayout>
      </DisplayLayout>
    </Layout>
  );
};

export default FeedPage;
