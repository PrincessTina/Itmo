<?php

global $_POST;

//объявляем POST переменные
$x = $_POST['x'];

if (!empty($x)) {
    echo "Приветствую Вас $x. <br />";
} else {
    echo "Как Вы посмели нажать эту кнопку?";
    echo "<meta http-equiv='refresh' content='15; url=index.html' />";
}