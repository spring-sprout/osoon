import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom';

import Content from './Content';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<Router><Content /></Router>, div);
});
