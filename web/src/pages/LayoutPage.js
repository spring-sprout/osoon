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
          <Header user={this.props.user} />
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
    user: state.user,
  };
}

export default connect(mapStateToProps)(LayoutPage);
