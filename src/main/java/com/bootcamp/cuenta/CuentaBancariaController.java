package com.bootcamp.cuenta;

import com.bootcamp.materia.Materia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cuentas")
@PreAuthorize("hasRole('ADMIN')")
public class CuentaBancariaController {

    private CuentaBancariaService cuentaBancariaService;

    @Autowired
    public CuentaBancariaController(CuentaBancariaService cuentaBancariaService) {
        this.cuentaBancariaService = cuentaBancariaService;
    }

    @GetMapping
    public List<CuentaBancaria> getAllCuentas(){
        return cuentaBancariaService.getAllCuentas();
    }

    @GetMapping("{id}")
    public CuentaBancaria getCuenta(@PathVariable Long id){
        return cuentaBancariaService.getCuentaBancaria(id);
    }

    @PostMapping
    public ResponseEntity<Long> createCuentaBancaria(@RequestBody CuentaBancaria cuentaBancaria){
        Long cuentaBancariaId = cuentaBancariaService.createCuentaBancaria(cuentaBancaria);
        return new ResponseEntity<>(cuentaBancariaId, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public CuentaBancaria updateCuentaBancaria(@PathVariable("id") Long id, @RequestBody CuentaBancaria cuentaBancariaActualizada){
        return cuentaBancariaService.updateCuentaBancaria(id, cuentaBancariaActualizada);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCuentaBancaria(@PathVariable("id") Long id){
        cuentaBancariaService.deleteCuentaBancaria(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
