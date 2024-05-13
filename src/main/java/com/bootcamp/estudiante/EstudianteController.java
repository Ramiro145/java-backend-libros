package com.bootcamp.estudiante;

import com.bootcamp.libro.Libro;
import com.bootcamp.materia.Materia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/estudiantes")
@RestController
@PreAuthorize("hasAnyRole('COOR', 'ADMIN')")
public class EstudianteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstudianteController.class);


    //spring se encarga de la inyeccion de dependencias
//    @Autowired
    private EstudianteService estudianteService;

    //mejor manera de usar Autowired, funciona asi no se tenga Autowired
    @Autowired
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('BIBL','COOR', 'ADMIN')")
    public List<Estudiante> getEstudiante (
            @RequestParam(value = "primerNombre", required = false)String primerNombre,
            @RequestParam(value = "primerApellido", required = false)String primerApellido
    ){
        if(primerNombre !=  null || primerApellido != null ){
           return  estudianteService.getEstudianteByPrimerNombreOrPrimerApellido(primerNombre, primerApellido);
        }

        return estudianteService.getAllEstudiante();
    }

    // este metodo podria remplazar el que nos retribuye la lista de estudiantes, es paginado
//    @GetMapping("/paged")
//    @PreAuthorize("hasAnyRole('BIBL','COOR', 'ADMIN')")
//    public Page<Estudiante> getEstudiante (@PageableDefault(size = 3, page = 0)Pageable pageable){
//        //parametros implicitos - size = tamaño de pagina
//        //page = indice de la pagina
//        //sort = orden sobre alguno de los atributos, podemos agregar direccion "asc", "desc"
//        return estudianteService.findAllEstudiantes(pageable);
//    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('BIBL','COOR', 'ADMIN')")
    public Estudiante getOneEstudiante(@PathVariable Long id){
        return  estudianteService.getOneEstudiante(id);
    }

    @PostMapping
    public ResponseEntity<Long> createEstudiante(@RequestBody Estudiante e){
        Long idEstudiante = estudianteService.createEstudiante(e);
        return new ResponseEntity<>(idEstudiante, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEstudiante(@PathVariable("id") Long id){
        estudianteService.deleteEstudiante(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public Estudiante updateEstudiante(@PathVariable("id") Long id, @RequestBody Estudiante estudianteActualizado){
        return estudianteService.updateEstudiante(id, estudianteActualizado);
    }

    //no se crea ningun recurso por ello se usa PUT
    @PutMapping("{estudianteId}/libros/{libroId}")
    @PreAuthorize("hasAnyRole('BIBL', 'ADMIN')")
    public Estudiante agregarLibroAEstudiante(@PathVariable Long estudianteId, @PathVariable Long libroId){
        return estudianteService.agregarLibroAEstudiante(estudianteId, libroId);
    }

    @PutMapping("{estudianteId}/materias/{materiaId}")
    @PreAuthorize("hasAnyRole('COOR', 'ADMIN')")
    public Estudiante agregarMateriaAEstudiante(@PathVariable Long estudianteId, @PathVariable Long materiaId){
        return estudianteService.agregarMateriaAEstudiante(estudianteId, materiaId);
    }

    @PutMapping("{estudianteId}/cuentas/{cuentaId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Estudiante agregarCuentaAEstudiante(@PathVariable Long estudianteId, @PathVariable Long cuentaId){
        return estudianteService.agregarCuentaAEstudiante(estudianteId, cuentaId);
    }

    @PreAuthorize("hasAnyRole('BIBL', 'ADMIN')")
    @PutMapping("{estudianteId}/libros/{libroId}/delete")
    public Estudiante quitarLibroAEstudiante(@PathVariable Long estudianteId, @PathVariable Long libroId){
        return estudianteService.quitarLibroAEstudiante(estudianteId, libroId);
    }

}
