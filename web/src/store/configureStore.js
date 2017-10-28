import { createStore, applyMiddleware, compose } from 'redux';
import createSagaMiddleware from 'redux-saga';

import rootReducer from '../reducers';

export default function () {
  const sagaMiddleware = createSagaMiddleware();
  const initialState = {};

  const store = createStore(
    rootReducer,
    initialState,
    compose(
      applyMiddleware( sagaMiddleware ),
      window.devToolsExtension ? window.devToolsExtension() : f => f,
    ),
  );

  store.runSaga = sagaMiddleware.run;
  store.close = () => store.dispatch(createSagaMiddleware.END);

  return store;
};
