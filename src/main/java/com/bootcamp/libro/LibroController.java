package com.bootcamp.libro;


import com.bootcamp.cuenta.CuentaBancaria;
import com.bootcamp.estudiante.Estudiante;
import com.bootcamp.materia.Materia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/libros")
@RestController
@PreAuthorize("hasAnyRole('BIBL', 'ADMIN')")
public class LibroController {

    private LibroService libroService;

    @Autowired
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('BIBL','COOR', 'ADMIN')")
    public List<Libro> getLibros(){
        return libroService.getLibros();
    }

//    @GetMapping
//    @PreAuthorize("hasAnyRole('BIBL','COOR', 'ADMIN')")
//    public Page<Libro> getAllLibros (@PageableDefault(size = 5, page = 0) Pageable pageable){
//        //parametros implicitos - size = tamaño de pagina
//        //page = indice de la pagina
//        //sort = orden sobre alguno de los atributos, podemos agregar direccion "asc", "desc"
//        return libroService.findAllLibros(pageable);
//    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('BIBL','COOR', 'ADMIN')")
    public Libro getLibro(@PathVariable Long id){
        return  libroService.getLibro(id);
    }

    @PostMapping
    public ResponseEntity<Long> createLibro(@RequestBody Libro libro){
        Long idLibro = libroService.createLibro(libro);
        return new ResponseEntity<>(idLibro, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable("id") Long id){
        libroService.deleteLibro(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public Libro updateLibro(@PathVariable("id") Long id, @RequestBody Libro libroActualizado){
        return libroService.updateLibro(id, libroActualizado);
    }


}
