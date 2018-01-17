import { createStore } from 'redux'

import pointsApp from './PointsReducer.jsx'

var store = createStore(pointsApp);

export default store;