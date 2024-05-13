package com.bootcamp.estudiante;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class EstudianteRepositoryMentiras {

    public final List<Estudiante> estudiantes;

    public EstudianteRepositoryMentiras() {
        this.estudiantes = new ArrayList<Estudiante>();
//        estudiantes.add(new Estudiante(1L, "Fulano"));
//        estudiantes.add(new Estudiante(2L, "Estudiante 1 - repo"));
//        estudiantes.add(new Estudiante(3L, "Estudiante 2 - repo"));
//        estudiantes.add(new Estudiante(4L, "Estudiante 3 - repo"));

    }

    public List<Estudiante> getEstudiantes(){
        return  estudiantes;
    }

    public void createEstudiante(Estudiante e){
        estudiantes.add(e);
    }

    public void deleteEstudiante(Long id) {
//        for (int i = 0; i < estudiantes.size(); i++){
//            if (estudiantes.get(i).getId().equals(id)){
//                estudiantes.remove(i);
//            }
//        }

        estudiantes.removeIf(e -> e.getId().equals(id));

    }

    public void updateEstudiante(Long id, Estudiante estudianteActualizado) {
//        for (int i = 0; i < estudiantes.size(); i++){
//            Estudiante estudiante = estudiantes.get(i);
//            if (estudiante.getId().equals(id)){
//                estudiante.setId(estudianteActualizado.getId());
//                estudiante.setNombre(estudianteActualizado.getNombre());
//            }
//        }

        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getId().equals(id)) {
                estudiante.setId(estudianteActualizado.getId());
                estudiante.getNombre().setPrimerNombre(estudianteActualizado.getNombre().getPrimerNombre());
            }
        }
    }

    public Estudiante getOneEstudiante(Long id) {
//        for(Estudiante e : estudiantes){
//            if (e.getId().equals(id)){
//                return e;
//            }
//        }
//        throw  new IllegalStateException("no se encontro con ese id");
        return estudiantes.stream().filter(estudiante -> estudiante.getId().equals(id)).findFirst()
                .orElseThrow(()->new IllegalStateException("No se encontro el estudiante con ese id"));
    }
}
