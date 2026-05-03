package com.universidad.funcionarios.view;

import com.universidad.funcionarios.config.DatabaseConnection;
import com.universidad.funcionarios.dao.FuncionarioDAO;
import com.universidad.funcionarios.dao.FuncionarioDAOImpl;
import com.universidad.funcionarios.exception.DAOException;
import com.universidad.funcionarios.model.ComboItem;
import com.universidad.funcionarios.model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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

    private final Color backgroundColor = new Color(245, 247, 250);
    private final Color panelColor = Color.WHITE;
    private final Color primaryColor = new Color(37, 99, 235);
    private final Color neutralColor = new Color(100, 116, 139);
    private final Color dangerColor = new Color(220, 38, 38);
    private final Color textColor = new Color(45, 55, 72);

    public FuncionarioView() {
        dateFormat.setLenient(false);

        setTitle("Gestión de Funcionarios");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(backgroundColor);

        initComponents();
        cargarCombos();
        cargarFuncionarios();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));

        JPanel headerPanel = crearHeader();
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

        JPanel formContainer = crearFormulario();
        JPanel tableContainer = crearTabla();

        mainPanel.add(formContainer, BorderLayout.WEST);
        mainPanel.add(tableContainer, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        JLabel title = new JLabel("Gestión de Funcionarios");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel subtitle = new JLabel("CRUD para administrar funcionarios de la universidad");
        subtitle.setForeground(new Color(225, 232, 255));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(subtitle);

        headerPanel.add(textPanel, BorderLayout.WEST);

        return headerPanel;
    }

    private JPanel crearFormulario() {
        JPanel container = new JPanel(new BorderLayout(10, 10));
        container.setPreferredSize(new Dimension(460, 0));
        container.setBackground(panelColor);
        container.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 235)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JLabel formTitle = new JLabel("Datos del funcionario");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        formTitle.setForeground(textColor);
        formTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(panelColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = crearTextField();
        txtId.setEditable(false);

        cmbTipoDocumento = crearComboBox();
        txtNumeroDocumento = crearTextField();
        txtNombres = crearTextField();
        txtApellidos = crearTextField();
        txtFechaNacimiento = crearTextField();
        txtDireccion = crearTextField();
        txtTelefono = crearTextField();
        txtCorreo = crearTextField();
        cmbEstadoCivil = crearComboBox();
        cmbFormacionAcademica = crearComboBox();

        agregarCampoGrid(panelForm, gbc, 0, "ID:", txtId);
        agregarCampoGrid(panelForm, gbc, 1, "Tipo Documento *:", cmbTipoDocumento);
        agregarCampoGrid(panelForm, gbc, 2, "Número Documento *:", txtNumeroDocumento);
        agregarCampoGrid(panelForm, gbc, 3, "Nombres *:", txtNombres);
        agregarCampoGrid(panelForm, gbc, 4, "Apellidos *:", txtApellidos);
        agregarCampoGrid(panelForm, gbc, 5, "Fecha Nacimiento *:", txtFechaNacimiento);
        agregarCampoGrid(panelForm, gbc, 6, "Dirección:", txtDireccion);
        agregarCampoGrid(panelForm, gbc, 7, "Teléfono:", txtTelefono);
        agregarCampoGrid(panelForm, gbc, 8, "Correo:", txtCorreo);
        agregarCampoGrid(panelForm, gbc, 9, "Estado Civil *:", cmbEstadoCivil);
        agregarCampoGrid(panelForm, gbc, 10, "Formación Académica *:", cmbFormacionAcademica);

        JButton btnCrear = crearBoton("Crear", primaryColor);
        JButton btnActualizar = crearBoton("Actualizar", primaryColor);
        JButton btnEliminar = crearBoton("Eliminar", dangerColor);
        JButton btnLimpiar = crearBoton("Limpiar", neutralColor);

        JPanel panelButtons = new JPanel(new GridLayout(2, 2, 10, 10));
        panelButtons.setBackground(panelColor);
        panelButtons.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));
        panelButtons.add(btnCrear);
        panelButtons.add(btnActualizar);
        panelButtons.add(btnEliminar);
        panelButtons.add(btnLimpiar);

        JLabel hint = new JLabel("Formato fecha: yyyy-MM-dd");
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        hint.setForeground(new Color(100, 116, 139));
        hint.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(panelColor);
        bottomPanel.add(panelButtons, BorderLayout.CENTER);
        bottomPanel.add(hint, BorderLayout.SOUTH);

        JScrollPane formScrollPane = new JScrollPane(panelForm);
        formScrollPane.setBorder(null);
        formScrollPane.setBackground(panelColor);
        formScrollPane.getViewport().setBackground(panelColor);
        formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        container.add(formTitle, BorderLayout.NORTH);
        container.add(formScrollPane, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);

        btnCrear.addActionListener(e -> crearFuncionario());
        btnActualizar.addActionListener(e -> actualizarFuncionario());
        btnEliminar.addActionListener(e -> eliminarFuncionario());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        return container;
    }

    private void agregarCampoGrid(JPanel panel, GridBagConstraints gbc, int row, String label, Component component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.35;

        JLabel lbl = crearLabel(label);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.65;

        panel.add(component, gbc);
    }

    private JPanel crearTabla() {
        JPanel container = new JPanel(new BorderLayout(10, 10));
        container.setBackground(panelColor);
        container.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 235)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JLabel tableTitle = new JLabel("Listado de funcionarios");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableTitle.setForeground(textColor);

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
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(230, 235, 240));
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(textColor);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(235, 240, 245));
        header.setForeground(textColor);
        header.setPreferredSize(new Dimension(header.getWidth(), 34));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(225, 230, 235)));

        table.removeColumn(table.getColumnModel().getColumn(12));
        table.removeColumn(table.getColumnModel().getColumn(10));
        table.removeColumn(table.getColumnModel().getColumn(1));
        table.removeColumn(table.getColumnModel().getColumn(0));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFuncionario();
            }
        });

        container.add(tableTitle, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);

        return container;
    }

    private JTextField crearTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(230, 36));
        field.setMinimumSize(new Dimension(230, 36));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return field;
    }

    private JComboBox<ComboItem> crearComboBox() {
        JComboBox<ComboItem> combo = new JComboBox<>();
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(230, 36));
        combo.setMinimumSize(new Dimension(230, 36));
        combo.setBackground(Color.WHITE);
        return combo;
    }

    private JLabel crearLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(textColor);
        return label;
    }

    private void agregarCampo(JPanel panel, String label, Component component) {
        panel.add(crearLabel(label));
        panel.add(component);
    }

    private JButton crearBoton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(9, 12, 9, 12));
        return button;
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
            int modelRow = table.convertRowIndexToModel(row);

            txtId.setText(getTableValue(modelRow, 0));
            seleccionarCombo(cmbTipoDocumento, Integer.parseInt(getTableValue(modelRow, 1)));
            txtNumeroDocumento.setText(getTableValue(modelRow, 3));
            txtNombres.setText(getTableValue(modelRow, 4));
            txtApellidos.setText(getTableValue(modelRow, 5));
            txtFechaNacimiento.setText(getTableValue(modelRow, 6));
            txtDireccion.setText(getTableValue(modelRow, 7));
            txtTelefono.setText(getTableValue(modelRow, 8));
            txtCorreo.setText(getTableValue(modelRow, 9));
            seleccionarCombo(cmbEstadoCivil, Integer.parseInt(getTableValue(modelRow, 10)));
            seleccionarCombo(cmbFormacionAcademica, Integer.parseInt(getTableValue(modelRow, 12)));
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