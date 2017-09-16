<?php

global $_POST;

$start = microtime(true);

$x = $_POST['x'];
$y = $_POST['y'];
$r = $_POST['r'];

$result;

if ((($x > 0) && ($x <= $r) && ($y >= -$r / 2) && ($y <= 0)) || (($x == 0) && ($y >= -$r / 2) && ($y <= $r)) ||
    (($x < 0) && ($y > 0) && ($y <= $r) && ($x >= -$r / 2) && ($y <= 2 * $x + $r)) || (($x < 0) && ($y < 0) &&
        (($x ^ 2 + $y ^ 2) < $r ^ 2))) {
    $result = "The point passed the test";
} else {
    $result = "The point didn't passed  the test";
}
$finish = microtime(true);
$delta = $finish - $start;
$arr = array('result' => $result, 'current_time' => date("H:i:s d/m/Y"), "work_time" => $delta);
echo json_encode($arr);

