import React, { Component } from 'react';
import { Link } from 'react-router-dom';

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
            <li><Link to="/login">Log In</Link></li>
          </ul>
        </nav>
      </header>
    );
  }
}

export default Header;
