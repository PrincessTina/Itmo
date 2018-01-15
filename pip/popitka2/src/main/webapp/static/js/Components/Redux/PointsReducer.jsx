import {combineReducers} from 'redux'

import {ADD_POINT} from './Actions.jsx'

function pointsReducer(state = { points: [] }, action) {
    switch (action.type) {
        case ADD_POINT:
            return {
                points: state.points.concat([{
                    x: action.x,
                    y: action.y
                }]),
            };
        default:
            return state;
    }
}

const pointsApp = combineReducers({
    pointsReducer
});

export default pointsApp