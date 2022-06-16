<?php
    $conexion = mysqli_connect("localhost", "root", "", "parkineo");

    $email = $_POST["email"];
    $contrasena = $_POST["contrasena"];

    $declaracion = mysqli_prepare($conexion, "SELECT * FROM usuarios WHERE email = ? AND contrasena = ?");
    mysqli_stmt_bind_param($declaracion, "ss", $email, $contrasena);
    mysqli_stmt_execute($declaracion);

    mysqli_stmt_store_result($declaracion);
    mysqli_stmt_bind_result($declaracion, $id_usuario, $usuario, $email, $contrasena, $fecha_nacimiento, $sexo);

    $respuesta = array();
    $respuesta["success"] = false;

    while(mysqli_stmt_fetch($declaracion)) {
        $respuesta["success"] = true;
        $respuesta["usuario"] = $usuario;
        $respuesta["email"] = $email;
        $respuesta["contrasena"] = $contrasena;
        $respuesta["fecha_nacimiento"] = $fecha_nacimiento;
        $respuesta["sexo"] = $sexo;
    }

    echo json_encode($respuesta);
?>