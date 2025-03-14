<%-- 
    Document   : reservas.jsp
    Created on : 14/03/2025, 12:27:04 p. m.
    Author     : Laura
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>   
<%@page import="models.Reserva"%>   

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Reservas</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        h1 {
            text-align: center;
        }

        button {
            padding: 5px 10px;
            background-color: #ff6666;
            color: white;
            border: none;
            cursor: pointer;
        }

        button:hover {
            background-color: #ff3333;
        }

        .mensaje-error {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>
    <h1>Lista de Reservas</h1>

    <%
        String mensaje = (String) request.getAttribute("mensaje");
        if (mensaje != null) {
    %>
        <p class="mensaje-error"><%= mensaje %></p>
    <%
        }
    %>

    <table>
        <thead>
            <tr>
                <th>ID Reserva</th>
                <th>Nombre</th>
                <th>Fecha</th>
                <th>Espacio</th>
                <th>Duración</th>
                <th>Acción</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Reserva> reservas = (List<Reserva>) request.getAttribute("reservas");
                if (reservas != null && !reservas.isEmpty()) {
                    for (Reserva reserva : reservas) {
            %>
            <tr>
                <td><%= reserva.getId() %></td>
                <td><%= reserva.getNombre() %></td>
                <td><%= reserva.getFecha() %></td>
                <td><%= reserva.getEspacio() %></td>
                <td><%= reserva.getDuracion() %> horas</td>
                <td>
                    <form action="EliminarReservaServlet" method="POST">
                        <input type="hidden" name="id" value="<%= reserva.getId() %>">
                        <button type="submit">Eliminar</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="6">No hay reservas disponibles.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
