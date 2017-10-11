import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom';

import Login from './Login.js';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<Router><Login /></Router>, div);
});
