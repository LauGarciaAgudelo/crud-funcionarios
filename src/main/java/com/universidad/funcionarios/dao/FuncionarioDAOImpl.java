package com.universidad.funcionarios.dao;

import com.universidad.funcionarios.config.DatabaseConnection;
import com.universidad.funcionarios.model.Funcionario;
import com.universidad.funcionarios.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAOImpl implements FuncionarioDAO {

    @Override
    public List<Funcionario> listar() throws DAOException {
        List<Funcionario> lista = new ArrayList<>();

        String sql = "SELECT * FROM funcionarios";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
                f.setNumeroDocumento(rs.getString("numero_documento"));
                f.setNombres(rs.getString("nombres"));
                f.setApellidos(rs.getString("apellidos"));
                f.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                f.setDireccion(rs.getString("direccion"));
                f.setTelefono(rs.getString("telefono"));
                f.setCorreo(rs.getString("correo"));
                f.setEstadoCivilId(rs.getInt("estado_civil_id"));
                f.setFormacionAcademicaId(rs.getInt("formacion_academica_id"));

                lista.add(f);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al listar funcionarios", e);
        }

        return lista;
    }

    @Override
    public void crear(Funcionario f) throws DAOException {
        String sql = "INSERT INTO funcionarios (tipo_documento_id, numero_documento, nombres, apellidos, fecha_nacimiento, direccion, telefono, correo, estado_civil_id, formacion_academica_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, f.getTipoDocumentoId());
            stmt.setString(2, f.getNumeroDocumento());
            stmt.setString(3, f.getNombres());
            stmt.setString(4, f.getApellidos());
            stmt.setDate(5, new java.sql.Date(f.getFechaNacimiento().getTime()));
            stmt.setString(6, f.getDireccion());
            stmt.setString(7, f.getTelefono());
            stmt.setString(8, f.getCorreo());
            stmt.setInt(9, f.getEstadoCivilId());
            stmt.setInt(10, f.getFormacionAcademicaId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al crear funcionario", e);
        }
    }

    @Override
    public void actualizar(Funcionario f) throws DAOException {
        String sql = "UPDATE funcionarios SET nombres=?, apellidos=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getNombres());
            stmt.setString(2, f.getApellidos());
            stmt.setInt(3, f.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al actualizar funcionario", e);
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM funcionarios WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al eliminar funcionario", e);
        }
    }
}