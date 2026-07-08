package com.ejemplo.calculadora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OperacionDAO {

    public void guardar(Operacion op) {
        try(Connection con = Conexion.conectar()) {

            String sql =
                    "insert into operaciones(numero1,numero2,operacion,resultado) values(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, op.getNumero1());
            ps.setDouble(2, op.getNumero2());
            ps.setString(3, op.getOperacion());
            ps.setDouble(4, op.getResultado());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listar() {

        try(Connection con = Conexion.conectar()){

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
