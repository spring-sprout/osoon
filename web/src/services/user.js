import request from './request';

export const checkLogin = () => request.get('/api/session');

