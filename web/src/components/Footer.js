import React, { Component } from 'react';
import { Link } from 'react-router-dom';

import './Footer.css';

class Footer extends Component {
  render() {
    return (
      <footer className="Footer">
        <div className="body">
          오순은 중개자이며 모임에 대한 당사자 및 주최자가 아닙니다.
          따라서 오순은 등록된 모임에 대하여 책임을 지지 않습니다.
          <ul>
            <li><Link to="/">About</Link></li>
            <li><Link to="/">약관</Link></li>
          </ul>
        </div>
      </footer>
    );
  }
}

export default Footer;
