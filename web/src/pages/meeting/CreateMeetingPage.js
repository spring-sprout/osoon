import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';

import { MEETING_CREATE } from '../../actionTypes';

import './CreateMeetingPage.css';

class CreateMeetingPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isProgress: false,
      isSucceed: false,
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
    const { user, meeting, location } = this.props;
    if (!user || !user.name) {
      return (
        <Redirect to={{
          pathname: '/login',
          state: { from: location },
        }}/>
      );
    }

    if (meeting.isSucceed) {
      return (<Redirect to={{
        pathname: `/meetings/${meeting.id}`,
      }}/>);
    }

    return (
      <div className="CreateMeetingPage">
        <form onSubmit={this.handleSubmit}>
          <div>
            <input
              name="title"
              type="text"
              value={this.state.title}
              onChange={this.handleInputChange}
              placeholder="모임 이름을 입력해 주세요"
              className="title"/>
          </div>
          <div className="content-body">
            <fieldset>
              <span className="legend">카테고리</span>
              <div>
                <input
                  name="category"
                  type="text"
                  className="short"
                  onChange={this.handleInputChange} />
              </div>
            </fieldset>
            <fieldset>
              <span className="legend">모임 기간</span>
              <div>
                <div className="block">
                  <input
                    name="meetStartAt"
                    type="text"
                    className="short"
                    value={this.state.meetStartAt}
                    onChange={this.handleInputChange} />
                  &nbsp;-&nbsp;
                  <input
                    name="meetEndAt"
                    type="text"
                    className="short"
                    value={this.state.meetEndAt}
                    onChange={this.handleInputChange} />
                </div>
                <input
                  name=""
                  type="text"
                  className="short"
                  onChange={this.handleInputChange} />
                &nbsp;-&nbsp;
                <input
                  name=""
                  type="text"
                  className="short"
                  onChange={this.handleInputChange} />
                <label>
                  <input
                    name=""
                    type="checkbox"
                    onChange={this.handleInputChange} /> 하루종일
                </label>
              </div>
            </fieldset>
            <fieldset>
              <span className="legend">신청 기간</span>
              <div>
                <input
                  name=""
                  type="text"
                  className="short"
                  onChange={this.handleInputChange} /> 일 전까지
                <span>(2017년 11월 31일)</span>
              </div>
            </fieldset>
            <fieldset>
              <span className="legend">참가 인원</span>
              <div>
                <input
                  name=""
                  type="text"
                  className="short"
                  onChange={this.handleInputChange} /> 명
              </div>
            </fieldset>
            <fieldset>
              <span className="legend">장소</span>
              <div>
                <label>
                  <input
                    name=""
                    type="radio"
                    onChange={this.handleInputChange} /> 오프라인
                  <input
                    name=""
                    type="radio"
                    onChange={this.handleInputChange} /> 온라인
                </label>
                <div className="block">
                  <input
                    name=""
                    type="text"
                    onChange={this.handleInputChange} />
                </div>
              </div>
            </fieldset>
            <fieldset>
              <span className="legend">모임 이미지</span>
              <div>
                <input
                  name=""
                  type="text"
                  onChange={this.handleInputChange} />
              </div>
            </fieldset>
            <fieldset>
              <span className="legend">모임 내용</span>
              <div>
                <textarea
                  name="contents"
                  value={this.state.contents}
                  onChange={this.handleInputChange} />
              </div>
            </fieldset>
            <fieldset>
              <span className="legend">태그</span>
              <div>
                <input
                  name=""
                  type="text"
                  onChange={this.handleInputChange} />
              </div>
            </fieldset>
          </div>
          <div className="buttons">
            <button className="save">임시 저장</button>
            <button className="publish">발행</button>
            <span>
              모임을 발행하기 전까지 "임시 저장"한 뒤 모임 정보를 편하게 수정하세요. 모임을 "발행"해야 참가 신청을 받을 수 있습니다.
            </span>
          </div>
        </form>
        <div className={meeting.isProgress ? '' : 'hidden'}>저장 중...</div>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    user: state.user,
    meeting: state.meeting,
  };
}

export default connect(mapStateToProps)(CreateMeetingPage);
