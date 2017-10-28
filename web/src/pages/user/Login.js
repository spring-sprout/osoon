import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';

import facebookButton from './facebook.png';
import { SERVER_URL, axios } from '../../utils/common';

import './Login.css';

class Login extends Component {
  state = {
    isLoggedIn: false
  }

  componentDidMount() {
    axios.get('/api/session')
      .then((response) => {
        if (response.data && response.data.name) {
          this.setState({ isLoggedIn: true });
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }

  render() {
    if (this.state.isLoggedIn) {
      return (
        <Redirect to={{ pathname: '/' }} />
      )
    }

    return (
      <div className="Login">
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
  };
}

export default connect(mapStateToProps)(Login);
