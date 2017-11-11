import { call, put, takeLatest } from 'redux-saga/effects';

import * as actionTypes from '../actionTypes';
import { createMeeting, fetchMeeting } from '../services/meeting';

function* createMeetingTask({ payload }) {
  const response = yield call(createMeeting, payload);

  if (response.statusText === 'OK') {
    yield put({ type: actionTypes.MEETING_CREATE_SUCCESS, payload: response.data.content });
  }
}

function* fetchMeetingTask({ payload }) {
  const response = yield call(fetchMeeting, payload.meetingId);

  if (response.statusText === 'OK') {
    yield put({ type: actionTypes.MEETING_FETCH_SUCCESS, payload: response.data });
  }
}

export default function* () {
  return [
    yield takeLatest(actionTypes.MEETING_CREATE, createMeetingTask),
    yield takeLatest(actionTypes.MEETING_FETCH, fetchMeetingTask),
  ];
}
