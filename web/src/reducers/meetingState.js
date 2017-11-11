import _ from 'lodash';

import * as actionTypes from '../actionTypes';

const initialState = {
  name: null,
  profile: null,
};

export default (state = initialState, action) => {
  switch (action.type) {
    case actionTypes.MEETING_CREATE: {
      return _.assign(state, action.payload);
    }

    default:
      return state;
  }

}
