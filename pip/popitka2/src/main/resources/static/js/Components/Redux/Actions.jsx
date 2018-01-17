export const ADD_POINT = 'ADD_POINT';

export function addPoint(x, y) {
    return {
        type: ADD_POINT,
        x: x,
        y: y
    }
}