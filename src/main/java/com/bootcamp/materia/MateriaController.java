package com.bootcamp.materia;


import com.bootcamp.estudiante.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/materias")
@RestController
@PreAuthorize("hasAnyRole('COOR', 'ADMIN')")
public class MateriaController {

    private MateriaService materiaService;

    @Autowired
    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping
    public Page<Materia> getAllMaterias(@PageableDefault(size = 2, page = 0)Pageable pageable){
        return materiaService.findAllMaterias(pageable);
    }

    @GetMapping("{id}")
    public Materia getMateria(@PathVariable Long id){
        return materiaService.getMateria(id);
    }

    @PostMapping
    public ResponseEntity<Long> createMateria(@RequestBody Materia materia){
        Long materiaId = materiaService.createMateria(materia);
        return new ResponseEntity<>(materiaId, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public Materia updateMateria(@PathVariable("id") Long id, @RequestBody Materia materiaActualizada){
        return materiaService.updateMateria(id, materiaActualizada);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMateria(@PathVariable("id") Long id){
        materiaService.deleteMateria(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
