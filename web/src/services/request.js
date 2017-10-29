import axios from 'axios';

import { SERVER_URL } from './common';

export default axios.create({
  baseURL: SERVER_URL,
});
