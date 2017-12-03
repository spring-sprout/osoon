import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';

import facebookButton from './facebook.png';
import { SERVER_URL } from '../../services/common';

import './LoginPage.css';

class LoginPage extends Component {
  render() {
    if (this.props.user && this.props.user.name) {
      // FIXME: redirect to where the user was after login
      const { from } = this.props.location.state || { from: { pathname: '/' } };
      return (
        <Redirect to={from} />
      );
    }

    return (
      <div className="LoginPage">
        <h1>Welcome to Osoon</h1>
        <h3>간단하게 가입하고 참여해 보세요!</h3>
        <div className="login-btn facebook-login">
          <form action={SERVER_URL + '/signin/facebook'} method="post">
            <input type="hidden" name="scope" value="public_profile, email" />
            <button type="submit" className="login-btn">페이스북으로 로그인</button>
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
