package Entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categoriaempleado")
public class CategoriaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long id;

    private String Rol;
    private String Sueldo;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.PERSIST)
    private List<Empleado> empleados;

    public CategoriaUsuario(String rol, String sueldo) {
        this.Rol = rol;
        this.Sueldo = sueldo;
    }

    public CategoriaUsuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public String getSueldo() {
        return Sueldo;
    }

    public void setSueldo(String sueldo) {
        Sueldo = sueldo;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

}
