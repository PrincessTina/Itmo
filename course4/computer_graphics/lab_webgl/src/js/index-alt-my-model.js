var scene = new THREE.Scene();
var camera = new THREE.PerspectiveCamera(60, window.innerWidth / window.innerHeight, 1, 1000); // 55
var loader = new THREE.GLTFLoader();
var renderer = new THREE.WebGLRenderer();
var angleZ = 0;
var angleY = -0.1;

function handleKeyDown(e) {
    switch (e.keyCode) {
        case 39:  // стрелка вправо
            angleY += 0.1;
            break;
        case 37:  // стрелка влево
            angleY -= 0.1;
            break;
        case 40:  // стрелка вниз
            angleZ += 0.5;
            break;
        case 38:  // стрелка вверх
            angleZ -= 0.5;
            break;
        case 32: // пробел
            angleY = -0.1;
            angleZ = 0;
            break;
        default:
            console.log(e.keyCode);
    }
}

function onWindowResize() {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
}

window.onload = function () {
    var model;

    loader.load('resources/models/roboDog.glb',
        function (gltf) {
            model = gltf.scene;
            scene.add(model);

            console.log(model.coords);
        },
        function (xhr) {
            console.log(( xhr.loaded / xhr.total * 100 ) + '% loaded');
        },
        function (error) {
            console.error(error);
        });

    scene.add(new THREE.AmbientLight(0x404040, 5)); // источник света

    camera.position.z = 2;
    //camera.position.y = 25;

    renderer.setSize(window.innerWidth, window.innerHeight);

    document.body.appendChild(renderer.domElement);
    document.addEventListener('keydown', handleKeyDown, false);
    window.addEventListener('resize', onWindowResize, false);

    (function animate() {
        requestAnimationFrame(animate);

        if (model !== undefined) {
            model.position.z = angleZ;
            model.rotation.y = angleY;
        }

        renderer.render(scene, camera);
    })();
};