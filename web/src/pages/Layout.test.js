import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom';

import Layout from './Layout';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<Router><Layout /></Router>, div);
});
