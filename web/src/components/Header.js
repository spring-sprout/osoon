import React, { Component } from 'react';
import { Link } from 'react-router-dom';

import './Header.css';
import searchIcon from './search.svg';
import logo from './logo.svg';

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
            <Link to="/">
              <img src={logo} alt="o"/>osoon
            </Link>
          </div>
          <nav>
            <ul>
              <li><Link to="/">Category</Link></li>
            </ul>
          </nav>
          <div className="login right">{userMenu}</div>
          <div className="search right ">
            <input type="text" placeholder="모임을 검색해 보세요" />
            <img src={searchIcon} alt="search"/>
          </div>
        </div>
      </header>
    );
  }
}

export default Header;
