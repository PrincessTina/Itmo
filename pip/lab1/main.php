<?php

global $_POST;

$start = microtime(true);

$x = $_POST['x'];
$y = $_POST['y'];
$r = $_POST['r'];

if ((($x > 0) && (bccomp($r, $x, 256) >= 0) && ($y >= -$r / 2) && ($y <= 0)) ||
    (($x == 0) && ($y >= -$r / 2) && ($y <= $r)) ||
    (($x < 0) && ((($y >= 0) && ($y <= $r) && (bccomp($x, -$r / 2, 256) >= 0) &&
                (bccomp(bcmul(2, $x, 256), $y - $r, 256) >= 0)) ||
            (($y <= 0) && (bccomp(pow($r, 2) - pow($y, 2), bcpow($x, 2, 256), 256) >= 0))))) {
    $result = "The point passed the test";
} else {
    $result = "The point didn't pass the test";
}

$date = date("H:i:s d/m/Y");
$hour = substr($date, 0, 2) + 3;

if ($hour < 10) {
    $hour = "0".$hour;
}
$date = $hour.substr($date, 2, 17);

$finish = microtime(true);
$delta = $finish - $start;
$arr = array('result' => $result, 'current_time' => $date, "work_time" => $delta);

echo json_encode($arr);

