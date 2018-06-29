<?php

global $_POST;

$start = microtime(true);

$x = $_POST['x'];
$y = $_POST['y'];
$r = $_POST['r'];
$offset = $_POST['offset'];

$x_value = floatval($x);
$y_value = floatval($y);
$r_value = intval($r);

if (extension_loaded('bcmath')) {
    $support = "yes";

    if ((check($x)) && (check($y)) && (check($r))) {
        if (($r_value <=5) && ($r_value >= 1) && ($x_value < 3) && ($x_value > -3) && (($y_value == -2) || ($y_value == -1.5) ||
                ($y_value == -1) || ($y_value==-0.5) || ($y_value == 0) || ($y_value == 0.5) || ($y_value == 1) || ($y_value == 1.5) ||
                ($y_value == 2))) {

            if ((($x > 0) && ((bccomp($r, $x, 256) >= 0)) && ($y >= -$r / 2) && ($y <= 0)) ||
                (($x == 0) && ($y >= -$r / 2) && ($y <= $r)) ||
                (($x < 0) && ((($y >= 0) && ($y <= $r) && (bccomp($x, -$r / 2, 256) >= 0) &&
                            (bccomp(bcmul(2, $x, 256), $y - $r, 256) >= 0)) ||
                        (($y <= 0) && (bccomp(pow($r, 2) - pow($y, 2), bcpow($x, 2, 256), 256) >= 0))))) {
                $result = "The point passed the test";
            } else {
                $result = "The point didn't pass the test";
            }
        } else {
            $result = "Data out of ODB";
        }
    } else {
        $result = "Invalid format of data";
    }
} else {
    $support = "no";
    if ((check($x)) && (check($y)) && (check($r))) {
        if (($r_value <=5) && ($r_value >= 1) && ($x_value < 3) && ($x_value > -3) && (($y_value == -2) || ($y_value == -1.5) ||
                ($y_value == -1) || ($y_value==-0.5) || ($y_value == 0) || ($y_value == 0.5) || ($y_value == 1) || ($y_value == 1.5) ||
                ($y_value == 2))) {

            if ((($x > 0) && ($x <= $r) && ($y >= -$r / 2) && ($y <= 0)) ||
                (($x == 0) && ($y >= -$r / 2) && ($y <= $r)) ||
                (($x < 0) && ((($y >= 0) && ($y <= $r) && ($x >= -$r/2) && (2*$x > $y - $r)) ||
                        (($y <= 0) && (pow($r, 2) - pow($y, 2) >= pow($x, 2)))))) {
                $result = "The point passed the test";
            } else {
                $result = "The point didn't pass the test";
            }
        } else {
            $result = "Data out of ODB";
        }
    } else {
        $result = "Invalid format of data";
    }
}

//Перевожу время на нормальное
$date = date("H:i:s d/m/Y");
$hour = substr($date, 0, 2);

if ($support == "no") {
    $hour += $offset - 1;
} else {
    $hour += -$offset;
}

if ($hour < 10) {
    $hour = "0" . $hour;
}

$date = $hour . substr($date, 2, 17);

$finish = microtime(true);
$delta = $finish - $start;


echo $result . ";" . $date . ";" . $delta . ";" . $x . ";" . $y . ";" . $r . ";" . $support;

function check($a) {
    if (preg_match("/^-?\d+\.?\d*$/", trim($a)) == 1) {
        return (trim($a) !== "");
    } else {
        return false;
    }
}

