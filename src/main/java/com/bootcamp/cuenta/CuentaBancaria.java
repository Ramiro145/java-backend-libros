package com.bootcamp.cuenta;

import com.bootcamp.estudiante.Estudiante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class CuentaBancaria {

    @Id
    @Column(name = "id_cuenta")
    @SequenceGenerator(
            sequenceName = "sequence_cuenta",
            name = "sequence_cuenta"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_cuenta"
    )
    private Long id;

    @Column(name = "numero_cuenta")
    private Long numeroCuenta;

    @Column(name = "banco")
    private String Banco;

    @Column(name = "titular")
    private String titular;

    //evita la recursividad en el objeto
    @JsonIgnore
    //asocia la relacion que se tienen entre esta entidad y con la que esta relacionada mediante el nombre definido de la variable en la entidad estudiante para el
    //objeto cuenta
    @OneToOne(mappedBy = "cuenta")
    private Estudiante estudiante;

    public CuentaBancaria() {
    }

    public CuentaBancaria(Long id, Long numeroCuenta, String banco, String titular) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        Banco = banco;
        this.titular = titular;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getBanco() {
        return Banco;
    }

    public void setBanco(String banco) {
        Banco = banco;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuentaBancaria that = (CuentaBancaria) o;
        return Objects.equals(id, that.id) && Objects.equals(numeroCuenta, that.numeroCuenta) && Objects.equals(Banco, that.Banco) && Objects.equals(titular, that.titular);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroCuenta, Banco, titular);
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "id=" + id +
                ", numeroCuenta=" + numeroCuenta +
                ", Banco='" + Banco + '\'' +
                ", titular='" + titular + '\'' +
                '}';
    }
}
