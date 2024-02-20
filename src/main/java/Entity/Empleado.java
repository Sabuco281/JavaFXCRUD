package Entity;

import jakarta.persistence.*;
import jakarta.persistence.Query;
import java.util.Date;

@Entity
@Table(name="trabajadores")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Long id;

    private String nombre;
    private String apellido;
    private String dni;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoria_id")
    private CategoriaUsuario categoria;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;

    public Especialidad getEspecialidad(){
        return especialidad;
    }
    public void setEspecialidad(Especialidad especialidad){
        this.especialidad = especialidad;
    }

    public CategoriaUsuario getCategoria(){
        return categoria;
    }
    public void setCategoria(CategoriaUsuario categoria){
        this.categoria = categoria;
    }

    public Empleado(){

    }
    public Empleado(String nombre, String apellido, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    public String getRol() {
        CategoriaUsuario categoria = getCategoria();
        return categoria != null ? categoria.getRol() : "";
    }

    public String getSueldo() {
        CategoriaUsuario categoria = getCategoria();
        return categoria != null ? categoria.getSueldo() : "";
    }

    public String getPuesto() {
        Especialidad especialidad = getEspecialidad();
        return especialidad != null ? especialidad.getPuesto() : "";
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
