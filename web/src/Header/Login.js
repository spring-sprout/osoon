import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';

import './Login.css';
import FacebookLogin from './Login/FacebookLogin';
import { axios } from '../common';

class Login extends Component {
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
      <div className="content-body login">
        <FacebookLogin/>
      </div>
    );
  }
}

export default Login;
