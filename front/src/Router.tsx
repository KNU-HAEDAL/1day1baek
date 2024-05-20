import { createBrowserRouter } from 'react-router-dom';

import ErrorPage from '@pages/error/ErrorPage';
import FeedPage from '@pages/feed/FeedPage';
import GroupPage from '@pages/group/GroupPage';
import GroupSelectionPage from '@pages/group/GroupSelectionPage';
import MyPage from '@pages/mypage/MyPage';
import RootPage from '@pages/root/RootPage';
import SearchPage from '@pages/search/SearchPage';

export const Router = createBrowserRouter([
  {
    path: '/',
    element: <RootPage />,
    id: 'root',
    errorElement: <ErrorPage />,
    children: [
      { index: true, path: '', element: <FeedPage /> },
      { path: 'mypage', element: <MyPage /> },
      { path: 'search', element: <SearchPage /> },
      {
        path: 'group',
        children: [
          { index: true, element: <GroupSelectionPage /> },
          { path: ':groupName', element: <GroupPage /> },
        ],
      },
    ],
  },
]);
