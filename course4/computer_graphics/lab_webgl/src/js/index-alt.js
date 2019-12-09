var scene = new THREE.Scene();
var camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 1, 1000);
var renderer = new THREE.WebGLRenderer();
var angleX = 0.1;
var angleY = 2;

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

window.onload = function () {
    var geometry = new THREE.CubeGeometry(200, 200, 200);
    var material = new THREE.MeshBasicMaterial({color: 0x00ffff});
    var cube = new THREE.Mesh(geometry, material);

    scene.add(cube);
    camera.position.z = 340;
    renderer.setSize(window.innerWidth, window.innerHeight);

    document.body.appendChild(renderer.domElement);
    document.addEventListener('keydown', handleKeyDown, false);

    (function animate() {
        requestAnimationFrame(animate);

        cube.rotation.x = angleX;
        cube.rotation.y = angleY;

        renderer.render(scene, camera);
    })();
};

