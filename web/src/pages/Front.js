import React, { Component } from 'react';
import { connect } from 'react-redux';

import './Front.css';

class Front extends Component {
  render() {
    return [
      <div className="Front">
        <div className="breadcrumb">
          <div className="breadcrumb-body">
            <h1>모임을 만들어 보세요</h1>
            <h4>원하는 모임이 없다면 직접 모임을 만들어 운영해 보세요.</h4>
            <a href="#" className="btn">모임만들기</a>
          </div>
        </div>,
        <div className="content-body">
          <div className="meeting-list">
            <h2>인기 모임</h2>
            <div className="meeting-item">
              <div className="meeting-image"></div>
              <h2 className="meeting-title">블라블라</h2>
              <time>2017.11.01</time>
              <span className="remain"></span>
            </div>
            <div className="meeting-item">
              <div className="meeting-image"></div>
              <h2 className="meeting-title">블라블라</h2>
              <time>2017.11.01</time>
              <span className="remain"></span>
            </div>
            <div className="meeting-item">
              <div className="meeting-image"></div>
              <h2 className="meeting-title">블라블라</h2>
              <time>2017.11.01</time>
              <span className="remain"></span>
            </div>
            <div className="meeting-item">
              <div className="meeting-image"></div>
              <h2 className="meeting-title">블라블라</h2>
              <time>2017.11.01</time>
              <span className="remain"></span>
            </div>
          </div>

          <div className="meeting-list">
            <h2>신규 모임</h2>
            <div className="meeting-item">
              <div className="meeting-image"></div>
              <h2 className="meeting-title">블라블라</h2>
              <time>2017.11.01</time>
              <span className="remain"></span>
            </div>
            <div className="meeting-item">
              <div className="meeting-image"></div>
              <h2 className="meeting-title">블라블라</h2>
              <time>2017.11.01</time>
              <span className="remain"></span>
            </div>
            <div className="meeting-item">
              <div className="meeting-image"></div>
              <h2 className="meeting-title">블라블라</h2>
              <time>2017.11.01</time>
              <span className="remain"></span>
            </div>
            <div className="meeting-item">
              <div className="meeting-image"></div>
              <h2 className="meeting-title">블라블라</h2>
              <time>2017.11.01</time>
              <span className="remain"></span>
            </div>
          </div>

          <div className="meeting-list">
            <h2>곧 마감하는 모임</h2>
            <div className="meeting-item">
              <div className="meeting-image"></div>
              <h2 className="meeting-title">블라블라</h2>
              <time>2017.11.01</time>
              <span className="remain"></span>
            </div>
            <div className="meeting-item">
              <div className="meeting-image"></div>
              <h2 className="meeting-title">블라블라</h2>
              <time>2017.11.01</time>
              <span className="remain"></span>
            </div>
            <div className="meeting-item">
              <div className="meeting-image"></div>
              <h2 className="meeting-title">블라블라</h2>
              <time>2017.11.01</time>
              <span className="remain"></span>
            </div>
            <div className="meeting-item">
              <div className="meeting-image"></div>
              <h2 className="meeting-title">블라블라</h2>
              <time>2017.11.01</time>
              <span className="remain"></span>
            </div>
          </div>
        </div>
      </div>,
    ];
  }
}

const mapStateToProps = (state) => {
  return {
  };
}

export default connect(mapStateToProps)(Front);
