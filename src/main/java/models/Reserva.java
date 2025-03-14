/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;  // Para manejar las fechas de forma más precisa en la base de datos.

public class Reserva {
    private int id;
    private String nombre;
    private Date fecha;
    private String espacio;
    private int duracion;

    // Constructor
    public Reserva(String nombre, Date fecha, String espacio, int id) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.espacio = espacio;
        this.duracion = duracion;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getEspacio() {
        return espacio;
    }

    public int getDuracion() {
        return duracion;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
