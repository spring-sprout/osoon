import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom';

import LayoutPage from './LayoutPage';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<Router><LayoutPage /></Router>, div);
});
