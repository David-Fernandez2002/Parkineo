<?php

include 'Conexion.php';
$id_parking=$_GET['id_parking'];

$consulta = "SELECT * FROM plazas WHERE id_parking = '$id_parking'";
$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()) {
    $reservas[] = array_map('utf8_encode', $fila);
}

echo json_encode($reservas);
$resultado -> close();

?>