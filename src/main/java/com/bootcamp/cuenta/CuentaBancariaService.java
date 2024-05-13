package com.bootcamp.cuenta;


import com.bootcamp.materia.Materia;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
@Service
public class CuentaBancariaService {

    private CuentaBancariaRepository cuentaBancariaRepository;

    public CuentaBancariaService(CuentaBancariaRepository cuentaBancariaRepository) {
        this.cuentaBancariaRepository = cuentaBancariaRepository;
    }

    @Transactional(readOnly = true)
    public List<CuentaBancaria> getAllCuentas() {
        return cuentaBancariaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CuentaBancaria getCuentaBancaria(Long id) {
        Optional<CuentaBancaria> cuentaBancariaOptional = cuentaBancariaRepository.findById(id);
        if (cuentaBancariaOptional.isEmpty()){
            throw new NoSuchElementException("Cuenta Bancaria con ese id " + id + " no existe");
        }

        return cuentaBancariaOptional.get();
        //check si no existe tira excepcion NoSuchElement
//        return estudianteRepository.findById(id).orElseThrow(()->new NoSuchElementException("Estudiante con id: " + id + " no existe"));
    }


    public Long createCuentaBancaria(CuentaBancaria cuentaBancaria) {
        //check si la cuenta ya existe
        boolean cuentaBancariaExiste = cuentaBancariaRepository.existsByNumeroCuenta(cuentaBancaria.getNumeroCuenta());

        if(cuentaBancariaExiste){
            throw new IllegalArgumentException("Cuenta Bancaria " + cuentaBancaria.getNumeroCuenta() + " ya esta registrado");
        }

        return cuentaBancariaRepository.save(cuentaBancaria).getId();
    }


    public CuentaBancaria updateCuentaBancaria(Long id, CuentaBancaria cuentaBancariaActualizada) {
        //check si cuenta con ese id existe, si no botamos un error
        CuentaBancaria cuentaBancariaExistente = cuentaBancariaRepository.
                findById(id).orElseThrow(()-> new NoSuchElementException("Cuenta con ese id no existe, id: " + id));


        //check si la cuenta que se quiere actualizar ya existe
        boolean cuentaBancariaExiste = cuentaBancariaRepository.existsByNumeroCuentaAndIdIsNot(cuentaBancariaActualizada.getNumeroCuenta(), cuentaBancariaExistente.getId());

        if(cuentaBancariaExiste){
            throw new IllegalArgumentException("Cuenta " + cuentaBancariaActualizada.getNumeroCuenta() + " ya esta registrado");
        }

        // Actualizar cuenta
        cuentaBancariaExistente.setNumeroCuenta(cuentaBancariaActualizada.getNumeroCuenta());
        cuentaBancariaExistente.setBanco(cuentaBancariaActualizada.getBanco());
        cuentaBancariaExistente.setTitular(cuentaBancariaActualizada.getTitular());

        return cuentaBancariaExistente;
    }


    public void deleteCuentaBancaria(Long id) {
        //llamar meotodo repositorio y tirar excepcion si no existe

        boolean cuentaBancariaExiste = cuentaBancariaRepository.existsById(id);

        if (!cuentaBancariaExiste){
            throw new NoSuchElementException("cuenta con id " + id + " no existe");
        }

        cuentaBancariaRepository.deleteById(id);
    }
}
