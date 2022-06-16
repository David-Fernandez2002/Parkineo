<?php

include 'Conexion.php';
$email=$_GET['email'];

$consulta = "SELECT * FROM reservas WHERE email = '$email'";
$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()) {
    $reservas[] = array_map('utf8_encode', $fila);
}

echo json_encode($reservas);
$resultado -> close();

?>