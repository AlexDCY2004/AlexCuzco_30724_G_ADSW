package com.example;

import com.example.view.FormularioCrudEstudiante;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FormularioCrudEstudiante formulario = new FormularioCrudEstudiante();
            formulario.setVisible(true);
        });
    }
}