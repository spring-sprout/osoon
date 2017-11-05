import React, { Component } from 'react';
import { Link } from 'react-router-dom';

import './Header.css';

class Header extends Component {
  render() {
    const { user } = this.props;

    let userMenu = null;
    if (user && user.name) {
      userMenu = (
        <div className="profile">
          <img src={user.profile} alt={user.name} />
          {user.name}
        </div>
      );
    } else {
      userMenu = <Link to="/login">Log In</Link>;
    }

    return (
      <header className="Header">
        <div className="body">
          <div className="logo">
            osoon
          </div>
          <nav>
            <ul>
              <li>{userMenu}</li>
            </ul>
          </nav>
        </div>
      </header>
    );
  }
}

export default Header;
