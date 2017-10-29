import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom';

import LoginPage from './LoginPage';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<Router><LoginPage /></Router>, div);
});
