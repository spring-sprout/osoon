import { call, put, take, fork } from 'redux-saga/effects';

import * as actionTypes from '../actionTypes';
import { checkLogin } from '../services/user';

function* init () {
  yield take(actionTypes.INITIALIZE);

  const response = yield call(checkLogin);
  if (response.data && response.data.name) {
    yield put({ type: actionTypes.LOGIN_SUCCESS, payload: response.data });
  }

  yield put({ type: actionTypes.INITIALIZED });
}

export default function* () {
  return [
    yield fork(init),
  ];
}
