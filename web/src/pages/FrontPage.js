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
            <Link to="/meetings/create" className="createBtn">모임 만들기</Link>
          </div>
        </div>
        <div className="content-body">
          <div className="meeting-list">
            <Link to="#" className="more">모두 보기<img src={rightArrowIcon} alt=""/></Link>
            <h2>인기 모임</h2>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">Design</h2>
                <span className="meeting-content">Freelance Design Tricks How To Get Away With Murder in The Workplace</span>
              </div>
              <div className="meeting-item-footer">
                <span className="location">7596 Launine Parkway</span>
                <time>15 Mar 2018</time>
              </div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">Design</h2>
                <span className="meeting-content">Freelance Design Tricks How To Get Away With Murder in The Workplace</span>
              </div>
              <div className="meeting-item-footer">
                <span className="location">7596 Launine Parkway</span>
                <time>15 Mar 2018</time>
              </div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">Design</h2>
                <span className="meeting-content">Freelance Design Tricks How To Get Away With Murder in The Workplace</span>
              </div>
              <div className="meeting-item-footer">
                <span className="location">7596 Launine Parkway</span>
                <time>15 Mar 2018</time>
              </div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">Design</h2>
                <span className="meeting-content">Freelance Design Tricks How To Get Away With Murder in The Workplace</span>
              </div>
              <div className="meeting-item-footer">
                <span className="location">7596 Launine Parkway</span>
                <time>15 Mar 2018</time>
              </div>
            </Link>
          </div>

          <div className="meeting-list">
            <Link to="#" className="more">모두 보기<img src={rightArrowIcon} alt=""/></Link>
            <h2>신규 모임</h2>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">Design</h2>
                <span className="meeting-content">Freelance Design Tricks How To Get Away With Murder in The Workplace</span>
              </div>
              <div className="meeting-item-footer">
                <span className="location">7596 Launine Parkway</span>
                <time>15 Mar 2018</time>
              </div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">Design</h2>
                <span className="meeting-content">Freelance Design Tricks How To Get Away With Murder in The Workplace</span>
              </div>
              <div className="meeting-item-footer">
                <span className="location">7596 Launine Parkway</span>
                <time>15 Mar 2018</time>
              </div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">Design</h2>
                <span className="meeting-content">Freelance Design Tricks How To Get Away With Murder in The Workplace</span>
              </div>
              <div className="meeting-item-footer">
                <span className="location">7596 Launine Parkway</span>
                <time>15 Mar 2018</time>
              </div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">Design</h2>
                <span className="meeting-content">Freelance Design Tricks How To Get Away With Murder in The Workplace</span>
              </div>
              <div className="meeting-item-footer">
                <span className="location">7596 Launine Parkway</span>
                <time>15 Mar 2018</time>
              </div>
            </Link>
          </div>

          <div className="meeting-list">
            <Link to="#" className="more">모두 보기<img src={rightArrowIcon} alt=""/></Link>
            <h2>곧 마감하는 모임</h2>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">Design</h2>
                <span className="meeting-content">Freelance Design Tricks How To Get Away With Murder in The Workplace</span>
              </div>
              <div className="meeting-item-footer">
                <span className="location">7596 Launine Parkway</span>
                <time>15 Mar 2018</time>
              </div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">Design</h2>
                <span className="meeting-content">Freelance Design Tricks How To Get Away With Murder in The Workplace</span>
              </div>
              <div className="meeting-item-footer">
                <span className="location">7596 Launine Parkway</span>
                <time>15 Mar 2018</time>
              </div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">Design</h2>
                <span className="meeting-content">Freelance Design Tricks How To Get Away With Murder in The Workplace</span>
              </div>
              <div className="meeting-item-footer">
                <span className="location">7596 Launine Parkway</span>
                <time>15 Mar 2018</time>
              </div>
            </Link>
            <Link to="#" className="meeting-item">
              <div className="meeting-image"></div>
              <div className="meeting-item-body">
                <h2 className="meeting-title">Design</h2>
                <span className="meeting-content">Freelance Design Tricks How To Get Away With Murder in The Workplace</span>
              </div>
              <div className="meeting-item-footer">
                <span className="location">7596 Launine Parkway</span>
                <time>15 Mar 2018</time>
              </div>
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
