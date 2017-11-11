import * as actionTypes from '../actionTypes';

const initialState = {
  name: null,
  profile: null,
};

export default (state = initialState, action) => {
  switch (action.type) {
    case actionTypes.MEETING_CREATE: {
      return {
        ...action.payload,
        isProgress: true,
      };
    }
    case actionTypes.MEETING_CREATE_SUCCESS: {
      return {
        ...action.payload,
        isProgress: false,
        isSucceed: true,
      };
    }
    case actionTypes.MEETING_FETCH_SUCCESS: {
      return {
        ...action.payload,
      };
    }

    default:
      return state;
  }

}
