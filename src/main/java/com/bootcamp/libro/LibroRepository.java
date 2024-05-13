package com.bootcamp.libro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByTitulo(String titulo);
    boolean existsById(Long id);

    boolean existsByTituloAndIdIsNot(String titulo, Long id);

    boolean existsByTituloAndAutor(String titulo, String autor);

    boolean existsByTituloAndAutorAndIdIsNot(String titulo, String autor, Long id);
}
