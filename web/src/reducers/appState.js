import _ from 'lodash';

import * as actionTypes from '../actionTypes';

const initialState = {
  initialized: false
};

export default (state = initialState, action) => {
  switch (action.type) {
    case actionTypes.INITIALIZED: {
      return _.assign(state, { initialized: true });
    }

    default:
      return state;
  }
}
