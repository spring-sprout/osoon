import request from './request';

export const createMeeting = (data) => {
  return request.post('/api/meeting/create', data);
}

export const fetchMeeting = (id) => {
  return request.get(`/api/meeting/${id}`);
}
