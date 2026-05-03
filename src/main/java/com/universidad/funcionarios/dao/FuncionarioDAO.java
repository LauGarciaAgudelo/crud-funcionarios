package com.universidad.funcionarios.dao;

import com.universidad.funcionarios.model.Funcionario;
import com.universidad.funcionarios.exception.DAOException;

import java.util.List;

public interface FuncionarioDAO {

    List<Funcionario> listar() throws DAOException;

    void crear(Funcionario funcionario) throws DAOException;

    void actualizar(Funcionario funcionario) throws DAOException;

    void eliminar(int id) throws DAOException;
}