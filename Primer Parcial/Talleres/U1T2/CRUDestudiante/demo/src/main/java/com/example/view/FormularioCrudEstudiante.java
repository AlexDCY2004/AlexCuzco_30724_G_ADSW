package com.example.view;

import com.example.controller.ControlEstudiante;
import com.example.model.Estudiante;
import com.example.model.Resultado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Component;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class FormularioCrudEstudiante extends JFrame {

    private int txtId;
    private String txtNombre;
    private int txtEdad;
    private final ControlEstudiante control;
    private JTextField txtIdInput;
    private JTextField txtNombreInput;
    private JTextField txtEdadInput;

    public FormularioCrudEstudiante() {
        this.control = new ControlEstudiante();
        setTitle("CRUD Estudiante");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new GridLayout(2, 1, 0, 8));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        txtIdInput = new JTextField();
        txtNombreInput = new JTextField();
        txtEdadInput = new JTextField();

        JPanel panelForm = new JPanel(new GridLayout(1, 3, 8, 0));

        JPanel panelId = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        panelId.add(new JLabel("ID:"));
        txtIdInput.setColumns(8);
        panelId.add(txtIdInput);
        panelForm.add(panelId);

        JPanel panelNombre = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        panelNombre.add(new JLabel("Nombre:"));
        txtNombreInput.setColumns(16);
        panelNombre.add(txtNombreInput);
        panelForm.add(panelNombre);

        JPanel panelEdad = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        panelEdad.add(new JLabel("Edad:"));
        txtEdadInput.setColumns(8);
        panelEdad.add(txtEdadInput);
        panelForm.add(panelEdad);

        panelSuperior.add(panelForm);

        DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Edad"}, 0);
        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 8, 0));

        JButton btnAgregar = new JButton("Agregar Estudiante");
        JButton btnActualizar = new JButton("Actualizar Estudiante");
        JButton btnEliminar = new JButton("Eliminar Estudiante");
        JButton btnMostrar = new JButton("Mostrar todos");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrar);

        panelSuperior.add(panelBotones);
        add(panelSuperior, BorderLayout.NORTH);

        btnAgregar.addActionListener(e -> {
            if (!validarEntradaNumerica(txtIdInput.getText(), "ID inválido: solo enteros positivos")) {
                return;
            }
            if (!validarEntradaTexto(txtNombreInput.getText(), "Nombre inválido: solo texto")) {
                return;
            }
            if (!validarEntradaNumerica(txtEdadInput.getText(), "Edad inválida: solo enteros positivos")) {
                return;
            }
            txtId = Integer.parseInt(txtIdInput.getText().trim());
            txtNombre = txtNombreInput.getText().trim();
            txtEdad = Integer.parseInt(txtEdadInput.getText().trim());
            clickAgregar();
        });

        btnActualizar.addActionListener(e -> {
            if (!validarEntradaNumerica(txtIdInput.getText(), "ID inválido: solo enteros positivos")) {
                return;
            }
            if (!validarEntradaTexto(txtNombreInput.getText(), "Nombre inválido: solo texto")) {
                return;
            }
            if (!validarEntradaNumerica(txtEdadInput.getText(), "Edad inválida: solo enteros positivos")) {
                return;
            }
            txtId = Integer.parseInt(txtIdInput.getText().trim());
            txtNombre = txtNombreInput.getText().trim();
            txtEdad = Integer.parseInt(txtEdadInput.getText().trim());
            clickActualizar();
        });

        btnEliminar.addActionListener(e -> {
            if (!validarEntradaNumerica(txtIdInput.getText(), "ID inválido: solo enteros positivos")) {
                return;
            }
            txtId = Integer.parseInt(txtIdInput.getText().trim());
            clickEliminar();
        });

        btnMostrar.addActionListener(e -> clickMostrarTodo());
    }

    public void clickAgregar() {
        Resultado r = control.agregarEstudiante(txtId, txtNombre, txtEdad);
        if (r.isExito()) {
            limpiarCampos();
            clickMostrarTodo();
            return;
        }

        mostrarMensaje(r.getMensaje());
    }

    public void clickActualizar() {
        Resultado r = control.actualizarEstudiante(txtId, txtNombre, txtEdad);
        if (r.isExito()) {
            limpiarCampos();
            clickMostrarTodo();
            return;
        }

        mostrarMensaje(r.getMensaje());
    }

    public void clickEliminar() {
        Resultado r = control.eliminarEstudiante(txtId);
        if (r.isExito()) {
            limpiarCampos();
            clickMostrarTodo();
            return;
        }

        mostrarMensaje(r.getMensaje());
    }

    public void clickMostrarTodo() {
        List<Estudiante> lista = control.mostrarTodos();
        mostrarTabla(lista);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void limpiarCampos() {
        txtIdInput.setText("");
        txtNombreInput.setText("");
        txtEdadInput.setText("");
    }

    public void mostrarTabla(List<Estudiante> estudiantes) {
        JTable tabla = encontrarTabla();
        if (tabla == null) {
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        for (Estudiante e : estudiantes) {
            modelo.addRow(new Object[]{e.getId(), e.getNombre(), e.getEdad()});
        }
    }

    private JTable encontrarTabla() {
        Deque<Component> pendientes = new ArrayDeque<>();
        pendientes.add(getContentPane());

        while (!pendientes.isEmpty()) {
            Component componente = pendientes.removeFirst();
            if (componente instanceof JTable) {
                return (JTable) componente;
            }
            if (componente instanceof Container) {
                for (Component hijo : ((Container) componente).getComponents()) {
                    pendientes.addLast(hijo);
                }
            }
        }

        return null;
    }

    private boolean validarEntradaNumerica(String texto, String mensajeError) {
        String limpio = texto == null ? "" : texto.trim();
        if (!limpio.matches("\\d+")) {
            mostrarMensaje(mensajeError);
            return false;
        }

        if (Integer.parseInt(limpio) <= 0) {
            mostrarMensaje(mensajeError);
            return false;
        }

        return true;
    }

    private boolean validarEntradaTexto(String texto, String mensajeError) {
        String limpio = texto == null ? "" : texto.trim();
        if (limpio.isEmpty()) {
            mostrarMensaje(mensajeError);
            return false;
        }

        if (!limpio.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+")) {
            mostrarMensaje(mensajeError);
            return false;
        }

        return true;
    }
}