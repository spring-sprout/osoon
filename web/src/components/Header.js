import React, { Component } from 'react';
import { Link } from 'react-router-dom';

import './Header.css';

class Header extends Component {
  render() {
    return (
      <header className="Header">
        <div className="body">
          <div className="logo">
            osoon
          </div>
          <nav>
            <ul>
              <li><Link to="/login">Log In</Link></li>
            </ul>
          </nav>
        </div>
      </header>
    );
  }
}

export default Header;
