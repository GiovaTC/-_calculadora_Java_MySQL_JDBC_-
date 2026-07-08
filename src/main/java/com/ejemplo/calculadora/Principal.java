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