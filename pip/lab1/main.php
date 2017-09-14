<?php

global $_POST;

//объявляем POST переменные
$x = $_POST['x'];
$y = $_POST['y'];
$r = $_POST['r'];
$arr = array('x' => $x, 'y' => $y, 'r' => $r);
echo json_encode($arr);



