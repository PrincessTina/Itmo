export const ADD_POINT = 'ADD_POINT';
export const ADD_USER = 'ADD_USER';
export const NULL = 'NULL';

export function addPoint(id, x, y, r, result) {
    return {
        type: ADD_POINT,
        id: id,
        x: x,
        y: y,
        r: r,
        result: result,
    }
}

export function setNullPoints() {
    return {
        type: NULL,
    }
}

export function setUser(id, login) {
    return {
        type: ADD_USER,
        id: id,
        login: login,
    }
}