import { http, HttpResponse } from 'msw';

export const SearchHandler = [
  http.get('/api/groups', () => {
    return HttpResponse.json({
      id: 1,
      name: '해달 파이팅',
      isPrivate: false,
    });
  }),
];
