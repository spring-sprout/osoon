import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom';

import ShowMeetingPage from './ShowMeetingPage';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<Router><ShowMeetingPage /></Router>, div);
});
