<?php

global $_POST;

//объявляем POST переменные
$x = $_POST['x'];
$y = $_POST['y'];
$r = $_POST['r'];

if (!empty($x)) {
    echo "Приветствую Вас $x $y $r. <br />";
} else {
    echo "Как Вы посмели нажать эту кнопку?";
    echo "<meta http-equiv='refresh' content='15; url=index.html' />";
}