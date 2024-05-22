import { http, HttpResponse } from 'msw';

export const UserHandler = [
  http.get('/api/profile', () => {
    return HttpResponse.json({
      name: 'KimKyuHoi',
      url: '',
    });
  }),

  http.get('/api/commit', () => {
    return HttpResponse.json({
      commit: 10,
    });
  }),
];
