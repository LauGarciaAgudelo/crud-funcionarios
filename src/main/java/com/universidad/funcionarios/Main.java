package com.universidad.funcionarios;

import javax.swing.SwingUtilities;

import com.universidad.funcionarios.view.FuncionarioView;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FuncionarioView view = new FuncionarioView();
            view.setVisible(true);
        });
    }
}