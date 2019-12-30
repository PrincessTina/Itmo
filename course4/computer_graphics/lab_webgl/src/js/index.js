var context; // контекст WebGL
var shaderProgram;
var cubeVertexBuffer;
var cubeIndexBuffer;
var cubeVertexNormalBuffer;

var mvMatrix;
var pMatrix;
var nMatrix;

var angleX = 0.01;
var angleY = 2.0;

function handleKeyDown(e) {
    switch (e.keyCode) {
        case 39:  // стрелка вправо
            angleY += 0.1;
            break;
        case 37:  // стрелка влево
            angleY -= 0.1;
            break;
        case 40:  // стрелка вниз
            angleX += 0.1;
            break;
        case 38:  // стрелка вверх
            angleX -= 0.1;
            break;
    }
}

window.requestAnimFrame = (function () {
    return window.requestAnimationFrame ||
        window.webkitRequestAnimationFrame ||
        window.mozRequestAnimationFrame ||
        window.oRequestAnimationFrame ||
        window.msRequestAnimationFrame ||
        function (callback) {
            return window.setTimeout(callback, 1000 / 60);
        };
})();

window.onload = function () {
    const canvas = document.getElementById("canvas");

    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    mvMatrix = mat4.create();
    pMatrix = mat4.create();
    nMatrix = mat3.create();

    try {
        // Сначала пытаемся получить стандартный контекст
        // Если не получится, обращаемся к экспериментальному контексту
        context = canvas.getContext("webgl") || canvas.getContext("experimental-webgl");
    } catch (e) {
        alert("Ваш браузер не поддерживает WebGL");
    }

    context.viewportWidth = canvas.width;
    context.viewportHeight = canvas.height;

    initShaders();
    initCubeBuffers();
    setupMaterials();
    setupLights();

    document.addEventListener('keydown', handleKeyDown, false);

    // функция анимации
    (function animloop() {
        setupWebGL();
        setMatrixUniforms();
        draw();
        requestAnimFrame(animloop, canvas);
    })();
};

function createContextShader(type, id) {
    const source = document.getElementById(id).innerHTML;
    const shader = context.createShader(type);

    context.shaderSource(shader, source);
    context.compileShader(shader);

    if (!context.getShaderParameter(shader, context.COMPILE_STATUS)) {
        console.log("Ошибка компиляции шейдера: " + context.getShaderInfoLog(shader));
        context.deleteShader(shader);
        return null;
    }

    return shader;
}

/**
 * Настройка uniform переменных освещения для передачи в vertex shader
 */
function setLightingParams() {
    shaderProgram.uniformLightPosition = context.getUniformLocation(shaderProgram, "uLightPosition");
    shaderProgram.uniformAmbientLightColor = context.getUniformLocation(shaderProgram, "uAmbientLightColor");
    shaderProgram.uniformDiffuseLightColor = context.getUniformLocation(shaderProgram, "uDiffuseLightColor");
    shaderProgram.uniformSpecularLightColor = context.getUniformLocation(shaderProgram, "uSpecularLightColor");
}

/**
 * Настройка uniform переменных материалов для передачи в vertex shader
 */
function setMaterialsParams() {
    shaderProgram.uniformAmbientMaterialColor = context.getUniformLocation(shaderProgram, "uAmbientMaterialColor");
    shaderProgram.uniformDiffuseMaterialColor = context.getUniformLocation(shaderProgram, "uDiffuseMaterialColor");
    shaderProgram.uniformSpecularMaterialColor = context.getUniformLocation(shaderProgram, "uSpecularMaterialColor");
}

function setShaderProgram() {
    shaderProgram.MVMatrix = context.getUniformLocation(shaderProgram, "uMVMatrix");
    shaderProgram.ProjMatrix = context.getUniformLocation(shaderProgram, "uPMatrix");
    shaderProgram.NormalMatrix = context.getUniformLocation(shaderProgram, "uNMatrix");

    shaderProgram.vertexPositionAttribute = context.getAttribLocation(shaderProgram, "aVertexPosition");
    shaderProgram.vertexNormalAttribute = context.getAttribLocation(shaderProgram, "aVertexNormal");

    context.enableVertexAttribArray(shaderProgram.vertexPositionAttribute);
    context.enableVertexAttribArray(shaderProgram.vertexNormalAttribute);

    setLightingParams();
    setMaterialsParams();
}

function initShaders() {
    const fragmentShader = createContextShader(context.FRAGMENT_SHADER, 'shader-fs');
    const vertexShader = createContextShader(context.VERTEX_SHADER, 'shader-vs');

    shaderProgram = context.createProgram();

    context.attachShader(shaderProgram, vertexShader);
    context.attachShader(shaderProgram, fragmentShader);

    context.linkProgram(shaderProgram);

    if (!context.getProgramParameter(shaderProgram, context.LINK_STATUS)) {
        console.log("Не удалсь установить шейдеры");
    }

    context.useProgram(shaderProgram);

    setShaderProgram();
}

/**
 * Настройка цветов освещения
 */
function setupLights() {
    context.uniform3fv(shaderProgram.uniformLightPosition, [0.0, 5.0, 5.0]);
    context.uniform3fv(shaderProgram.uniformAmbientLightColor, [0.0, 0.0, 0.0]);
    context.uniform3fv(shaderProgram.uniformDiffuseLightColor, [0.7, 0.7, 0.7]);
    context.uniform3fv(shaderProgram.uniformSpecularLightColor, [1.0, 1.0, 1.0]);
}

/**
 * Установка материалов
 */
function setupMaterials() {
    context.uniform3fv(shaderProgram.uniformAmbientMaterialColor, [1.0, 1.0, 1.0]);
    context.uniform3fv(shaderProgram.uniformDiffuseMaterialColor, [0.0, 1.0, 1.0]);
    context.uniform3fv(shaderProgram.uniformSpecularMaterialColor, [1.0, 1.0, 1.0]);
}

function setMatrixUniforms() {
    context.uniformMatrix4fv(shaderProgram.ProjMatrix, false, pMatrix);
    context.uniformMatrix4fv(shaderProgram.MVMatrix, false, mvMatrix);
    context.uniformMatrix3fv(shaderProgram.NormalMatrix, false, nMatrix);
}

function initCubeBuffers() {
    const points = [
        // передняя сторона
        -0.5, -0.5, 0.5, // 0
        -0.5, 0.5, 0.5, // 1
        0.5, 0.5, 0.5, // 2
        0.5, -0.5, 0.5, // 3
        // задняя сторона
        -0.5, -0.5, -0.5, // 4
        -0.5, 0.5, -0.5, // 5
        0.5, 0.5, -0.5, // 6
        0.5, -0.5, -0.5, // 7
        // левый бок
        -0.5, -0.5, 0.5, // 0
        -0.5, 0.5, 0.5, // 1
        -0.5, 0.5, -0.5, // 5
        -0.5, -0.5, -0.5, // 4
        // правый бок
        0.5, -0.5, 0.5, // 3
        0.5, 0.5, 0.5, // 2
        0.5, 0.5, -0.5, // 6
        0.5, -0.5, -0.5, // 7
        // верх
        0.5, 0.5, 0.5, // 2
        -0.5, 0.5, 0.5, // 1
        -0.5, 0.5, -0.5, // 5
        0.5, 0.5, -0.5, // 6
        // низ
        0.5, -0.5, 0.5, // 3
        -0.5, -0.5, 0.5, // 0
        -0.5, -0.5, -0.5, // 4
        0.5, -0.5, -0.5 // 7
    ];
    const indexes = [
        // передняя сторона
        0, 1, 2,
        2, 3, 0,
        // задняя сторона
        4, 5, 6,
        6, 7, 4,
        // левый бок
        8, 9, 10,
        10, 11, 8,
        // правый бок
        12, 13, 14,
        14, 15, 12,
        // верх
        16, 17, 18,
        18, 19, 16,
        // низ
        20, 21, 22,
        22, 23, 20
    ];
    const normals = [
        // передняя сторона
        0.0,  0.0,  1.0,
        0.0,  0.0,  1.0,
        0.0,  0.0,  1.0,
        0.0,  0.0,  1.0,

        // задняя сторона
        0.0,  0.0, -1.0,
        0.0,  0.0, -1.0,
        0.0,  0.0, -1.0,
        0.0,  0.0, -1.0,

        // левый бок
        -1.0,  0.0,  0.0,
        -1.0,  0.0,  0.0,
        -1.0,  0.0,  0.0,
        -1.0,  0.0,  0.0,

        // правый бок
        1.0,  0.0,  0.0,
        1.0,  0.0,  0.0,
        1.0,  0.0,  0.0,
        1.0,  0.0,  0.0,

        // верх
        0.0, 1.0, 0.0,
        0.0, 1.0, 0.0,
        0.0, 1.0, 0.0,
        0.0, 1.0, 0.0,

        // низ
        0.0, -1.0, 0.0,
        0.0, -1.0, 0.0,
        0.0, -1.0, 0.0,
        0.0, -1.0, 0.0
    ];

    cubeVertexBuffer = context.createBuffer();
    cubeIndexBuffer = context.createBuffer();
    cubeVertexNormalBuffer = context.createBuffer();

    cubeVertexBuffer.itemSize = 3; // число координат на вершину (x, y, z)
    //cubeVertexBuffer.numberOfItems = 3;
    cubeIndexBuffer.numberOfItems = indexes.length;
    cubeVertexNormalBuffer.itemSize = 3;

    context.bindBuffer(context.ARRAY_BUFFER, cubeVertexBuffer);
    context.bufferData(context.ARRAY_BUFFER, new Float32Array(points), context.STATIC_DRAW);

    context.bindBuffer(context.ELEMENT_ARRAY_BUFFER, cubeIndexBuffer);
    context.bufferData(context.ELEMENT_ARRAY_BUFFER, new Uint16Array(indexes), context.STATIC_DRAW);

    context.bindBuffer(context.ARRAY_BUFFER, cubeVertexNormalBuffer);
    context.bufferData(context.ARRAY_BUFFER, new Float32Array(normals), context.STATIC_DRAW);
}

function setupWebGL() {
    context.clearColor(0.0, 0.0, 0.0, 1.0); // закрашивание фона
    context.viewport(0, 0, context.viewportWidth, context.viewportHeight); // установка области отрисовки
    context.clear(context.COLOR_BUFFER_BIT || context.DEPTH_BUFFER_BIT);

    mat4.perspective(pMatrix, 1.04, context.viewportWidth / context.viewportHeight, 0.1, 100.0);

    mat4.identity(mvMatrix);
    mat4.translate(mvMatrix, mvMatrix, [0, 0, -2.0]);

    mat4.rotateX(mvMatrix, mvMatrix, angleX);
    mat4.rotateY(mvMatrix, mvMatrix, angleY);

    mat3.normalFromMat4(nMatrix, mvMatrix);
    //mat4.lookAt(mvMatrix, [2, 0, -2], [0, 0, 0], [0, 1, 0]);
}

function cubeDraw() {
    context.bindBuffer(context.ARRAY_BUFFER, cubeVertexBuffer);
    context.vertexAttribPointer(shaderProgram.vertexPositionAttribute, cubeVertexBuffer.itemSize, context.FLOAT, false, 0, 0);

    context.bindBuffer(context.ARRAY_BUFFER, cubeVertexNormalBuffer);
    context.vertexAttribPointer(shaderProgram.vertexNormalAttribute, cubeVertexNormalBuffer.itemSize, context.FLOAT, false, 0, 0);

    context.enable(context.DEPTH_TEST);

    context.drawElements(context.TRIANGLES, cubeIndexBuffer.numberOfItems, context.UNSIGNED_SHORT, 0); // отрисовка примитивов
    //context.drawArrays(context.TRIANGLES, 0, cubeVertexBuffer.numberOfItems);
}

function draw() {
    cubeDraw();
}