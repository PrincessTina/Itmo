import {combineReducers} from 'redux'
import {createStore} from 'redux'
import {ADD_USER} from './Actions.jsx'
import {ADD_POINT} from "./Actions.jsx";

function userReducer(state = {user: {}}, action) {
    switch (action.type) {
        case ADD_USER:
            return {
                user: {
                    id: action.id,
                    login: action.login,
                },
            };
        default:
            return state;
    }
}

function pointsReducer(state = {points: []}, action) {
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

var store = createStore(
    combineReducers({
        userReducer,
        pointsReducer,
    })
);

export default store;