import { put, take, fork } from 'redux-saga/effects';
import * as action from '../actionTypes';

function* init () {
  yield take(action.INITIALIZE);
  yield put({ type: action.INITIALIZED });
}

export default function* () {
  return [
    yield fork(init),
  ];
}
