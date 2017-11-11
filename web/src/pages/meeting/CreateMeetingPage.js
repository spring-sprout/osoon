import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';

import { MEETING_CREATE, MEETING_CREATE_SUCCESS } from '../../actionTypes';

import './CreateMeetingPage.css';

class CreateMeetingPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      title: '',
      contents: '',
      meetStartAt: '',
      meetEndAt: '',
      maxAttendees: 0,
      isAutoConfirm: true,
      isOnline: false,
    };
    // TODO: 이미지 업로드를 구현해야 한다 - coverImage

    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState({
      [name]: value
    });
  }

  handleSubmit(event) {
    event.preventDefault();
    this.props.dispatch({
      type: MEETING_CREATE,
      payload: this.state,
    });
  }

  render() {
    if (!this.props.user || !this.props.user.name) {
      return (
        <Redirect to={{
          pathname: '/login',
          state: { from: this.props.location }
        }}/>
      )
    }

    return (
      <div className="CreateMeetingPage content-body">
        <h1>모임 생성하기</h1>
        <form onSubmit={this.handleSubmit}>
          <label>
            <span>모임명:</span>
            <input
              name="title"
              type="text"
              value={this.state.title}
              onChange={this.handleInputChange} />
          </label>
          <label>
            <span>모임 설명:</span>
            <textarea
              name="contents"
              value={this.state.contents}
              onChange={this.handleInputChange} />
          </label>
          <label>
            <span>모임 시작일시:</span>
            <input
              name="meetStartAt"
              type="text"
              value={this.state.meetStartAt}
              onChange={this.handleInputChange} />
          </label>
          <label>
            <span>모임 종료일시:</span>
            <input
              name="meetEndAt"
              type="text"
              value={this.state.meetEndAt}
              onChange={this.handleInputChange} />
          </label>
          <label>
            <span>총 모집 인원:</span>
            <input
              name="maxAttendees"
              type="text"
              value={this.state.maxAttendees}
              onChange={this.handleInputChange} />
          </label>
          <label>
            <span>자동 승인 여부:</span>
            <input
              name="isAutoConfirm"
              type="checkbox"
              checked={this.state.isAutoConfirm}
              onChange={this.handleInputChange} />
          </label>
          <input type="submit" value="생성하기" />
        </form>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    user: state.user,
  };
}

export default connect(mapStateToProps)(CreateMeetingPage);
