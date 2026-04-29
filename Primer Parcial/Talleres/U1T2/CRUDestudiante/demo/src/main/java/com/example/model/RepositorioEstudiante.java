package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class RepositorioEstudiante {

    private List<Estudiante> estudiantes = new ArrayList<>();

    public boolean existeId(int id) {
        for (Estudiante e : estudiantes) {
            if (e.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void guardar(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    public Estudiante buscarPorId(int id) {
        for (Estudiante e : estudiantes) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public void actualizar(Estudiante estudiante) {
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getId() == estudiante.getId()) {
                estudiantes.set(i, estudiante);
                return;
            }
        }
    }

    public void eliminar(int id) {
        estudiantes.removeIf(e -> e.getId() == id);
    }

    public List<Estudiante> listarTodos() {
        return estudiantes;
    }
}