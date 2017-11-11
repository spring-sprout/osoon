import { all } from 'redux-saga/effects';

import initialTask from './initialTask';
import meetingTask from './meetingTask';

export default function* rootSaga () {
  yield all([
    meetingTask(),
    initialTask(),
  ]);
};


