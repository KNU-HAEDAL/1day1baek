import { useParams } from 'react-router-dom';

import dayjs from 'dayjs';

import { DefaultButton } from '@components/button/DefaultButton';
import Text from '@components/typography/Text';

import { StyleCalendar } from '@pages/feed/components/Calendar';
import CodeProblemList from '@pages/group/components/CodeProblemList';
import HolidayDeleteModal from '@pages/group/components/HolidayDeleteModal';
import HolidayModal from '@pages/group/components/HolidayModal';

import { useFineMonth } from '@hooks/queries/group/getFineMonthQuery';
import { useGroupProblem } from '@hooks/queries/group/getGroupProblem';
import { useGroupProblemMonth } from '@hooks/queries/group/getGroupProblemMonthQuery';
import { useHolidayMonth } from '@hooks/queries/group/getHolidayMonthQuery';

import { useFeedStore } from '@stores/useFeedStore';
import { useHolidayModalStore } from '@stores/useHolidayModal';

import {
  IGroupProblemProps,
  IGroupTeamProps,
} from '@interfaces/GroupInterface';

import {
  ProfileLayout,
  CalendarLayout,
  HolidayLayout,
} from '@styles/GroupCodeLayout';

import styled from '@emotion/styled';
import 'dayjs/locale/ko';

const MakeButton = styled(DefaultButton)`
  width: 90px;
  height: 30px;
  background-color: var(--color-red);
  color: var(--color-white);
  font-size: var(--size-sm);
  border-radius: 20px;
  padding: 0;
  margin: 10px 0;

  &:hover {
    color: var(--color-black);
    background-color: var(--color-byellow);
  }
  &:focus {
    background-color: var(--color-red);
    color: var(--color-white);
  }
`;

const AdminCodeLayout = () => {
  const { selectedDate, setSelectedDate } = useFeedStore();
  const { isModalOpen, isDeleteModalOpen, toggleModal, toggleDeleteModal } =
    useHolidayModalStore();
  const { groupId } = useParams();

  // console.log(groupHoliday);

  const changeStateModal = () => {
    toggleModal();
  };

  const changeStateDeleteModal = () => {
    toggleDeleteModal();
  };

  const formattedMonth = selectedDate
    ? dayjs(selectedDate).format('YYYY-MM')
    : dayjs().format('YYYY-MM');

  const formattedDate = selectedDate
    ? dayjs(selectedDate).format('YYYY-MM-DD')
    : dayjs().format('YYYY-MM-DD');

  const { data: groupHoliday } = useHolidayMonth(groupId, formattedMonth);
  const { data: groupFine } = useFineMonth(groupId, formattedMonth);

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
          <Text size='var(--size-sm)' color='var(--color-black)' weight='700'>
            {formattedMonth}의 휴일
          </Text>
          {Array.isArray(groupHoliday) && groupHoliday.length > 0 ? (
            <Text size='var(--size-xs)'>
              해당 월에는 {groupHoliday.join(', ')}입니다.
            </Text>
          ) : (
            <Text size='var(--size-xs)'>해당 월에는 휴일이 없습니다.</Text>
          )}
          <MakeButton onClick={changeStateModal}>휴일 생성하기</MakeButton>
          <MakeButton onClick={changeStateDeleteModal}>
            휴일 삭제하기
          </MakeButton>
          <Text size='var(--size-sm)' color='var(--color-black)' weight='700'>
            {formattedMonth} 벌금 명단 현황
          </Text>
          {Array.isArray(groupFine) && groupFine.length > 0 ? (
            groupFine.map((fine) => (
              <Text
                key={fine.user.loginId}
                size='var(--size-xs)'
                color='var(--color-black)'
              >
                {fine.user.name}: {fine.fine}원
              </Text>
            ))
          ) : (
            <Text size='var(--size-xs)'>해당 월에는 벌금 명단이 없습니다.</Text>
          )}
        </HolidayLayout>
      </CalendarLayout>
      {selectedDate && groupProblem ? (
        <CodeProblemList
          problems={groupProblem}
          formattedDate={formattedDate}
        />
      ) : null}
      {isModalOpen && <HolidayModal closeModal={changeStateModal} />}
      {isDeleteModalOpen && (
        <HolidayDeleteModal closeModal={changeStateDeleteModal} />
      )}
    </ProfileLayout>
  );
};

export default AdminCodeLayout;
