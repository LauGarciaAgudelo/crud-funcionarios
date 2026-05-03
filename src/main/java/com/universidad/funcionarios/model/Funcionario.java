package com.universidad.funcionarios.model;

import java.util.Date;

public class Funcionario {

    private int id;
    private int tipoDocumentoId;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private Date fechaNacimiento;
    private String direccion;
    private String telefono;
    private String correo;
    private int estadoCivilId;
    private int formacionAcademicaId;

    public Funcionario() {}

    public Funcionario(int id, int tipoDocumentoId, String numeroDocumento, String nombres,
                       String apellidos, Date fechaNacimiento, String direccion,
                       String telefono, String correo, int estadoCivilId,
                       int formacionAcademicaId) {
        this.id = id;
        this.tipoDocumentoId = tipoDocumentoId;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.estadoCivilId = estadoCivilId;
        this.formacionAcademicaId = formacionAcademicaId;
    }

    // Getters y Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTipoDocumentoId() { return tipoDocumentoId; }
    public void setTipoDocumentoId(int tipoDocumentoId) { this.tipoDocumentoId = tipoDocumentoId; }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public int getEstadoCivilId() { return estadoCivilId; }
    public void setEstadoCivilId(int estadoCivilId) { this.estadoCivilId = estadoCivilId; }

    public int getFormacionAcademicaId() { return formacionAcademicaId; }
    public void setFormacionAcademicaId(int formacionAcademicaId) { this.formacionAcademicaId = formacionAcademicaId; }
}