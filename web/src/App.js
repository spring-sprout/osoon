import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';

import './App.css';
import Header from './Header';

class App extends Component {
  render() {
    return (
      <div className="app">
        <Header/>
      </div>
    );
  }
}

export default App;
