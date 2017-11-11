import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom';

import CreateMeetingPage from './CreateMeetingPage';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<Router><CreateMeetingPage /></Router>, div);
});
