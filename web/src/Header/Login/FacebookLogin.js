import React, { Component } from 'react';

import './FacebookLogin.css';
import facebookButton from './facebook.png';
import { SERVER_URL } from '../../common';

class FacebookLogin extends Component {
  render() {
    return (
      <div className="login-btn facebook-login">
        <form action={SERVER_URL + '/signin/facebook'} method="post">
          <input type="hidden" name="scope" value="public_profile, email" />
          <input type="image" src={facebookButton} alt="Login with Facebook" />
        </form>
      </div>
    );
  }
}

export default FacebookLogin;
