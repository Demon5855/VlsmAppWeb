<%-- 
    Document   : resultados
    Created on : 30 ene 2025, 1:07:15 a. m.
    Author     : beren
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultados del Cálculo VLSM</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8e8f6;
            color: #4a235a;
            text-align: center;
        }
        .container {
            width: 80%;
            margin: 20px auto;
            padding: 20px;
            background-color: #fdf4fc;
            border: 2px solid #eac0f1;
            border-radius: 10px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }
        h1 {
            background-color: #d5a6d3;
            color: white;
            padding: 15px;
            border-radius: 8px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #fdf4fc;
            border: 2px solid #eac0f1;
        }
        th, td {
            border: 1px solid #eac0f1;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #d5a6d3;
            color: white;
        }
        .resumen-binario {
            background-color: #e0f7fa;
            padding: 20px;
            border-radius: 8px;
            text-align: left;
            font-family: monospace;
            margin-top: 20px;
        }
        button {
            background-color: #d5a6d3;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin: 20px;
            font-size: 16px;
        }
        button:hover {
            background-color: #b780b6;
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>Resultados del Cálculo VLSM</h1>

        <!-- 📌 Tabla de Resultados -->
        <table>
            <tr>
                <th>Subred</th>
                <th>Dirección de Red</th>
                <th>Primera IP Utilizable</th>
                <th>Última IP Utilizable</th>
                <th>Broadcast</th>
                <th>Máscara</th>
            </tr>
            <%
                List<String[]> resultados = (List<String[]>) session.getAttribute("resultados");
                if (resultados != null) {
                    for (String[] fila : resultados) {
            %>
            <tr>
                <td><%= fila[0] %></td>
                <td><%= fila[1] %></td>
                <td><%= fila[2] %></td>
                <td><%= fila[3] %></td>
                <td><%= fila[4] %></td>
                <td><%= fila[5] %></td>
            </tr>
            <%
                    }
                }
            %>
        </table>

        <!-- 📌 Resumen Binario -->
        <div class="resumen-binario">
            <h2>Resumen del Cálculo</h2>
            <pre>
<%
    List<String> resumenBinario = (List<String>) session.getAttribute("resumenVLSM");
    if (resumenBinario != null) {
        for (String linea : resumenBinario) {
            out.println(linea);
        }
    }
%>
            </pre>
        </div>

        <button onclick="window.location.href='index.jsp'">Volver al Inicio</button>
        <button onclick="window.location.href='GenerarPDFServlet'">Generar PDF</button>
    </div>

</body>
</html>
