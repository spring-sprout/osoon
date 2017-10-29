import { combineReducers } from 'redux';

import appState from './appState';
import user from './userState';

export default combineReducers({
  appState,
  user,
});
