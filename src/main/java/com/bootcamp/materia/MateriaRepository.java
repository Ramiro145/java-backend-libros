package com.bootcamp.materia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long>{
    boolean existsById(Long id);
    boolean existsByNombre(String Nombre);
    boolean existsByNombreAndIdIsNot(String Nombre, Long id);
}
