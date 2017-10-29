import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';

import facebookButton from './facebook.png';
import { SERVER_URL } from '../../services/common';

import './LoginPage.css';

class LoginPage extends Component {
  render() {
    if (this.props.user) {
      return (
        <Redirect to={{ pathname: '/' }} />
      )
    }

    return (
      <div className="LoginPage">
        <div className="login-btn facebook-login">
          <form action={SERVER_URL + '/signin/facebook'} method="post">
            <input type="hidden" name="scope" value="public_profile, email" />
            <input type="image" src={facebookButton} alt="Login with Facebook" />
          </form>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    user: state.user,
  };
}

export default connect(mapStateToProps)(LoginPage);
