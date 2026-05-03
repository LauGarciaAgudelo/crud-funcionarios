package com.universidad.funcionarios.dao;

import java.util.List;

import com.universidad.funcionarios.exception.DAOException;
import com.universidad.funcionarios.model.Funcionario;

public interface FuncionarioDAO {

    List<Funcionario> listar() throws DAOException;

    void crear(Funcionario funcionario) throws DAOException;

    void actualizar(Funcionario funcionario) throws DAOException;

    void eliminar(int id) throws DAOException;
}