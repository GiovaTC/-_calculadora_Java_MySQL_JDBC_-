# -_calculadora_Java_MySQL_JDBC_- :.
# Calculadora Java + MySQL + JDBC:

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/77f48fae-aa1b-4adc-95b5-f6be2f207114" />    

<img width="2556" height="1034" alt="image" src="https://github.com/user-attachments/assets/c99b0f13-f283-4cb7-b1e1-94d0e98ca132" />    

```

Proyecto muy básico desarrollado en **Java 21**, utilizando **Consola**, **JDBC** y **MySQL**, que permite realizar operaciones matemáticas básicas, almacenar cada operación en la base de datos y consultar el historial registrado.

---

# Características

- ✅ Java 21
- ✅ Consola
- ✅ Maven
- ✅ JDBC
- ✅ MySQL 8
- ✅ Solicita dos números
- ✅ Realiza operaciones matemáticas
- ✅ Guarda cada operación en MySQL
- ✅ Consulta el historial de operaciones

---

# Funcionalidades

La aplicación permite realizar las siguientes operaciones:

- ➕ Suma
- ➖ Resta
- ✖ Multiplicación
- ➗ División

Al finalizar cada operación:

- Muestra el resultado en consola.
- Guarda la operación en la base de datos.
- Consulta y muestra el historial completo de operaciones almacenadas.

---

# Estructura del proyecto

```text
CalculadoraJavaMySQL
│
├── pom.xml
├── script.sql
│
└── src
    └── main
        └── java
            └── com
                └── ejemplo
                    └── calculadora
                        │
                        ├── Principal.java
                        ├── Conexion.java
                        ├── Calculadora.java
                        ├── Operacion.java
                        └── OperacionDAO.java
```

---

# Base de datos

## script.sql

```sql
CREATE DATABASE calculadora;

USE calculadora;

CREATE TABLE operaciones(

    id INT AUTO_INCREMENT PRIMARY KEY,

    numero1 DOUBLE,

    numero2 DOUBLE,

    operacion VARCHAR(20),

    resultado DOUBLE

);
```

---

# Archivo Maven

## pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.ejemplo</groupId>

    <artifactId>CalculadoraJavaMySQL</artifactId>

    <version>1.0</version>

    <properties>

        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>

    </properties>

    <dependencies>

        <dependency>

            <groupId>com.mysql</groupId>

            <artifactId>mysql-connector-j</artifactId>

            <version>9.3.0</version>

        </dependency>

    </dependencies>

</project>
```

---

# Clase de conexión

## Conexion.java

```java
package com.ejemplo.calculadora;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3306/calculadora";

    private static final String USER = "root";

    private static final String PASSWORD = "1234";

    public static Connection conectar() {

        try {

            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }

    }

}
```

---

# Modelo

## Operacion.java

```java
package com.ejemplo.calculadora;

public class Operacion {

    private double numero1;
    private double numero2;
    private String operacion;
    private double resultado;

    public Operacion(double numero1,
                     double numero2,
                     String operacion,
                     double resultado) {

        this.numero1 = numero1;
        this.numero2 = numero2;
        this.operacion = operacion;
        this.resultado = resultado;

    }

    public double getNumero1() {
        return numero1;
    }

    public double getNumero2() {
        return numero2;
    }

    public String getOperacion() {
        return operacion;
    }

    public double getResultado() {
        return resultado;
    }

}
```

---

# Lógica de negocio

## Calculadora.java

```java
package com.ejemplo.calculadora;

public class Calculadora {

    public double sumar(double a,double b){

        return a+b;

    }

    public double restar(double a,double b){

        return a-b;

    }

    public double multiplicar(double a,double b){

        return a*b;

    }

    public double dividir(double a,double b){

        return a/b;

    }

}
```

---

# Acceso a datos

## OperacionDAO.java

```java
package com.ejemplo.calculadora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OperacionDAO {

    public void guardar(Operacion op){

        try(Connection con=Conexion.conectar()){

            String sql=
                    "insert into operaciones(numero1,numero2,operacion,resultado) values(?,?,?,?)";

            PreparedStatement ps=con.prepareStatement(sql);

            ps.setDouble(1,op.getNumero1());
            ps.setDouble(2,op.getNumero2());
            ps.setString(3,op.getOperacion());
            ps.setDouble(4,op.getResultado());

            ps.executeUpdate();

        }catch(Exception e){

            e.printStackTrace();

        }

    }

    public void listar(){

        try(Connection con=Conexion.conectar()){

            String sql="select * from operaciones";

            PreparedStatement ps=con.prepareStatement(sql);

            ResultSet rs=ps.executeQuery();

            System.out.println();

            System.out.println("HISTORIAL");

            while(rs.next()){

                System.out.println(

                        rs.getInt("id")+" | "+

                        rs.getDouble("numero1")+" | "+

                        rs.getString("operacion")+" | "+

                        rs.getDouble("numero2")+" = "+

                        rs.getDouble("resultado")

                );

            }

        }catch(Exception e){

            e.printStackTrace();

        }

    }

}
```

---

# Clase principal

## Principal.java

```java
package com.ejemplo.calculadora;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);

        Calculadora calculadora=new Calculadora();

        OperacionDAO dao=new OperacionDAO();

        System.out.println("CALCULADORA");

        System.out.print("Numero 1: ");

        double n1=sc.nextDouble();

        System.out.print("Numero 2: ");

        double n2=sc.nextDouble();

        System.out.println();

        System.out.println("1 Sumar");
        System.out.println("2 Restar");
        System.out.println("3 Multiplicar");
        System.out.println("4 Dividir");

        int opcion=sc.nextInt();

        double resultado=0;

        String operacion="";

        switch(opcion){

            case 1:

                resultado=calculadora.sumar(n1,n2);

                operacion="SUMA";

                break;

            case 2:

                resultado=calculadora.restar(n1,n2);

                operacion="RESTA";

                break;

            case 3:

                resultado=calculadora.multiplicar(n1,n2);

                operacion="MULTIPLICACION";

                break;

            case 4:

                resultado=calculadora.dividir(n1,n2);

                operacion="DIVISION";

                break;

            default:

                System.out.println("Opcion incorrecta");

                return;

        }

        System.out.println();

        System.out.println("Resultado = "+resultado);

        Operacion op=new Operacion(

                n1,

                n2,

                operacion,

                resultado

        );

        dao.guardar(op);

        dao.listar();

    }

}
```

---

# Ejecución del programa

```text
CALCULADORA

Numero 1:
20

Numero 2:
5

1 Sumar
2 Restar
3 Multiplicar
4 Dividir

4

Resultado = 4.0

HISTORIAL

1 | 20.0 | DIVISION | 5.0 = 4.0
2 | 15.0 | SUMA | 10.0 = 25.0
3 | 9.0 | MULTIPLICACION | 7.0 = 63.0
```

---

# Tecnologías utilizadas

- Java 21
- IntelliJ IDEA
- Maven
- JDBC
- MySQL 8

---

# Mejoras sugeridas

Este ejemplo es intencionalmente sencillo. Se puede ampliar con funcionalidades como:

- Menú interactivo para realizar múltiples operaciones sin reiniciar el programa.
- Validación para evitar la división entre cero.
- Operaciones avanzadas:
  - Potencia
  - Raíz cuadrada
  - Porcentaje
  - Módulo
- Eliminación y consulta específica de operaciones almacenadas en MySQL.
- Arquitectura por capas (`model`, `dao`, `service`, `view`).
- Pruebas unitarias con **JUnit 5**.
- Exportación del historial a **Excel** o **PDF**.
- Interfaz gráfica utilizando **Swing** o **JavaFX**.

---

# Compatibilidad

Este proyecto es compatible con:

- Java 21
- IntelliJ IDEA
- Maven
- JDBC
- MySQL 8

---
```
:. . / .  
