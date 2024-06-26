package com.bootcamp.cuenta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria,Long> {
    boolean existsByNumeroCuenta(Long numeroCuenta);
    boolean existsByNumeroCuentaAndIdIsNot(Long numeroCuenta, Long id);
    boolean existsById(Long id);
}
