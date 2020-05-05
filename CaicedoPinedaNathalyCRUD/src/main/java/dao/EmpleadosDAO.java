/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import entidades.Empleado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alvaro
 */
public class EmpleadosDAO {

    private Connection conexion = null;

    //establecer la conexion con la base de datos
    public EmpleadosDAO() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/jardineria", "root", "Jomevimilu88");
        } catch (SQLException ex) {
            System.err.println("Error al conectar: " + ex.getMessage());
            //ex.printStackTrace(); //usarlo sólo cuando se hacen pruebas del programa ya que avisa de dónde se encuentra el problema
        }
    }

    //comprobar la conexion con la base de datos
    public Connection getConexion() {
        return conexion;
    }

    //realizar la consulta
    public Empleado read(Integer idEmpleado) {
        Empleado empleado = null;
        PreparedStatement stmt = null;

        if (this.conexion == null) {
            return null;
        }

        try {
            String query = "SELECT * FROM empleados WHERE codigoempleado = ?";
            stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idEmpleado);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                empleado = new Empleado(
                        rs.getInt("CodigoEmpleado"),
                        rs.getString("Nombre"),
                        rs.getString("Apellido1"),
                        rs.getString("Apellido2"),
                        rs.getString("Extension"),
                        rs.getString("Email"),
                        rs.getString("CodigoOficina"),
                        rs.getInt("CodigoJefe"),
                        rs.getString("Puesto")
                );
            }

            stmt.close();

        } catch (SQLException e) {

            System.err.println("Error en el Select: " + e.getMessage() + "\nQuery: " + stmt.toString());
        }

        return empleado;
    }

    //insertar datos
    public Boolean insert(Empleado empleado) {
        Boolean resultado = false;
        PreparedStatement stmt = null;
        Integer ultimoID = null;

        if (this.conexion == null || empleado == null) {
            return false;
        }

        try {
//            String query = "SELECT Max(CodigoEmpleado)+1 FROM `empleados`";
//            PreparedStatement stm = conexion.prepareStatement(query);
//            ResultSet rs = stm.executeQuery();
//            if(rs.next()){
//                ultimoID = rs.getInt("CodigoEmpleado") + 1;
//                empleado.setCodigoEmpleado(ultimoID);
//            }
//            

            /**
             * Inserto en la BBDD un empelado con un código mayor en uno que
             * todos los anteriores. Esta BD no tiene autoincremental en en el
             * campo codito de empleado
             */
            String sql = "INSERT INTO empleados "
                    + "(CodigoEmpleado, nombre, apellido1, apellido2, extension, email, codigooficina, codigojefe, puesto) "
                    + "VALUES ((SELECT Max(CodigoEmpleado)+1 FROM `empleados` E), ?, ?, ?, ?, ?, ?, ?, ?)";

            stmt = conexion.prepareStatement(sql);
//            stmt.setInt(1, empleado.getCodigoEmpleado());
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido1());
            stmt.setString(3, empleado.getApellido2());
            stmt.setString(4, empleado.getExtension());
            stmt.setString(5, empleado.getEmail());
            stmt.setString(6, empleado.getCodigoOficina());
            stmt.setInt(7, empleado.getCodigoJefe());
            stmt.setString(8, empleado.getPuesto());

            if (stmt.executeUpdate() > 0) {
                resultado = true;

            }
        } catch (SQLException e) {
            System.err.println("Error en el Insert: " + e.getMessage()+ " SQL:" + stmt.toString());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }

        return resultado;

    }

    //actualizar datos del empleado
    public Boolean update(Empleado empleado) {
        Boolean resultado = null;
        PreparedStatement stmt = null;

        if (this.conexion == null || empleado == null) {
            return false;
        }

        try {

            String sql = "UPDATE empleados SET nombre = ?, apellido1 = ?, apellido2 = ?, extension = ?"
                    + ", email = ?, codigooficina = ?, codigojefe = ?, puesto = ? WHERE codigoempleado = ?";

            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido1());
            stmt.setString(3, empleado.getApellido2());
            stmt.setString(4, empleado.getExtension());
            stmt.setString(5, empleado.getEmail());
            stmt.setString(6, empleado.getCodigoOficina());
            stmt.setInt(7, empleado.getCodigoJefe());
            stmt.setString(8, empleado.getPuesto());

            stmt.setInt(9, empleado.getCodigoEmpleado());
            if (stmt.executeUpdate() > 0) {
                resultado = true;

            }
        } catch (SQLException e) {
            System.err.println("Error en el Update: " + e.getMessage()+ " SQL:" + stmt.toString());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }

        return resultado;
    }

    //eliminar un registro
    public Boolean delete(Integer idEmpleado) {
        Boolean resultado = false;
        PreparedStatement stmt = null;

        try {
            String sql = "DELETE FROM empleados WHERE codigoempleado = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idEmpleado);

            resultado = stmt.execute();

            stmt.close();

            System.out.println();

        } catch (SQLException e) {

            System.err.println("Error en el Delete: " + e.getMessage() + " " + stmt.toString());
        }

        return resultado;

    }

    public ArrayList<Empleado> listar() {
        ArrayList<Empleado> lista = new ArrayList<>();
        PreparedStatement stm = null;
        String sql = "SELECT * FROM `empleados` ";
        try {
            stm = conexion.prepareStatement(sql);
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Empleado empleado;
                empleado = new Empleado(
                        rs.getInt("CodigoEmpleado"),
                        rs.getString("Nombre"),
                        rs.getString("Apellido1"),
                        rs.getString("Apellido2"),
                        rs.getString("Extension"),
                        rs.getString("Email"),
                        rs.getString("CodigoOficina"),
                        rs.getInt("CodigoJefe"),
                        rs.getString("Puesto")
                );
                lista.add(empleado);

            }
        } catch (SQLException ex) {
            System.err.println("Error en listar empleados " + ex.getMessage());
        }
        //devuelve un array de empleados    
        return lista;
    }
}
