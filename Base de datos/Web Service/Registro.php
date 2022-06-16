<?php
    $conexion = mysqli_connect("localhost", "root", "", "parkineo");

    $usuario = $_POST["usuario"];
    $email = $_POST["email"];
    $contrasena = $_POST["contrasena"];
    $fecha_nacimiento = $_POST["fecha_nacimiento"];
    $sexo = $_POST["sexo"];
    $declaracion = mysqli_prepare($conexion, "INSERT INTO usuarios (usuario, email, contrasena, fecha_nacimiento, sexo) VALUES (?, ?, ?, ?, ?)");

    mysqli_stmt_bind_param($declaracion, "sssss", $usuario, $email, $contrasena, $fecha_nacimiento, $sexo);
    mysqli_stmt_execute($declaracion);

    $respuesta = array();
    $respuesta["success"] = true;

    echo json_encode($respuesta);
?>