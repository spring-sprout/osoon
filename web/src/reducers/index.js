import { combineReducers } from 'redux';

import appState from './appState';
import user from './userState';
import meeting from './meetingState';

export default combineReducers({
  appState,
  user,
  meeting,
});
