package Servlets;

import models.Reserva;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ReservaServlet", urlPatterns = {"/ReservaServlet"})
public class ReservaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Debug: Verificar si los datos llegan al servidor
        System.out.println("Procesando datos del formulario de reserva...");

        // Validar entradas
        String errorMessage = validarEntradas(request);
        if (errorMessage != null) {
            System.out.println("Error en los datos del formulario: " + errorMessage);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMessage);
            return;
        }

        try {
            // Procesar y crear la nueva reserva
            Reserva nuevaReserva = crearReservaDesdeFormulario(request);

            // Guardar la reserva en la base de datos
            guardarReservaEnBD(nuevaReserva);

            // Debug: Confirmación de reserva creada
            System.out.println("Reserva creada con éxito: " + nuevaReserva.toString());

            // Redirigir a una página de éxito o lista de reservas
            response.sendRedirect("ListaReservasServlet?success=Reserva creada con éxito.");
        } catch (Exception e) {
            // Enviar mensaje de error a la página de error
            System.out.println("Ocurrió un error: " + e.getMessage());
            response.sendRedirect("error.jsp?message=" + e.getMessage());
        }
    }

    // Validar las entradas del formulario
    private String validarEntradas(HttpServletRequest request) {
        String nombre = request.getParameter("nombre");
        String fechaStr = request.getParameter("fecha");
        String espacio = request.getParameter("espacio");
        String duracionStr = request.getParameter("duracion");

        if (nombre == null || nombre.isEmpty()) return "El nombre es obligatorio.";
        if (fechaStr == null || fechaStr.isEmpty()) return "La fecha es obligatoria.";
        if (espacio == null || espacio.isEmpty()) return "El espacio es obligatorio.";
        if (duracionStr == null || duracionStr.isEmpty()) return "La duración es obligatoria.";

        try {
            Integer.parseInt(duracionStr);
        } catch (NumberFormatException e) {
            return "La duración debe ser un número válido.";
        }

        try {
            Date.valueOf(fechaStr);
        } catch (IllegalArgumentException e) {
            return "El formato de la fecha es inválido.";
        }

        return null;
    }

    // Crear una reserva desde el formulario
    private Reserva crearReservaDesdeFormulario(HttpServletRequest request) {
        String nombre = request.getParameter("nombre");
        String fechaStr = request.getParameter("fecha");
        String espacio = request.getParameter("espacio");
        int duracion = Integer.parseInt(request.getParameter("duracion"));
        Date fecha = Date.valueOf(fechaStr);

        return new Reserva(nombre, fecha, espacio, duracion); // 'id' será auto-generado en la base de datos
    }

    // Guardar la reserva en la base de datos
    private void guardarReservaEnBD(Reserva reserva) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Conectar a la base de datos (usa las credenciales adecuadas)
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tu_base_de_datos", "tu_usuario", "tu_contraseña");

            // Crear la consulta SQL para insertar la reserva
            String sql = "INSERT INTO Reservas (nombre, fecha, espacio, duracion) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, reserva.getNombre());
            stmt.setDate(2, reserva.getFecha());
            stmt.setString(3, reserva.getEspacio());
            stmt.setInt(4, reserva.getDuracion());

            // Ejecutar la consulta
            stmt.executeUpdate();
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    // Obtener la lista de reservas desde la base de datos
    public static List<Reserva> obtenerReservasDeBD() throws SQLException {
        List<Reserva> listaReservas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Conectar a la base de datos
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tu_base_de_datos", "tu_usuario", "tu_contraseña");

            // Crear la consulta SQL para obtener todas las reservas
            String sql = "SELECT * FROM Reservas";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            // Iterar sobre los resultados y crear objetos Reserva
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Date fecha = rs.getDate("fecha");
                String espacio = rs.getString("espacio");
                int duracion = rs.getInt("duracion");

                Reserva reserva = new Reserva(nombre, fecha, espacio, id);
                listaReservas.add(reserva);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return listaReservas;
    }
}
