import _ from 'lodash';

import * as actionTypes from '../actionTypes';

const initialState = {
  name: null,
  profile: null,
};

export default (state = initialState, action) => {
  switch (action.type) {
    case actionTypes.LOGIN_SUCCESS: {
      return _.assign(state, { name: action.payload.name, profile: action.payload.imageUrl });
    }

    default:
      return state;
  }

}
