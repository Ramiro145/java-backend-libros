package com.bootcamp.estudiante;

import com.bootcamp.cuenta.CuentaBancaria;
import com.bootcamp.cuenta.CuentaBancariaRepository;
import com.bootcamp.libro.Libro;
import com.bootcamp.libro.LibroRepository;
import com.bootcamp.materia.Materia;
import com.bootcamp.materia.MateriaRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

@Transactional
@Service
public class EstudianteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstudianteService.class);
    private EstudianteRepository estudianteRepository;
    private final LibroRepository libroRepository;
    private final MateriaRepository materiaRepository;
    private final CuentaBancariaRepository cuentaBancariaRepository;

    @Autowired
    public EstudianteService(EstudianteRepository estudianteRepository,
                             LibroRepository libroRepository,
                             MateriaRepository materiaRepository,
                             CuentaBancariaRepository cuentaBancariaRepository) {
        this.estudianteRepository = estudianteRepository;
        this.libroRepository = libroRepository;
        this.materiaRepository = materiaRepository;
        this.cuentaBancariaRepository = cuentaBancariaRepository;
    }


    public List<Estudiante> getAllEstudiante(){
        List<Estudiante> estudiantes = estudianteRepository.findAll();

        //logica de negocio

        return estudiantes;
    }

//    public Page<Estudiante> findAllEstudiantes(Pageable pageable){
//       return estudianteRepository.findAll(pageable);
//        //logica de negocio
//    }

    
    public List<Estudiante> getEstudianteByPrimerNombreOrPrimerApellido(String primerNombre, String primerApellido){
        List<Estudiante> estudiantes = estudianteRepository.findEstudianteByPrimerNombreOrPrimerApellido(primerNombre,primerApellido);

        //logica de negocio

        return estudiantes;
    }


    public Long createEstudiante(Estudiante e){
        LOGGER.info("creando estudiante {}", e);
        //check si el email es valido
        if(!checkValidezEmail(e.getEmail())){
            LOGGER.warn("Email {} no es valido", e.getEmail());
            throw new IllegalArgumentException("Email " + e.getEmail() + " no es valido");
        }

        //check si el email ya existe
        boolean emailExiste = estudianteRepository.existsByEmail(e.getEmail());
        if(emailExiste){
            LOGGER.warn("Email {} ya esta registrado", e.getEmail());
            throw new IllegalArgumentException("email " + e.getEmail() + " ya esta registrado");
        }

        Estudiante estudianteGuardado =  estudianteRepository.save(e);
        LOGGER.info("Estudiante con id {} fue guardado exitosamente", estudianteGuardado.getId());
        return estudianteGuardado.getId();
    }

    public void deleteEstudiante(Long id) {
        //llamar meotodo repositorio y tirar excepcion si no existe

        boolean estudianteExiste = estudianteRepository.existsById(id);

        if (!estudianteExiste){
            throw new NoSuchElementException("estudiante con id " + id + " no existe");
        }

        estudianteRepository.deleteById(id);
    }

    public Estudiante updateEstudiante(Long id, Estudiante estudianteActualizado) {

        //check si estuidante con ese id existe, si no botamos un error
        Estudiante estudianteExistente = estudianteRepository.
                findById(id).orElseThrow(()-> new NoSuchElementException("Estudiante con ese id no existe, id: " + id));

        //check si el email es valido
        if(!checkValidezEmail(estudianteActualizado.getEmail())){
            throw new IllegalArgumentException("Email " + estudianteActualizado.getEmail() + " no es valido");
        }

        //check si el email que se quiere actualizar ya existe
           boolean emailExiste = estudianteRepository.existsByEmailAndIdIsNot(estudianteActualizado.getEmail(), estudianteExistente.getId());
           if(emailExiste){
               throw new IllegalArgumentException("email " + estudianteActualizado.getEmail() + " ya esta registrado");
           }

        Nombre nombre = new Nombre();
        // Actualizar estudiante
        nombre.setPrimerNombre(estudianteActualizado.getNombre().getPrimerNombre());
        nombre.setSegundoNombre(estudianteActualizado.getNombre().getSegundoNombre());
        nombre.setPrimerApellido(estudianteActualizado.getNombre().getPrimerApellido());
        nombre.setSegundoApellido(estudianteActualizado.getNombre().getSegundoApellido());
        estudianteExistente.setNombre(nombre);
        estudianteExistente.setFechaNacimiento(estudianteActualizado.getFechaNacimiento());
        estudianteExistente.setEmail(estudianteActualizado.getEmail());


        return estudianteExistente;
    }

    public Estudiante getOneEstudiante(Long id) {
        Optional<Estudiante> estudianteOptional = estudianteRepository.findById(id);
        if (estudianteOptional.isEmpty()){
            throw new NoSuchElementException("Estudiante con ese id " + id + " no existe");
        }

        return estudianteOptional.get();
        //check si no existe tira excepcion NoSuchElement
//        return estudianteRepository.findById(id).orElseThrow(()->new NoSuchElementException("Estudiante con id: " + id + " no existe"));
    }

    private boolean checkValidezEmail (String email){
        return Pattern.compile(
                "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE
        ).asPredicate().test(email);
    }

    public Estudiante agregarLibroAEstudiante(Long estudianteId, Long libroId) {
        //logica
        //check si estuidante con ese id existe, si no botamos un error
        Estudiante estudianteExistente = estudianteRepository.
                findById(estudianteId).orElseThrow(()-> new NoSuchElementException("Estudiante con ese id no existe, id: " + estudianteId));

        //check si libro con ese id existe, si no botamos un error
        Libro libroExistente = libroRepository.
                findById(libroId).orElseThrow(()-> new NoSuchElementException("Libro con ese id no existe, id: " + libroId));

//        libroExistente.setEstudiante(estudianteExistente);
        //se puede realizar de esta forma tambien
        estudianteExistente.addLibro(libroExistente);
        return estudianteExistente;
    }

    public Estudiante agregarMateriaAEstudiante(Long estudianteId, Long materiaId) {
        //logica
        //check si estuidante con ese id existe, si no botamos un error
        Estudiante estudianteExistente = estudianteRepository.
                findById(estudianteId).orElseThrow(()-> new NoSuchElementException("Estudiante con ese id no existe, id: " + estudianteId));

        //check si materia con ese id existe, si no botamos un error
        Materia materiaExistente = materiaRepository.
                findById(materiaId).orElseThrow(()-> new NoSuchElementException("Materia con ese id no existe, id: " + materiaId));

        estudianteExistente.addMateria(materiaExistente);
        return estudianteExistente;
    }


    public Estudiante agregarCuentaAEstudiante(Long estudianteId, Long cuentaId) {
        //logica
        //check si estuidante con ese id existe, si no botamos un error
        Estudiante estudianteExistente = estudianteRepository.
                findById(estudianteId).orElseThrow(()-> new NoSuchElementException("Estudiante con ese id no existe, id: " + estudianteId));

        //check si cuenta con ese id existe, si no botamos un error
        CuentaBancaria cuentaExistente = cuentaBancariaRepository.
                findById(cuentaId).orElseThrow(()-> new NoSuchElementException("Cuenta con ese id no existe, id: " + cuentaId));


        estudianteExistente.setCuenta(cuentaExistente);

        return estudianteExistente;
    }

    public Estudiante quitarLibroAEstudiante(Long estudianteId, Long libroId) {
        //logica
        //check si estuidante con ese id existe, si no botamos un error
        Estudiante estudianteExistente = estudianteRepository.
                findById(estudianteId).orElseThrow(()-> new NoSuchElementException("Estudiante con ese id no existe, id: " + estudianteId));

        //check si libro con ese id existe, si no botamos un error
        Libro libroExistente = libroRepository.
                findById(libroId).orElseThrow(()-> new NoSuchElementException("Libro con ese id no existe, id: " + libroId));

        libroExistente.setEstudiante(null);
        //se puede realizar de esta forma tambien
        //estudianteExistente.addLibro(libroExistente);
        return estudianteExistente;
    }
}
