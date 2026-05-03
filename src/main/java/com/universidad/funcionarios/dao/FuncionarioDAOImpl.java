package com.universidad.funcionarios.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.universidad.funcionarios.config.DatabaseConnection;
import com.universidad.funcionarios.exception.DAOException;
import com.universidad.funcionarios.model.Funcionario;

public class FuncionarioDAOImpl implements FuncionarioDAO {

    @Override
    public List<Funcionario> listar() throws DAOException {
        List<Funcionario> funcionarios = new ArrayList<>();

        String sql = """
            SELECT 
                f.id,
                f.tipo_documento_id,
                td.nombre AS tipo_documento_nombre,
                f.numero_documento,
                f.nombres,
                f.apellidos,
                f.fecha_nacimiento,
                f.direccion,
                f.telefono,
                f.correo,
                f.estado_civil_id,
                ec.nombre AS estado_civil_nombre,
                f.formacion_academica_id,
                fa.nivel AS formacion_academica_nombre
            FROM funcionarios f
            INNER JOIN tipo_documento td ON f.tipo_documento_id = td.id
            INNER JOIN estado_civil ec ON f.estado_civil_id = ec.id
            INNER JOIN formacion_academica fa ON f.formacion_academica_id = fa.id
            ORDER BY f.id
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();

                funcionario.setId(rs.getInt("id"));
                funcionario.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
                funcionario.setTipoDocumentoNombre(rs.getString("tipo_documento_nombre"));
                funcionario.setNumeroDocumento(rs.getString("numero_documento"));
                funcionario.setNombres(rs.getString("nombres"));
                funcionario.setApellidos(rs.getString("apellidos"));
                funcionario.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                funcionario.setDireccion(rs.getString("direccion"));
                funcionario.setTelefono(rs.getString("telefono"));
                funcionario.setCorreo(rs.getString("correo"));
                funcionario.setEstadoCivilId(rs.getInt("estado_civil_id"));
                funcionario.setEstadoCivilNombre(rs.getString("estado_civil_nombre"));
                funcionario.setFormacionAcademicaId(rs.getInt("formacion_academica_id"));
                funcionario.setFormacionAcademicaNombre(rs.getString("formacion_academica_nombre"));

                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al listar funcionarios.", e);
        }

        return funcionarios;
    }

    @Override
    public void crear(Funcionario funcionario) throws DAOException {
        String sql = """
            INSERT INTO funcionarios (
                tipo_documento_id,
                numero_documento,
                nombres,
                apellidos,
                fecha_nacimiento,
                direccion,
                telefono,
                correo,
                estado_civil_id,
                formacion_academica_id
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, funcionario.getTipoDocumentoId());
            stmt.setString(2, funcionario.getNumeroDocumento());
            stmt.setString(3, funcionario.getNombres());
            stmt.setString(4, funcionario.getApellidos());
            stmt.setDate(5, new java.sql.Date(funcionario.getFechaNacimiento().getTime()));
            stmt.setString(6, funcionario.getDireccion());
            stmt.setString(7, funcionario.getTelefono());
            stmt.setString(8, funcionario.getCorreo());
            stmt.setInt(9, funcionario.getEstadoCivilId());
            stmt.setInt(10, funcionario.getFormacionAcademicaId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al crear funcionario.", e);
        }
    }

    @Override
    public void actualizar(Funcionario funcionario) throws DAOException {
        String sql = """
            UPDATE funcionarios
            SET tipo_documento_id = ?,
                numero_documento = ?,
                nombres = ?,
                apellidos = ?,
                fecha_nacimiento = ?,
                direccion = ?,
                telefono = ?,
                correo = ?,
                estado_civil_id = ?,
                formacion_academica_id = ?
            WHERE id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, funcionario.getTipoDocumentoId());
            stmt.setString(2, funcionario.getNumeroDocumento());
            stmt.setString(3, funcionario.getNombres());
            stmt.setString(4, funcionario.getApellidos());
            stmt.setDate(5, new java.sql.Date(funcionario.getFechaNacimiento().getTime()));
            stmt.setString(6, funcionario.getDireccion());
            stmt.setString(7, funcionario.getTelefono());
            stmt.setString(8, funcionario.getCorreo());
            stmt.setInt(9, funcionario.getEstadoCivilId());
            stmt.setInt(10, funcionario.getFormacionAcademicaId());
            stmt.setInt(11, funcionario.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al actualizar funcionario.", e);
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM funcionarios WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al eliminar funcionario.", e);
        }
    }
}