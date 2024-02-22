# Gestion de Usuario

Este software proporciona una interfaz intuitiva para la gestión de empleados en una empresa. Sus principales funciones incluyen el registro de empleados, la descarga de tablas con información detallada, y la capacidad de modificar o eliminar registros. También, se centra en la especialización y el rol que cada empleado desempeña dentro de la empresa.

## Requisitos previos

- Java Development kit(JDK) 11 o superior preferible Java 11 para evitar problemas.
- Maven instalado y configurado correctamente.
- Tener XAMPP para instalar el servidor localmente, con lo siguentes requisitos
-     Versión de la base de datos: 10.4.28-MariaDB 
-     Apache/2.4.56 (Win64) OpenSSL/1.1.1t PHP/8.2.4
-     Versión del cliente de base de datos: libmysql - mysqlnd 8.2.4
-     phpMyAdmin versión: 5.2.1 (actualizada)

### Manejo de Xampp y Phpmyadmin
Una vez ya instalado el xampp y lo inicies, tendrás que activar el apache y Mysql presionando en los dos botones start, una vez activado, pon en el navegador web localhost o (http://localhost/dashboard/)
le llevara al xampp luego seleccione phpadmin ahí tendrá que poner usuario y contraseña que serán siempre root, ya que es la primera vez que accedemos, y la contraseña no se pone nada, entonces le damos a continuar, y se vera el phpadmin, nos vamos donde ponga importar y ahí importamos el sql de gestionusuarios veremos que hay un botón de examinar se pone ahí y luego nos vamos abajo del todo donde lo importare y se creara la base de datos

```gestionusuarios.sql

-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generaciÃ³n: 20-02-2024 a las 20:20:30
-- VersiÃ³n del servidor: 10.4.28-MariaDB
-- VersiÃ³n de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

create DATABASE IF NOT EXISTS gestionusuarios;
use gestionusuarios;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gestionusuarios`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoriaempleado`
--

CREATE TABLE `categoriaempleado` (
  `id_categoria` int(11) NOT NULL,
  `Rol` varchar(50) NOT NULL,
  `Sueldo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categoriaempleado`
--

INSERT INTO `categoriaempleado` (`id_categoria`, `Rol`, `Sueldo`) VALUES
(41, 'operario de producciÃ³n', '1200'),
(42, 'supervisor de calidad', '1500'),
(43, 'diseÃ±ador de calzado', '1800');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialidad`
--

CREATE TABLE `especialidad` (
  `id_especialidad` int(11) NOT NULL,
  `puesto` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `especialidad`
--

INSERT INTO `especialidad` (`id_especialidad`, `puesto`) VALUES
(22, 'corte de cuero'),
(23, 'ensamblaje'),
(24, 'control de calidad');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajadores`
--

CREATE TABLE `trabajadores` (
  `id_empleado` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `dni` varchar(50) NOT NULL,
  `categoria_id` int(20) DEFAULT NULL,
  `especialidad_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `trabajadores`
--

INSERT INTO `trabajadores` (`id_empleado`, `nombre`, `apellido`, `dni`, `categoria_id`, `especialidad_id`) VALUES
(56, 'juan', 'sabuco', '06267864l', 41, 22),
(57, 'maria ', 'rodriguez', '90539154e', 42, 23),
(58, 'carlos', 'garcia', '62650946g', 43, 23),
(59, 'alberto', 'sabuco', '74390880w', 43, 24);

--
-- Ãndices para tablas volcadas
--

--
-- Indices de la tabla `categoriaempleado`
--
ALTER TABLE `categoriaempleado`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indices de la tabla `especialidad`
--
ALTER TABLE `especialidad`
  ADD PRIMARY KEY (`id_especialidad`);

--
-- Indices de la tabla `trabajadores`
--
ALTER TABLE `trabajadores`
  ADD PRIMARY KEY (`id_empleado`),
  ADD KEY `categoria_id` (`categoria_id`) USING BTREE,
  ADD KEY `especialidad_id` (`especialidad_id`) USING BTREE;

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoriaempleado`
--
ALTER TABLE `categoriaempleado`
  MODIFY `id_categoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT de la tabla `especialidad`
--
ALTER TABLE `especialidad`
  MODIFY `id_especialidad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `trabajadores`
--
ALTER TABLE `trabajadores`
  MODIFY `id_empleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `trabajadores`
--
ALTER TABLE `trabajadores`
  ADD CONSTRAINT `trabajadores_ibfk_1` FOREIGN KEY (`especialidad_id`) REFERENCES `especialidad` (`id_especialidad`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `trabajadores_ibfk_2` FOREIGN KEY (`categoria_id`) REFERENCES `categoriaempleado` (`id_categoria`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
```
### Requisitos del JDK

Asegúrate de tener instalado un JDK de Java en la versión 11, ya que la funcionalidad del proyecto está respaldada en esta versión.

### Base de Datos Configurada

La base de datos debe estar configurada y lista, ya que es un requisito fundamental para el correcto funcionamiento del software de gestión de empleados.
sino nos dara un aviso de error.

### Actualización de Java

Se recomienda tener Java en el entorno del usuario actualizado, para evitar problemas al ejecutar el archivo JAR. Si experimentas problemas, a través del intérprete de comandos o CMD, apunta al directorio donde se encuentra tu archivo JAR y llámalo directamente desde el JDK de Java 11.
###Intellij
Otra cosa importante en este punto es tener el intellij instalado, y cuando lo inicies ir a la parte superior, seleccionar file para después darle a project structure, ahí nos aseguraremos de que instalamos el siguiente sdk (ya que sera la manera correcta de iniciar nuestro proyecto en intellij) tiene que seleccionar java 11 corretto-11 Amazon Coretto version 11.0.22. Al mismo tiempo esto nos serviría ya más adelante de ejecutar nuestro proyecto mediante el interprete de comandos, si tiene problemas de ejecución la aplicación.
Y si da problemas de fallo de librerías, tendría que descargar las librerías de javafx-sdk-17.0.10, y añadirlas, volvemos a project structure, y seleccionar artifacts, si el jar esta, veras un sitio donde pone output layout abajo de esa palabra, le das al botón + y seleccionas file, ahí te vas al archivo javafx-sdk-17.0.10 a la carpeta bin y seleccionas todo lo que esta dentro de la carpeta para que ejecute correctamente su interfaz en JavaFX. al finalizar le das a apply y guardar, vas a la ventana de build y le das a rebuild project.
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

-Alta Usuario:
    Permite registrar nuevos usuarios en el sistema.
    Ofrece funciones adicionales como borrar, modificar y descargar usuarios.
    Proporciona la capacidad de importar información de empleados desde un documento CSV a la base de datos.

-Crear Roles:
        Facilita la creación y asignación de roles a los empleados.
        Y también todo lo especificado en alta usuario.
        

-Asignar Especialización:
        Permite asignar especialidades a los empleados, lo cual es crucial para definir sus funciones y responsabilidades específicas.
        Y también todo lo especificado en alta usuario.
-Buscar Usuarios:
        Facilita la búsqueda de usuarios registrados en el sistema.
        Y cuándo realiza esa busqueda podemos guardala, a un documento csv.

Las funcionalidades de "Crear Roles" y "Asignar Especialización" están diseñadas para gestionar la organización interna de la empresa. En particular:

-Roles:
        Después de registrar un empleado, puedes asignarle un rol específico que refleje sus responsabilidades y posición en la empresa.

-Especialidades:
        Además, podrás asignar especialidades a los empleados para detallar aún más sus habilidades y competencias.
### Nota importante
La interfaz ha sido diseñada para ser intuitiva. Al hacer clic en ciertos botones, la interfaz cambia dinámicamente para mostrar tareas específicas. Por ejemplo, al hacer clic en "Borrar", se presentará de manera clara la lista de pasos a seguir para completar esa tarea específica. Si deseas registrar nuevamente, simplemente pulsa "Salir". Para volver a la interfaz principal, selecciona "Menú Principal". Este enfoque facilita la navegación y la ejecución de tareas de manera fluida.
###Enlace del proyecto en Github.
https://github.com/Sabuco281/JavaFXCRUD
