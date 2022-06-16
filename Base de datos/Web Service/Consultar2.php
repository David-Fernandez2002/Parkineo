<?php

include 'Conexion.php';
$parking=$_GET['parking'];

$consulta = "SELECT * FROM reservas WHERE CURDATE() >= fecha_final AND parking = '$parking'";
$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()) {
    $reservas[] = array_map('utf8_encode', $fila);
}

echo json_encode($reservas);
$resultado -> close();

?>