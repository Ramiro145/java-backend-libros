package com.bootcamp.estudiante;

import com.bootcamp.cuenta.CuentaBancaria;
import com.bootcamp.libro.Libro;
import com.bootcamp.materia.Materia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//nuestra clase representa una tabla con esta notacion
@Entity
@Table(name = "estudiante")
public class Estudiante {

    @Id
    @Column(name = "id_estudiante")
    @SequenceGenerator(
            sequenceName = "sequence_estudiante",
            name = "sequence_estudiante"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_estudiante"
    )
    private Long id;

    //de esta manera las columnas estan embedidas en esta entidad
    @Embedded
    private Nombre nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    //lo que involucre a la entidad relacionada se vera guardada tambien en la otra entidad, causara una cascada
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "id_cuenta",
            referencedColumnName = "id_cuenta",
            //cada cuenta bancaria tiene que ser unica por cada estudiante
            unique = true
    )
    private CuentaBancaria cuenta;

    @JsonIgnore
    @OneToMany(mappedBy = "estudiante")
    private List<Libro> libros =  new ArrayList<>();

    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "inscripciones",
            joinColumns = @JoinColumn(
                    name = "estudiante_id",
                    referencedColumnName = "id_estudiante"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "materia_id",
                    referencedColumnName = "id_materia"
            )
    )
    private List<Materia> materias = new ArrayList<>();


    public Estudiante(){

    }

    public Estudiante(Long id, Nombre nombre, LocalDate fechaNacimiento, String email, CuentaBancaria cuenta, List<Libro> libros, List<Materia> materias) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.cuenta = cuenta;
        this.libros = libros;
        this.materias = materias;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void addMateria (Materia materia){
        if(!materias.contains(materia)){
            materias.add(materia);
        }
    }

    public void RemoveMateria(Materia materia){
        materias.remove(materia);
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void addLibro(Libro libro) {
        if(!libros.contains(libro)){
            libros.add(libro);
        }
    }

    public void RemoveLibro(Libro libro){
        libros.remove(libro);
    }

    public Long getCuenta() {
        return cuenta.getId();
    }

    public void setCuenta(CuentaBancaria cuenta) {
        this.cuenta = cuenta;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public Nombre getNombre() {
        return nombre;
    }

    public void setNombre(Nombre nombre) {
        this.nombre = nombre;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estudiante that = (Estudiante) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(fechaNacimiento, that.fechaNacimiento) && Objects.equals(email, that.email) && Objects.equals(cuenta, that.cuenta) && Objects.equals(libros, that.libros) && Objects.equals(materias, that.materias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, fechaNacimiento, email, cuenta, libros, materias);
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "id=" + id +
                ", nombre=" + nombre +
                ", fechaNacimiento=" + fechaNacimiento +
                ", email='" + email + '\'' +
                ", cuenta=" + cuenta +
                ", libros=" + libros +
                ", materias=" + materias +
                '}';
    }
}
