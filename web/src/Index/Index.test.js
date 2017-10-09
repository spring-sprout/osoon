import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom';

import Index from './Index.js';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<Router><Index /></Router>, div);
});
