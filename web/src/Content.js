import React, { Component } from 'react';
import { Switch, Route } from 'react-router-dom';

import './Content.css';
import Login from './Header/Login'

class Content extends Component {
  render() {
    return (
      <div className="content">
        <Switch>
          <Route exact path='/login' component={Login}/>
        </Switch>
      </div>
    );
  }
}

export default Content;
