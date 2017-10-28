import React, { Component } from 'react';
import { connect } from 'react-redux';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';

import Header from '../components/Header';
import Login from './user/Login'
import Front from './Front'

import './Layout.css';

class Layout extends Component {
  render() {
    return (
      <Router>
        <div className="Layout">
          <Header/>
          <div className="content">
            <Switch>
              <Route exact path='/' component={Front}/>
              <Route exact path='/login' component={Login}/>
            </Switch>
          </div>
        </div>
      </Router>
    );
  }
}

const mapStateToProps = (state) => {
  return {
  };
}

export default connect(mapStateToProps)(Layout);
