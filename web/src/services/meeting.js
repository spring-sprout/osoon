import request from './request';

export const createMeeting = (data) => {
  return request.post('/api/meeting/create', data);
}

