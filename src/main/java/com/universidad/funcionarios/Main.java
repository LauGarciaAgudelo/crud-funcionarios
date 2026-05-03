package com.universidad.funcionarios;

import java.text.SimpleDateFormat;
import java.util.List;

import com.universidad.funcionarios.dao.FuncionarioDAO;
import com.universidad.funcionarios.dao.FuncionarioDAOImpl;
import com.universidad.funcionarios.exception.DAOException;
import com.universidad.funcionarios.model.Funcionario;

public class Main {

    public static void main(String[] args) {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAOImpl();

        try {
            System.out.println("=== LISTADO INICIAL ===");
            listarFuncionarios(funcionarioDAO);

            Funcionario nuevo = new Funcionario();
            nuevo.setTipoDocumentoId(1);
            nuevo.setNumeroDocumento("100000003");
            nuevo.setNombres("Andrea");
            nuevo.setApellidos("Martínez Ruiz");
            nuevo.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse("1992-03-15"));
            nuevo.setDireccion("Calle 50 #30-20");
            nuevo.setTelefono("3021234567");
            nuevo.setCorreo("andrea.martinez@email.com");
            nuevo.setEstadoCivilId(1);
            nuevo.setFormacionAcademicaId(4);

            funcionarioDAO.crear(nuevo);

            System.out.println("\n=== DESPUÉS DE CREAR ===");
            listarFuncionarios(funcionarioDAO);

            nuevo.setId(3);
            nuevo.setNombres("Andrea Carolina");
            nuevo.setApellidos("Martínez Ruiz");

            funcionarioDAO.actualizar(nuevo);

            System.out.println("\n=== DESPUÉS DE ACTUALIZAR ===");
            listarFuncionarios(funcionarioDAO);

            funcionarioDAO.eliminar(3);

            System.out.println("\n=== DESPUÉS DE ELIMINAR ===");
            listarFuncionarios(funcionarioDAO);

        } catch (DAOException e) {
            System.out.println("Error en la capa DAO: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }

    private static void listarFuncionarios(FuncionarioDAO funcionarioDAO) throws DAOException {
        List<Funcionario> funcionarios = funcionarioDAO.listar();

        for (Funcionario funcionario : funcionarios) {
            System.out.println(
                    funcionario.getId() + " - " +
                    funcionario.getNumeroDocumento() + " - " +
                    funcionario.getNombres() + " " +
                    funcionario.getApellidos()
            );
        }
    }
}