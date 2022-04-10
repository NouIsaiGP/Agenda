package com.example.agenda.model;

import java.util.Set;
/*
    Modelo principal para el uso de la aplicacion
 */
public class Contacto {

    private int id;
    private String nombre;
    private String telefono;
    private String cumpleaños;
    private String nota;
    private Set<Contacto>contactos;

    public  Contacto(){
    }

    public Contacto(int id, String nombre, String telefono, String cumpleaños, String nota) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.cumpleaños = cumpleaños;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCumpleaños() {
        return cumpleaños;
    }

    public void setCumpleaños(String cumpleaños) {
        this.cumpleaños = cumpleaños;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Set<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(Set<Contacto> contactos) {
        this.contactos = contactos;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", cumpleaños='" + cumpleaños + '\'' +
                ", nota='" + nota + '\'' +
                ", contactos=" + contactos +
                '}';
    }
}
