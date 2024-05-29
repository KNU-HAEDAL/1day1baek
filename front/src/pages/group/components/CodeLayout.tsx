import dayjs from 'dayjs';

import { StyleCalendar } from '@pages/feed/components/Calendar';

import { useFeedStore } from '@stores/useFeedStore';

import styled from '@emotion/styled';
import 'dayjs/locale/ko';

const ProfileLayout = styled.div`
  height: 100%;
  width: calc(100% - 150px);
  border: 1px solid var(--color-gray);
  overflow: hidden;
`;

const CodeLayout = () => {
  const { selectedDate, setSelectedDate } = useFeedStore();

  const formatDay = (_locale: string | undefined, date: Date) =>
    dayjs(date).format('D');

  const handleDateClick = (date: Date) => {
    setSelectedDate(date);
  };

  return (
    <ProfileLayout>
      <StyleCalendar
        formatDay={formatDay}
        calendarType='iso8601'
        onClickDay={handleDateClick}
      />
    </ProfileLayout>
  );
};

export default CodeLayout;
