package Servlets;

import models.Reserva;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ListaReservasServlet", urlPatterns = {"/ListaReservasServlet"})
public class ListaReservasServlet extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/coworking";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private static final Logger LOGGER = Logger.getLogger(ListaReservasServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Reserva> reservas = new ArrayList<>();
        String mensaje = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            LOGGER.log(Level.INFO, "Conexión establecida con la base de datos");

            String sql = "SELECT * FROM reservas";
            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Date fecha = rs.getDate("fecha");
                String espacio = rs.getString("espacio");
                int duracion = rs.getInt("duracion");

                Reserva reserva = new Reserva(nombre, fecha, espacio, id);
                reservas.add(reserva);
            }

            LOGGER.log(Level.INFO, "Número de reservas obtenidas: {0}", reservas.size());

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener las reservas", e);
            mensaje = "Ocurrió un error al obtener las reservas. Inténtelo de nuevo más tarde.";
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error al cerrar la conexión", e);
            }
        }

        request.setAttribute("reservas", reservas);  // Cambiado a 'reservas' para coincidir con la JSP
        if (mensaje != null) {
            request.setAttribute("mensaje", mensaje);
        }
        request.getRequestDispatcher("reservas.jsp").forward(request, response);
    }
}
