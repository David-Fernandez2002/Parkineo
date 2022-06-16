<?php
    $conexion = mysqli_connect("localhost", "root", "", "parkineo");

    $estrellas = $_POST["estrellas"];
    $comentario = $_POST["comentario"];
    $email = $_POST["email"];
    $declaracion = mysqli_prepare($conexion, "INSERT INTO valoraciones (estrellas, comentario, email) VALUES (?, ?, ?)");

    mysqli_stmt_bind_param($declaracion, "dss", $estrellas, $comentario, $email);
    mysqli_stmt_execute($declaracion);

    $respuesta = array();
    $respuesta["success"] = true;

    echo json_encode($respuesta);
?>