import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom';

import FrontPage from './FrontPage';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<Router><FrontPage /></Router>, div);
});
