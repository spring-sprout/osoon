import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';

import './Header.css';

class Header extends Component {
  render() {
    return (
      <header className="main-header">
        <div className="header-logo">
          osoon
        </div>
        <nav>
          <ul>
            <li>
              <a href="#">Log In</a>
            </li>
            <li>
              <a href="#">Sign Up</a>
            </li>
          </ul>
        </nav>
      </header>
    );
  }
}

export default Header;
