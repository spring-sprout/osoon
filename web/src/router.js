import React from 'react';
import { connect } from 'react-redux';
import { Router } from 'react-router-dom';
import createBrowserHistory from 'history/createBrowserHistory'

import App from './App';

const history = createBrowserHistory();

const AppRouter = (props) => {
  return (
    <Router history={ history }>
      <App />
    </Router>
  );
}

const mapStateToProps = (state) => {
  return {
  };
}

export default connect(mapStateToProps)(AppRouter);
