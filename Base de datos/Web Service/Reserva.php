<?php
    $conexion = mysqli_connect("localhost", "root", "", "parkineo");

    $titular = $_POST["titular"];
    $matricula = $_POST["matricula"];
    $vehiculo = $_POST["vehiculo"];
    $plaza = $_POST["plaza"];
    $parking = $_POST["parking"];
    $dias = $_POST["dias"];
    $fecha_reserva = $_POST["fecha_reserva"];
    $fecha_final = $_POST["fecha_final"];
    $seguro = $_POST["seguro"];
    $email = $_POST["email"];
    $declaracion = mysqli_prepare($conexion, "INSERT INTO reservas (titular, matricula, vehiculo, plaza, parking, dias, fecha_reserva, fecha_final, seguro, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

    mysqli_stmt_bind_param($declaracion, "sssssissss", $titular, $matricula, $vehiculo, $plaza, $parking, $dias, $fecha_reserva, $fecha_final, $seguro, $email);
    mysqli_stmt_execute($declaracion);

    $respuesta = array();
    $respuesta["success"] = true;

    echo json_encode($respuesta);
?>