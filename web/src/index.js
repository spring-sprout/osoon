import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';

import registerServiceWorker from './registerServiceWorker';
import { INITIALIZE, LOGIN_CHECK } from './actionTypes';
import configureStore from './store/configureStore';
import rootSaga from './sagas';
import LayoutPage from './pages/LayoutPage';

const store = configureStore();

const render = () => {
  ReactDOM.render(
    <Provider store={ store }>
      <LayoutPage />
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
store.dispatch({ type: LOGIN_CHECK });

registerServiceWorker();
