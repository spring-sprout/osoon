import { call, put, takeLatest } from 'redux-saga/effects';

import * as actionTypes from '../actionTypes';
import { createMeeting } from '../services/meeting';

function* createMeetingTask({ payload }) {
  const response = yield call(createMeeting, payload);

  if (response.statusText === 'OK') {
    yield put({ type: actionTypes.MEETING_CREATE_SUCCESS, payload: response.data.content });
  }
}

export default function* () {
  return [
    yield takeLatest(actionTypes.MEETING_CREATE, createMeetingTask),
  ];
}
