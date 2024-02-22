# Gestion de Usuario

Este software proporciona una interfaz intuitiva para la gestión de empleados en una empresa. Sus principales funciones incluyen el registro de empleados, la descarga de tablas con información detallada, y la capacidad de modificar o eliminar registros. También, se centra en la especialización y el rol que cada empleado desempeña dentro de la empresa.

## Requisitos previos

- Java Development kit(JDK) 11 o superior preferible Java 11 para evitar problemas.
- Maven instalado y configurado correctamente.
- Tener XAMPP para instalar el servidor localmente
- Versión de la base de datos: 10.4.28-MariaDB 
- 

### Requisitos del JDK

Asegúrate de tener instalado un JDK de Java en la versión 11, ya que la funcionalidad del proyecto está respaldada en esta versión.

### Base de Datos Configurada

La base de datos debe estar configurada y lista, ya que es un requisito fundamental para el correcto funcionamiento del software de gestión de empleados.
sino nos dara un aviso de error.

### Actualización de Java

Se recomienda tener Java en el entorno del usuario actualizado para evitar problemas al ejecutar el archivo JAR. Si experimentas problemas a través del intérprete de comandos o CMD, apunta al directorio donde se encuentra tu archivo JAR y llámalo directamente desde el JDK de Java 11.

### Comandos de Ejecución

```bash
:: Ejemplo de ejecución en un sistema Unix (Linux o macOS)
$ /ruta/a/tu/jdk/bin/java -jar /ruta/a/tu/proyecto/JavaF.jar

cmd

:: Ejemplo de ejecución en un sistema Windows
C:\ruta\a\tu\jdk\bin\java.exe -jar C:\ruta\a\tu\proyecto\JavaF.jar
```
### Configuración de Hibernate

El archivo `hibernate.cfg.xml` proporciona la configuración para Hibernate, la herramienta de mapeo objeto-relacional.Asegúrate de que esté configurado correctamente, especialmente el puerto al que estás haciendo la conexión (en este caso, localhost:3306) y el nombre de la base de datos (en este caso, "gestionusuarios"). Si no realizas esta verificación, es posible que no puedas visualizar la base de datos y que se genere un error.
<property name="connection.url">jdbc:mysql://localhost:3306/gestionusuarios</property>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<hibernate-configuration>
    <session-factory>
        <!-- ... otras propiedades ... -->
        <property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://localhost:3306/gestionusuarios</property>
        <property name="dialect">org.hibernate.dialect.MySQLDialect</property>
        <property name="connection.username">root</property>
        <property name="connection.password"></property>
        <property name="show_sql">true</property>
        <property name="hibernate.hbm2ddl.auto">update</property>
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <mapping class="Entity.Empleado"></mapping>
        <mapping class="Entity.CategoriaUsuario"></mapping>
        <mapping class="Entity.Especialidad"></mapping>
    </session-factory>
</hibernate-configuration>
```
### Explicación de la Intefaz

-En la interfaz, encontrarás cuatro botones, cada uno con una funcionalidad específica:

    -Crear Usuarios:
        Permite registrar nuevos usuarios en el sistema.

    -Crear Roles:
        Facilita la creación y asignación de roles a los empleados.

    -Asignar Especialización:
        Permite asignar especialidades a los empleados, lo cual es crucial para definir sus funciones y responsabilidades específicas.

    -Buscar Usuarios:
        Facilita la búsqueda de usuarios registrados en el sistema.

Las funcionalidades de "Crear Roles" y "Asignar Especialización" están diseñadas para gestionar la organización interna de la empresa. En particular:

    -Roles:
        Después de registrar un empleado, puedes asignarle un rol específico que refleje sus responsabilidades y posición en la empresa.

Especialidades:
        Además, podrás asignar especialidades a los empleados para detallar aún más sus habilidades y competencias.
