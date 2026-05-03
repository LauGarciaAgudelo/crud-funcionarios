package com.universidad.funcionarios.view;

import com.universidad.funcionarios.config.DatabaseConnection;
import com.universidad.funcionarios.dao.FuncionarioDAO;
import com.universidad.funcionarios.dao.FuncionarioDAOImpl;
import com.universidad.funcionarios.exception.DAOException;
import com.universidad.funcionarios.model.ComboItem;
import com.universidad.funcionarios.model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class FuncionarioView extends JFrame {

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAOImpl();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private JTextField txtId;
    private JComboBox<ComboItem> cmbTipoDocumento;
    private JTextField txtNumeroDocumento;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtFechaNacimiento;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JComboBox<ComboItem> cmbEstadoCivil;
    private JComboBox<ComboItem> cmbFormacionAcademica;

    private JTable table;
    private DefaultTableModel tableModel;

    public FuncionarioView() {
        dateFormat.setLenient(false);

        setTitle("CRUD Funcionarios");
        setSize(1250, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        cargarCombos();
        cargarFuncionarios();
    }

    private void initComponents() {
        JPanel panelForm = new JPanel(new GridLayout(11, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del funcionario"));

        txtId = new JTextField();
        txtId.setEditable(false);

        cmbTipoDocumento = new JComboBox<>();
        txtNumeroDocumento = new JTextField();
        txtNombres = new JTextField();
        txtApellidos = new JTextField();
        txtFechaNacimiento = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();
        cmbEstadoCivil = new JComboBox<>();
        cmbFormacionAcademica = new JComboBox<>();

        panelForm.add(new JLabel("ID:"));
        panelForm.add(txtId);

        panelForm.add(new JLabel("Tipo Documento *:"));
        panelForm.add(cmbTipoDocumento);

        panelForm.add(new JLabel("Número Documento *:"));
        panelForm.add(txtNumeroDocumento);

        panelForm.add(new JLabel("Nombres *:"));
        panelForm.add(txtNombres);

        panelForm.add(new JLabel("Apellidos *:"));
        panelForm.add(txtApellidos);

        panelForm.add(new JLabel("Fecha Nacimiento * (yyyy-MM-dd):"));
        panelForm.add(txtFechaNacimiento);

        panelForm.add(new JLabel("Dirección:"));
        panelForm.add(txtDireccion);

        panelForm.add(new JLabel("Teléfono:"));
        panelForm.add(txtTelefono);

        panelForm.add(new JLabel("Correo:"));
        panelForm.add(txtCorreo);

        panelForm.add(new JLabel("Estado Civil *:"));
        panelForm.add(cmbEstadoCivil);

        panelForm.add(new JLabel("Formación Académica *:"));
        panelForm.add(cmbFormacionAcademica);

        JButton btnCrear = new JButton("Crear");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnCrear);
        panelButtons.add(btnActualizar);
        panelButtons.add(btnEliminar);
        panelButtons.add(btnLimpiar);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
                "ID",
                "Tipo Doc ID",
                "Tipo Documento",
                "Documento",
                "Nombres",
                "Apellidos",
                "Fecha Nacimiento",
                "Dirección",
                "Teléfono",
                "Correo",
                "Estado Civil ID",
                "Estado Civil",
                "Formación ID",
                "Formación Académica"
        });

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Listado de funcionarios"));

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.add(panelForm, BorderLayout.CENTER);
        leftPanel.add(panelButtons, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        btnCrear.addActionListener(e -> crearFuncionario());
        btnActualizar.addActionListener(e -> actualizarFuncionario());
        btnEliminar.addActionListener(e -> eliminarFuncionario());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFuncionario();
            }
        });
    }

    private void cargarCombos() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            cargarCombo(conn, "SELECT id, nombre FROM tipo_documento ORDER BY id", cmbTipoDocumento);
            cargarCombo(conn, "SELECT id, nombre FROM estado_civil ORDER BY id", cmbEstadoCivil);
            cargarCombo(conn, "SELECT id, nivel AS nombre FROM formacion_academica ORDER BY id", cmbFormacionAcademica);
        } catch (Exception e) {
            mostrarError("Error al cargar combos: " + e.getMessage());
        }
    }

    private void cargarCombo(Connection conn, String sql, JComboBox<ComboItem> combo) throws SQLException {
        combo.removeAllItems();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                combo.addItem(new ComboItem(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ));
            }
        }
    }

    private void cargarFuncionarios() {
        try {
            tableModel.setRowCount(0);
            List<Funcionario> funcionarios = funcionarioDAO.listar();

            for (Funcionario f : funcionarios) {
                tableModel.addRow(new Object[]{
                        f.getId(),
                        f.getTipoDocumentoId(),
                        f.getTipoDocumentoNombre(),
                        f.getNumeroDocumento(),
                        f.getNombres(),
                        f.getApellidos(),
                        f.getFechaNacimiento(),
                        f.getDireccion(),
                        f.getTelefono(),
                        f.getCorreo(),
                        f.getEstadoCivilId(),
                        f.getEstadoCivilNombre(),
                        f.getFormacionAcademicaId(),
                        f.getFormacionAcademicaNombre()
                });
            }

        } catch (DAOException e) {
            mostrarError("Error al cargar funcionarios: " + e.getMessage());
        }
    }

    private void crearFuncionario() {
        try {
            Funcionario funcionario = construirFuncionarioDesdeFormulario();
            funcionarioDAO.crear(funcionario);

            cargarFuncionarios();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Funcionario creado correctamente.");

        } catch (Exception e) {
            mostrarError("Error al crear funcionario: " + e.getMessage());
        }
    }

    private void actualizarFuncionario() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                mostrarError("Debe seleccionar un funcionario para actualizar.");
                return;
            }

            Funcionario funcionario = construirFuncionarioDesdeFormulario();
            funcionario.setId(Integer.parseInt(txtId.getText().trim()));

            funcionarioDAO.actualizar(funcionario);

            cargarFuncionarios();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Funcionario actualizado correctamente.");

        } catch (Exception e) {
            mostrarError("Error al actualizar funcionario: " + e.getMessage());
        }
    }

    private void eliminarFuncionario() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                mostrarError("Debe seleccionar un funcionario para eliminar.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está segura de eliminar este funcionario?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(txtId.getText().trim());
                funcionarioDAO.eliminar(id);

                cargarFuncionarios();
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Funcionario eliminado correctamente.");
            }

        } catch (Exception e) {
            mostrarError("Error al eliminar funcionario: " + e.getMessage());
        }
    }

    private Funcionario construirFuncionarioDesdeFormulario() throws ParseException {
        validarFormulario();

        Funcionario funcionario = new Funcionario();

        funcionario.setTipoDocumentoId(((ComboItem) cmbTipoDocumento.getSelectedItem()).getId());
        funcionario.setNumeroDocumento(txtNumeroDocumento.getText().trim());
        funcionario.setNombres(txtNombres.getText().trim());
        funcionario.setApellidos(txtApellidos.getText().trim());
        funcionario.setFechaNacimiento(dateFormat.parse(txtFechaNacimiento.getText().trim()));
        funcionario.setDireccion(txtDireccion.getText().trim());
        funcionario.setTelefono(txtTelefono.getText().trim());
        funcionario.setCorreo(txtCorreo.getText().trim());
        funcionario.setEstadoCivilId(((ComboItem) cmbEstadoCivil.getSelectedItem()).getId());
        funcionario.setFormacionAcademicaId(((ComboItem) cmbFormacionAcademica.getSelectedItem()).getId());

        return funcionario;
    }

    private void validarFormulario() {
        if (cmbTipoDocumento.getSelectedItem() == null
                || cmbEstadoCivil.getSelectedItem() == null
                || cmbFormacionAcademica.getSelectedItem() == null
                || txtNumeroDocumento.getText().trim().isEmpty()
                || txtNombres.getText().trim().isEmpty()
                || txtApellidos.getText().trim().isEmpty()
                || txtFechaNacimiento.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe completar todos los campos obligatorios marcados con *.");
        }

        try {
            dateFormat.parse(txtFechaNacimiento.getText().trim());
        } catch (ParseException e) {
            throw new IllegalArgumentException("La fecha debe tener el formato yyyy-MM-dd.");
        }

        String correo = txtCorreo.getText().trim();

        if (!correo.isEmpty() && !correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido.");
        }
    }

    private void seleccionarFuncionario() {
        int row = table.getSelectedRow();

        if (row >= 0) {
            txtId.setText(getTableValue(row, 0));
            seleccionarCombo(cmbTipoDocumento, Integer.parseInt(getTableValue(row, 1)));
            txtNumeroDocumento.setText(getTableValue(row, 3));
            txtNombres.setText(getTableValue(row, 4));
            txtApellidos.setText(getTableValue(row, 5));
            txtFechaNacimiento.setText(getTableValue(row, 6));
            txtDireccion.setText(getTableValue(row, 7));
            txtTelefono.setText(getTableValue(row, 8));
            txtCorreo.setText(getTableValue(row, 9));
            seleccionarCombo(cmbEstadoCivil, Integer.parseInt(getTableValue(row, 10)));
            seleccionarCombo(cmbFormacionAcademica, Integer.parseInt(getTableValue(row, 12)));
        }
    }

    private void seleccionarCombo(JComboBox<ComboItem> combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).getId() == id) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }

    private String getTableValue(int row, int column) {
        Object value = tableModel.getValueAt(row, column);
        return value == null ? "" : value.toString();
    }

    private void limpiarFormulario() {
        txtId.setText("");
        cmbTipoDocumento.setSelectedIndex(cmbTipoDocumento.getItemCount() > 0 ? 0 : -1);
        txtNumeroDocumento.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtFechaNacimiento.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        cmbEstadoCivil.setSelectedIndex(cmbEstadoCivil.getItemCount() > 0 ? 0 : -1);
        cmbFormacionAcademica.setSelectedIndex(cmbFormacionAcademica.getItemCount() > 0 ? 0 : -1);
        table.clearSelection();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}