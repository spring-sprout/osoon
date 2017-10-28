import _ from 'lodash';

import * as appType from '../actionTypes';

const initialState = {
  initialized: false
};

export default (state = initialState, action) => {
  switch (action.type) {
    case appType.INITIALIZED: {
      return _.assign(state, { initialized: true });
    }

    default:
      return state;
  }

}
