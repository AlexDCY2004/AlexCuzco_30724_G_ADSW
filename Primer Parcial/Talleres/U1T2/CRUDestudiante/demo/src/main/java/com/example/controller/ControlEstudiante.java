package com.example.controller;

import com.example.model.Estudiante;
import com.example.model.RepositorioEstudiante;
import com.example.model.Resultado;

import java.util.List;

public class ControlEstudiante {

    private RepositorioEstudiante repo = new RepositorioEstudiante();

    public Resultado agregarEstudiante(int id, String nombre, int edad) {
        if (!validarDatos(id, nombre, edad)) {
            return new Resultado(false, "Datos inválidos: ID numérico, nombre solo texto y edad mayor a 0");
        }

        if (repo.existeId(id)) {
            return new Resultado(false, "El ID ya existe");
        }

        Estudiante estudiante = new Estudiante(id, nombre, edad);
        repo.guardar(estudiante);

        return new Resultado(true, "Estudiante agregado");
    }

    public Resultado actualizarEstudiante(int id, String nombre, int edad) {
        if (!validarDatos(id, nombre, edad)) {
            return new Resultado(false, "Datos inválidos: ID numérico, nombre solo texto y edad mayor a 0");
        }

        Estudiante existente = repo.buscarPorId(id);
        if (existente == null) {
            return new Resultado(false, "Estudiante no encontrado");
        }

        Estudiante actualizado = new Estudiante(id, nombre, edad);
        repo.actualizar(actualizado);

        return new Resultado(true, "Estudiante actualizado");
    }

    public Resultado eliminarEstudiante(int id) {
        if (!repo.existeId(id)) {
            return new Resultado(false, "Estudiante no encontrado");
        }

        repo.eliminar(id);
        return new Resultado(true, "Estudiante eliminado");
    }

    public List<Estudiante> mostrarTodos() {
        return repo.listarTodos();
    }

    public boolean validarDatos(int id, String nombre, int edad) {
        if (id <= 0) return false;
        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (!nombre.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+")) return false;
        if (edad <= 0) return false;
        return true;
    }
}
