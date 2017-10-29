import React, { Component } from 'react';
import { connect } from 'react-redux';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';

import Header from '../components/Header';
import LoginPage from './user/LoginPage'
import FrontPage from './FrontPage'

import './LayoutPage.css';

class LayoutPage extends Component {
  render() {
    return (
      <Router>
        <div className="LayoutPage">
          <Header/>
          <div className="content">
            <Switch>
              <Route exact path='/' component={FrontPage}/>
              <Route exact path='/login' component={LoginPage}/>
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

export default connect(mapStateToProps)(LayoutPage);
