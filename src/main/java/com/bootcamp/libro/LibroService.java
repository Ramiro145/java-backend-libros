package com.bootcamp.libro;

import com.bootcamp.estudiante.Estudiante;
import com.bootcamp.estudiante.Nombre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
@Service
public class LibroService {

    private LibroRepository libroRepository;

    @Autowired
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public Page<Libro> findAllLibros(Pageable pageable) {
        return libroRepository.findAll(pageable);
        //logica de negocio
    }


    public Libro getLibro(Long id) {
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (libroOptional.isEmpty()){
            throw new NoSuchElementException("Libro con ese id:  " + id + ", no existe");
        }

        return libroOptional.get();
        //check si no existe tira excepcion NoSuchElement
//        return estudianteRepository.findById(id).orElseThrow(()->new NoSuchElementException("Estudiante con id: " + id + " no existe"));
    }


    public Long createLibro(Libro libro) {

        //check si el libro ya existe
        boolean libroExiste = libroRepository.existsByTituloAndAutor(libro.getTitulo(), libro.getAutor());
        if(libroExiste){
            throw new IllegalArgumentException("Libro " + libro.getTitulo() + "con autor" + libro.getAutor() + " ya esta registrado");
        }

        Libro libroGuardado =  libroRepository.save(libro);
        return libroGuardado.getId();
    }


    public void deleteLibro(Long id) {
        //llamar meotodo repositorio y tirar excepcion si no existe

        boolean libroExiste = libroRepository.existsById(id);

        if (!libroExiste){
            throw new NoSuchElementException("libro con id " + id + " no existe");
        }

        libroRepository.deleteById(id);
    }


    public Libro updateLibro(Long id, Libro libroActualizado) {

        //check si libro con ese id existe, si no botamos un error

        Libro libroExistente = libroRepository.
                findById(id).orElseThrow(()-> new NoSuchElementException("Libro con ese id no existe, id: " + id));


        //check si el libro que se quiere actualizar ya existe
        boolean libroExiste = libroRepository.existsByTituloAndAutorAndIdIsNot(libroActualizado.getTitulo(), libroActualizado.getAutor(), libroExistente.getId());
        if(libroExiste){
            throw new IllegalArgumentException("libro " + libroActualizado.getTitulo() + "con autor" + libroActualizado.getAutor() +  " ya esta registrado");
        }

        // Actualizar libro
        libroExistente.setAutor(libroActualizado.getAutor());
        libroExistente.setTitulo(libroActualizado.getTitulo());

        return libroExistente;
    }

    public List<Libro> getLibros() {
        return libroRepository.findAll();
    }
}
