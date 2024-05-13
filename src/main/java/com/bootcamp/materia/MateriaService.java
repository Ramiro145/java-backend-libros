package com.bootcamp.materia;

import com.bootcamp.estudiante.Estudiante;
import com.bootcamp.estudiante.Nombre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
@Service
public class MateriaService {

    private MateriaRepository materiaRepository;

    @Autowired
    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    @Transactional(readOnly = true)
    public Page<Materia> findAllMaterias(Pageable pageable) {
        //logica

        return materiaRepository.findAll(pageable);
    }


    public Long createMateria(Materia materia) {
        //check si la materia ya existe
        boolean materiaExiste = materiaRepository.existsByNombre(materia.getNombre());

        if(materiaExiste){
            throw new IllegalArgumentException("materia " + materia.getNombre() + " ya esta registrado");
        }

        return materiaRepository.save(materia).getId();
    }


    public Materia updateMateria(Long id, Materia materiaActualizada) {
        //check si materia con ese id existe, si no botamos un error
        Materia materiaExistente = materiaRepository.
                findById(id).orElseThrow(()-> new NoSuchElementException("Materia con ese id no existe, id: " + id));


        //check si la materia que se quiere actualizar ya existe
        boolean materiaExiste = materiaRepository.existsByNombreAndIdIsNot(materiaActualizada.getNombre(), materiaExistente.getId());

        if(materiaExiste){
            throw new IllegalArgumentException("materia " + materiaActualizada.getNombre() + " ya esta registrado");
        }

        // Actualizar materia
        materiaExistente.setNombre(materiaActualizada.getNombre());
        materiaExistente.setCreditos(materiaActualizada.getCreditos());

        return materiaExistente;
    }

    public void deleteMateria(Long id) {
        //llamar meotodo repositorio y tirar excepcion si no existe

        boolean materiaExiste = materiaRepository.existsById(id);

        if (!materiaExiste){
            throw new NoSuchElementException("materia con id " + id + " no existe");
        }

        materiaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Materia getMateria(Long id) {
        Optional<Materia> materiaOptional = materiaRepository.findById(id);
        if (materiaOptional.isEmpty()){
            throw new NoSuchElementException("Materia con ese id " + id + " no existe");
        }

        return materiaOptional.get();
        //check si no existe tira excepcion NoSuchElement
//        return estudianteRepository.findById(id).orElseThrow(()->new NoSuchElementException("Estudiante con id: " + id + " no existe"));
    }
}
