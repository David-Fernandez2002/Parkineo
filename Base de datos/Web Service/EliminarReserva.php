<?php

include 'Conexion.php';
$id_reserva=$_POST['id_reserva'];

$consulta="DELETE FROM reservas WHERE id_reserva = ".$id_reserva;
mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion)

?>