import axios from 'axios';

export const SERVER_URL =  process.env.SERVER_URL || 'http://localhost:3000';

const instance = axios.create({
  baseURL: SERVER_URL,
});

export {
  instance as axios
};



