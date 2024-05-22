import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

import styled from '@emotion/styled';

export const StyleCalendar = styled(Calendar)`
  max-width: 100%;
  border: none;
  font-family: 'Noto Sans', sans-serif;
  width: 300px;
  height: 300px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);

  .react-calendar__navigation button {
    color: var(--color-red);
    min-width: 44px;
    background: none;
    font-size: var(--size-md);
    margin-top: 8px;
    border: 1px solid var(--color-byellow);
    border-radius: 8px;
    transition:
      background-color 0.3s,
      transform 0.3s;
  }
  .react-calendar__navigation button:enabled:hover,
  .react-calendar__navigation button:enabled:focus {
    background-color: var(--color-lyellow);
  }
  .react-calendar__navigation button[disabled] {
    background-color: #f0f0f0;
    border-color: #dcdcdc;
  }

  .react-calendar__tile {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: var(--size-xs);
    font-weight: bold;
    height: 41px;
    transition:
      background-color 0.3s,
      transform 0.3s;
  }
  .react-calendar__tile:enabled:hover,
  .react-calendar__tile:enabled:focus {
    background: var(--color-lyellow);
    color: var(--color-red);
    border-radius: 50%;
  }
  .react-calendar__tile--now {
    background: #ffd70033;
    border-radius: 50%;
    font-weight: bold;
    color: var(--color-red);
    border: 1px solid var(--color-byellow);
  }
  .react-calendar__tile--now:enabled:hover,
  .react-calendar__tile--now:enabled:focus {
    background: #ffd70033;
    border-radius: 50%;
    font-weight: bold;
    color: var(--color-red);
  }
  .react-calendar__tile--hasActive:enabled:hover,
  .react-calendar__tile--hasActive:enabled:focus {
    background: var(--color-lyellow);
    border-radius: 50%;
  }
  .react-calendar__tile--active {
    background: var(--color-red);
    border-radius: 50%;
    font-weight: bold;
    color: white;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    transition:
      background-color 0.3s,
      transform 0.3s;
  }
  .react-calendar__tile--active:enabled:hover,
  .react-calendar__tile--active:enabled:focus {
    background: var(--color-red);
    color: var(--color-white);
    border-radius: 50%;
  }
  .react-calendar--selectRange .react-calendar__tile--hover {
    background-color: var(--color-lyellow);
  }

  .react-calendar__month-view {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  }

  .react-calendar__month-view__weekdays {
    abbr {
      font-size: var(--size-md);
      font-weight: 700;
      text-decoration: none;
      color: var(--color-red);
    }
  }

  .react-calendar__tile--dot {
    position: relative;
  }

  .react-calendar__tile--dot::after {
    content: '';
    position: absolute;
    top: 9px;
    right: 50%;
    width: 6px;
    height: 6px;
    background-color: var(--color-red);
    border-radius: 50%;
    transform: translateX(-50%);
  }
`;
