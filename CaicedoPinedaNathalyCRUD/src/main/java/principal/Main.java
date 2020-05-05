/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import dao.EmpleadosDAO;
import entidades.Empleado;
import java.util.Scanner;

/**
 *
 * @author Alvaro
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static Scanner sc = new Scanner(System.in, "ISO-8859-1");
    static EmpleadosDAO empleados = new EmpleadosDAO();

    public static void main(String[] args) {
        Integer opcion = null;

        if (empleados.getConexion() == null) {
            System.err.println("Programa terminado. Error en la conexión.");
            System.exit(1);
        }

        System.out.println("\t\tBIENVENIDO");
        System.out.println("\t\t-----------");
        while (true) {
            try {
                System.out.println("ELIJA ALGUNA DE LAS OPCIONES QUE SE MUESTRAN A CONTINUACIÓN\n");
                System.out.println("  1. Buscar datos de empleados.");
                System.out.println("  2. Insertar datos de un empleado.");
                System.out.println("  3. Actualizar datos de un empleado.");
                System.out.println("  4. Eliminar datos de un empleado.\n");
                System.out.print("Su elección [introduzca 0 para salir]: ");
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 0:
                        System.out.println("\nHasta pronto.\n");
                        System.out.println("\t    -------------");
                        System.out.println("\t\tFIN\n");
                        System.exit(0);
                        break;
                    case 1:
                        System.out.println("\nBÚSQUEDA");
                        System.out.println("--------");
                        buscarEmpleado();
                        break;
                    case 2:
                        System.out.println("\nINSERCIÓN");
                        System.out.println("---------");
                        introducirEmpleado();
                        break;
                    case 3:
                        System.out.println("\nACTUALIZAR");
                        System.out.println("----------");
                        actualizarEmpleado();
                        break;
                    case 4:
                        System.out.println("\nBORRADO");
                        System.out.println("-------");
                        borrarEmpleado();
                        break;
                    default:
                        System.out.println("\nIntroduzca alguna de las opciones válidas.");
                }
                System.out.println();
            } catch (NumberFormatException nfe) {
                System.err.println("\nError: Entrada no válida." + nfe.getMessage() + "\n");
            }
        }
    }

    public static Empleado existeEmpleado() {
        Empleado empleado = null;

        System.out.print("Indique el ID del empleado que desea buscar: ");
        empleado = empleados.read(Integer.parseInt(sc.nextLine()));

        return empleado;
    }

    public static void buscarEmpleado() {
        Empleado empleado = existeEmpleado();

        if (empleado != null) {
            System.out.println(empleado.toString());
        } else {
            System.err.println("El empleado no existe o no se puede leer.");
        }
    }

    public static void introducirEmpleado() {
        Empleado empleado = new Empleado();

        System.out.print("Indique el nombre del empleado: ");
        empleado.setNombre(sc.nextLine());

        System.out.print("Indique el primer apellido del empleado: ");
        empleado.setApellido1(sc.nextLine());

        System.out.print("Indique el segundo apellido del empleado: ");
        empleado.setApellido2(sc.nextLine());

        System.out.print("Indique la extensión del empleado: ");
        empleado.setExtension(sc.nextLine());

        System.out.print("Indique el email del empleado: ");
        empleado.setEmail(sc.nextLine());

        System.out.print("Indique el código de oficina del empleado: ");
        empleado.setCodigoOficina(sc.nextLine());

        System.out.print("Indique el código del jefe del empleado: ");
        empleado.setCodigoJefe(Integer.parseInt(sc.nextLine()));

        System.out.print("Indique el puesto del empleado: ");
        empleado.setPuesto(sc.nextLine());


        if (empleados.insert(empleado)) {
            System.out.println("El empleado '" + empleado.getNombre() + " " + empleado.getApellido1()
                    + " " + empleado.getApellido2() + "' ha sido añadido satisfactoriamente.");
        } else {
            System.err.println("El empleado que intenta introducir no es válido.\n");
        }
    }

    public static void actualizarEmpleado() {
        Empleado empleado = existeEmpleado();

        if (empleado == null) {
            System.err.println("El empleado no existe o no se puede leer.");
            return;
        }

        while (true) {
            try {
                System.out.println("\n" + empleado);

                Integer opcion;

                System.out.println("\nELIJA ALGUNA DE LAS OPCIONES QUE SE MUESTRAN A CONTINUACIÓN\n");
                System.out.println("  1. Nombre.");
                System.out.println("  2. Primer apellido.");
                System.out.println("  3. Segundo apellido.");
                System.out.println("  4. Extensión.");
                System.out.println("  5. Email.");
                System.out.println("  6. Código de oficina.");
                System.out.println("  7. Código de jefe.");
                System.out.println("  8. Puesto.");
                System.out.print("\nSu elección [introduzca 0 para retroceder]: ");
                opcion = Integer.parseInt(sc.nextLine());

                if (opcion > 0 && opcion < 9) {
                    System.out.print("\nIntroduzca la modificación que desea realizar: ");
                }

                switch (opcion) {
                    case 0:
                        return;
                    case 1:
                        empleado.setNombre(sc.nextLine());
                        empleados.update(empleado);
                        break;
                    case 2:
                        empleado.setApellido1(sc.nextLine());
                        empleados.update(empleado);
                        break;
                    case 3:
                        empleado.setApellido2(sc.nextLine());
                        empleados.update(empleado);
                        break;
                    case 4:
                        empleado.setExtension(sc.nextLine());
                        empleados.update(empleado);
                        break;
                    case 5:
                        empleado.setEmail(sc.nextLine());
                        empleados.update(empleado);
                        break;
                    case 6:
                        empleado.setCodigoOficina(sc.nextLine());
                        empleados.update(empleado);
                        break;
                    case 7:
                        empleado.setCodigoJefe(Integer.parseInt(sc.nextLine()));
                        empleados.update(empleado);
                        break;
                    case 8:
                        empleado.setPuesto(sc.nextLine());
                        empleados.update(empleado);
                        break;
                    default:
                        System.out.println("\nIntroduzca alguna de las opciones válidas.\n");
                }
            } catch (NumberFormatException nfe) {
                System.err.println("\nError: " + nfe.getMessage() + "\n");
            }
        }
    }

    public static void borrarEmpleado() {
        Empleado empleado = existeEmpleado();
        String resp = null;

        if (empleado != null) {
                System.out.println("\n¿Está seguro que desea eliminar al siguiente usuario?"
                        + "\n  " + empleado);
                System.out.print("Su respuesta [Y/N]: ");
                resp = sc.nextLine();
                
                if (resp.equalsIgnoreCase("y")) {
                    empleados.delete(empleado.getCodigoEmpleado());
                    System.out.println("Entrada eliminada.");
                } else {
                    System.out.println("Entrada no eliminada.");
                }
        } else {
            System.err.println("El empleado no existe o no se puede leer.");
        }
    }

}
