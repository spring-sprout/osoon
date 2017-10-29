import { all } from 'redux-saga/effects';

import initialTask from './initialTask';

export default function* rootSaga () {
  yield all([
    initialTask(),
  ]);
};


