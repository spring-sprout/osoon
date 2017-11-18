import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import './FrontPage.css';
import rightArrowIcon from './right-arrow.svg';

class FrontPage extends Component {
  render() {
    return (
      <div className="FrontPage">
        <div className="breadcrumb">
          <div className="breadcrumb-body">
            <h1>모임을 만들어 보세요</h1>
            <span>이불 밖도 안전해!</span>
            <span>원하는 모임을 만들어서 오순도순 함께하는 즐거움을 느껴보세요.</span>
            <Link to="/meetings/create" className="createBtn">지금 만들기</Link>
          </div>
        </div>
        <div className="content-body">
          <div className="meeting-list">
            <Link to="#" className="more">모두 보기<img src={rightArrowIcon} alt=""/></Link>
            <h2>인기 모임</h2>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">meeting title goes here. meeting title goes here.</h2>
                <span className="remain">7명 남음</span>
                <time>2017.12.12</time>
              </div>
              <div className="progress hurryup" style={{width: 90 + '%'}}></div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">meeting title goes here. meeting title goes here.</h2>
                <span className="remain">7명 남음</span>
                <time>2017.12.12</time>
              </div>
              <div className="progress" style={{width: 60 + '%'}}></div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">meeting title goes here. meeting title goes here.</h2>
                <span className="remain">7명 남음</span>
                <time>2017.12.12</time>
              </div>
              <div className="progress" style={{width: 60 + '%'}}></div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">meeting title goes here. meeting title goes here.</h2>
                <span className="remain">7명 남음</span>
                <time>2017.12.12</time>
              </div>
              <div className="progress" style={{width: 60 + '%'}}></div>
            </Link>
          </div>

          <div className="meeting-list">
            <Link to="#" className="more">모두 보기</Link>
            <h2>신규 모임</h2>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">meeting title goes here. meeting title goes here.</h2>
                <span className="remain">7명 남음</span>
                <time>2017.12.12</time>
              </div>
              <div className="progress hurryup" style={{width: 90 + '%'}}></div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">meeting title goes here. meeting title goes here.</h2>
                <span className="remain">7명 남음</span>
                <time>2017.12.12</time>
              </div>
              <div className="progress" style={{width: 60 + '%'}}></div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">meeting title goes here. meeting title goes here.</h2>
                <span className="remain">7명 남음</span>
                <time>2017.12.12</time>
              </div>
              <div className="progress" style={{width: 60 + '%'}}></div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">meeting title goes here. meeting title goes here.</h2>
                <span className="remain">7명 남음</span>
                <time>2017.12.12</time>
              </div>
              <div className="progress" style={{width: 60 + '%'}}></div>
            </Link>
          </div>

          <div className="meeting-list">
            <Link to="#" className="more">모두 보기</Link>
            <h2>곧 마감하는 모임</h2>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">meeting title goes here. meeting title goes here.</h2>
                <span className="remain">7명 남음</span>
                <time>2017.12.12</time>
              </div>
              <div className="progress hurryup" style={{width: 90 + '%'}}></div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">meeting title goes here. meeting title goes here.</h2>
                <span className="remain">7명 남음</span>
                <time>2017.12.12</time>
              </div>
              <div className="progress" style={{width: 60 + '%'}}></div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">meeting title goes here. meeting title goes here.</h2>
                <span className="remain">7명 남음</span>
                <time>2017.12.12</time>
              </div>
              <div className="progress" style={{width: 60 + '%'}}></div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">meeting title goes here. meeting title goes here.</h2>
                <span className="remain">7명 남음</span>
                <time>2017.12.12</time>
              </div>
              <div className="progress" style={{width: 60 + '%'}}></div>
            </Link>
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
  };
}

export default connect(mapStateToProps)(FrontPage);
