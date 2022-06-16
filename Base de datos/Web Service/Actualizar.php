<?php

include 'Conexion.php';
$id_parking = $_POST['id_parking'];
$nombre = $_POST['nombre'];

$consulta="UPDATE plazas SET disponibilidad = 'F' WHERE id_parking = ".$id_parking." AND nombre = '".$nombre."'";
mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);

?>