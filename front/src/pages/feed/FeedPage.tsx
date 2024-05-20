import { useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

import moment from 'moment';

import { Layout, DisplayLayout } from '@styles/Layout';

interface IProblem {
  date: string;
  problem: string;
  difficulty: string;
  code: string;
}

const problemRecords: IProblem[] = [
  {
    date: '2024-05-20',
    problem: '토마토',
    difficulty: 'gold4',
    code: '해결한 코드',
  },
];

const FeedPage = () => {
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);
  const [showCode, setShowCode] = useState(false);

  const handleDateClick = (date: Date) => {
    setSelectedDate(date);
    setShowCode(false);
  };

  const formattedDate = selectedDate
    ? moment(selectedDate).format('YYYY-MM-DD')
    : '';

  return (
    <Layout>
      <DisplayLayout>
        <Calendar
          onClickDay={handleDateClick}
          tileContent={({ date, view }) =>
            view === 'month' &&
            problemRecords.find(
              (problem) => problem.date === moment(date).format('YYYY-MM-DD')
            ) ? (
              <div className='react-calendar__tile--dot'></div>
            ) : null
          }
        />
        {selectedDate &&
          problemRecords.find((problem) => problem.date === formattedDate) && (
            <div>
              {problemRecords
                .filter((problem) => problem.date === formattedDate)
                .map((problem, index) => (
                  <div key={index}>
                    <button onClick={() => setShowCode((prev) => !prev)}>
                      문제: {problem.problem} 난이도: {problem.difficulty}
                    </button>
                    {showCode && <pre>{problem.code}</pre>}
                  </div>
                ))}
            </div>
          )}
      </DisplayLayout>
    </Layout>
  );
};

export default FeedPage;
