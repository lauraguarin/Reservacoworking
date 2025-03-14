/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "EliminarReservaServlet", urlPatterns = {"/EliminarReservaServlet"})
public class EliminarReservaServlet extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/coworking"; 
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el ID de la reserva desde el formulario
        String id = request.getParameter("id");

        // Validar que el ID no sea nulo, vacío y que sea un número válido
        if (id == null || id.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID de reserva no puede estar vacío.");
            return;
        }
        
        try {
            Integer.parseInt(id);  // Verificar que sea un número válido
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de reserva inválido.");
            return;
        }

        // Conectar a la base de datos y eliminar la reserva con ese ID
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM reservas WHERE id = ?")) {
            
            // Establecer el ID en la consulta preparada
            stmt.setString(1, id);

            // Ejecutar la consulta y verificar si se eliminó alguna fila
            int filasEliminadas = stmt.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("Reserva con ID " + id + " eliminada.");
                request.setAttribute("mensaje", "Reserva eliminada correctamente.");
            } else {
                System.out.println("No se encontró la reserva con ID " + id + ".");
                request.setAttribute("mensaje", "No se encontró la reserva con el ID proporcionado.");
            }

        } catch (SQLException e) {
            log("Error al eliminar la reserva con ID " + id, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocurrió un error al procesar su solicitud.");
            return;
        }

        // Redirigir de nuevo a la lista de reservas o mostrar mensaje de éxito/error
        request.getRequestDispatcher("/ListaReservasServlet").forward(request, response);
    }
}
