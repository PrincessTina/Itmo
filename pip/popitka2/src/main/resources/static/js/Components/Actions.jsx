export const ADD_POINT = 'ADD_POINT';
export const ADD_USER = 'ADD_USER';

export function addPoint(x, y) {
    return {
        type: ADD_POINT,
        x: x,
        y: y
    }
}

export function setUser(id, login) {
    return {
        type: ADD_USER,
        id: id,
        login: login,
    }
}