import React, { Component } from 'react';
import { connect } from 'react-redux';

import { MEETING_FETCH } from '../../actionTypes';

import './ShowMeetingPage.css';

class showMeetingPage extends Component {
  componentDidMount () {
    this.props.dispatch({
      type: MEETING_FETCH,
      payload: {
        meetingId: this.props.match.params.meetingId,
      },
    })
  }

  render() {
    const { meeting } = this.props;
    console.log(meeting);

    return (
      <div className="ShowMeetingPage content-body">
        <h1>{meeting.title}</h1>
        <div>{meeting.contents}</div>
        <div>
          모임 일시: {meeting.meetStartAt} ~ {meeting.meetEndAt}
        </div>

        <div>
          최대 참가자 수: {meeting.maxAttendees} 명
        </div>
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

export default connect(mapStateToProps)(showMeetingPage);
