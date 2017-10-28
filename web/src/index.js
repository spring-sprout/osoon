import React from 'react';
import ReactDOM from 'react-dom';
import Router from './router';
import { Provider } from 'react-redux';

import registerServiceWorker from './registerServiceWorker';
import { INITIALIZE } from './actionTypes';
import configureStore from './store/configureStore';
import rootSaga from './sagas';

const store = configureStore();

const render = () => {
  ReactDOM.render(
    <Provider store={ store }>
      <Router />
    </Provider>,
    document.getElementById('root'),
  );
};

const unsubscribe = store.subscribe(() => {
  const initialized = store.getState().appState.initialized;
  if (initialized) {
    unsubscribe();
    render();
  }
});

store.runSaga(rootSaga);
store.dispatch({ type: INITIALIZE });

registerServiceWorker();
