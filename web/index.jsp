<%-- 
    Document   : index
    Created on : 29 ene 2025, 9:54:01 p. m.
    Author     : beren
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Calculadora VLSM</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8e8f6;
            color: #4a235a;
            text-align: center;
        }
        .container {
            width: 50%;
            margin: auto;
            margin-top: 50px;
            background-color: #fdf4fc;
            border: 2px solid #eac0f1;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }
        h1 {
            background-color: #d5a6d3;
            color: white;
            padding: 15px;
            border-radius: 8px;
        }
        label {
            display: block;
            font-weight: bold;
            margin: 10px 0 5px;
            color: #4a235a;
        }
        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #eac0f1;
            border-radius: 5px;
            font-size: 16px;
            text-align: center;
        }
        button {
            width: 100%;
            background-color: #d5a6d3;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            font-size: 18px;
            cursor: pointer;
            margin-top: 10px;
        }
        button:hover {
            background-color: #b780b6;
        }
    </style>
    <script>
        function generarCampos() {
            let numSubredes = document.getElementById("numSubredes").value;
            let contenedor = document.getElementById("contenedorHosts");
            contenedor.innerHTML = ""; // Limpiar contenido anterior

            for (let i = 1; i <= numSubredes; i++) {
                let label = document.createElement("label");
                label.textContent = "Hosts para Subred " + i + ":";
                let input = document.createElement("input");
                input.type = "number";
                input.name = "hosts";
                input.required = true;
                contenedor.appendChild(label);
                contenedor.appendChild(input);
            }
        }
    </script>
</head>
<body>

    <div class="container">
        <h1>Calculadora VLSM</h1>
        <form action="CalculadoraServlet" method="POST">
            <label for="ip">Dirección IP:</label>
            <input type="text" id="ip" name="ip" placeholder="Ej: 192.168.1.0" required>

            <label for="mascara">Máscara de Red:</label>
            <input type="number" id="mascara" name="mascara" min="1" max="32" required>

            <label for="numSubredes">Número de Subredes:</label>
            <input type="number" id="numSubredes" name="numSubredes" min="1" required>

            <button type="button" onclick="generarCampos()">Generar Campos</button>

            <div id="contenedorHosts"></div>

            <button type="submit">Calcular</button>
        </form>
    </div>

</body>
</html>
