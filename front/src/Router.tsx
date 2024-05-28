import { createBrowserRouter } from 'react-router-dom';

import ErrorPage from '@pages/error/ErrorPage';
import FeedPage from '@pages/feed/FeedPage';
import GroupMemberPage from '@pages/group/GroupMemberPage';
import GroupPage from '@pages/group/GroupPage';
import GroupSelectionPage from '@pages/group/GroupSelectionPage';
import LoginPage from '@pages/login/LoginPage';
import LoginRedirectPage from '@pages/login/LoginRedirectPage';
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
      { path: 'search', element: <SearchPage /> },
      { path: 'login', element: <LoginPage /> },
      {
        path: 'group',
        children: [
          { index: true, element: <GroupSelectionPage /> },
          { path: ':groupId', element: <GroupPage /> },
          { path: ':groupId/:userName', element: <GroupMemberPage /> },
        ],
      },
    ],
  },
  {
    path: '/login/code',
    element: <LoginRedirectPage />,
  },
]);
