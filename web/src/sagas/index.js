import { all } from 'redux-saga/effects';
import init from './init';

export default function* rootSaga () {
  yield all([
    init(),
  ]);
};


