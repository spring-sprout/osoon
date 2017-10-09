import React, { Component } from 'react';
import { Switch, Route } from 'react-router-dom';

import './Content.css';
import Login from './Header/Login'
import Index from './Index/Index'

class Content extends Component {
  render() {
    return (
      <div className="content">
        <Switch>
          <Route exact path='/' component={Index}/>
          <Route exact path='/login' component={Login}/>
        </Switch>
      </div>
    );
  }
}

export default Content;
