package DataStructures.unal.applications;

import DataStructures.unal.datastructures.SimulatedPointerArrayLinearList;


import java.util.*;

public class BancoUnal {

    private SimulatedPointerArrayLinearList<Cliente> clientes;
    SimulatedPointerArrayLinearList<Character> tipos;
    SimulatedPointerArrayLinearList<Character> states;
    private static final String claveDelJefe = "UNrio";

    public SimulatedPointerArrayLinearList<Cliente> getClientes() {
        return clientes;
    }

    public BancoUnal() {
        clientes = new SimulatedPointerArrayLinearList<>();
        tipos = new SimulatedPointerArrayLinearList<>();
        states = new SimulatedPointerArrayLinearList<>();
        StringBuilder s = new StringBuilder("ABCDFGHIJKNOPRSTUVWXYZ");
        for (int i = 0; i < s.length(); i++)
            tipos.add(i, s.charAt(i));
        states.add(0, 'M');
        states.add(1, 'E');
        states.add(2, 'L');
        states.add(3, 'Q');
    }

    public void read() {
        clientes.load("BancoUnal.dat");
        Cliente.ids -= clientes.size();
    }

    public void save() {
        clientes.save("BancoUnal.dat");
    }

    public void addCliente(Cliente cliente) {
        clientes.add(clientes.size(), cliente);
    }

    public void addCliente(String nombre, char state, int saldo_cuenta_corriente,
                           int saldo_cuenta_ahorros, int estrato, char tipo) {
        clientes.add(clientes.size(), new Cliente(nombre, state, saldo_cuenta_corriente,
                saldo_cuenta_ahorros, estrato, tipo));

    }

    public Cliente removeCliente(int id) {
        int aux = 0;
        for (Cliente x : clientes) {
            if (x.getId() == id) {
                System.out.println("Este cliente ha sido borrado :(: " + clientes.remove(aux));
                return x;
            }
            aux++;
        }
        System.out.println("El cliente que busca no esta :(");
        return null;
    }

    public void editarCliente(int id) {
        for (Cliente x : clientes) {
            if (x.getId() == id) {
                Scanner scaner = new Scanner(System.in);
                try {
                    int aux = 0;
                    char tmp;
                    System.out.println("Ingrese el nuevo estrato");
                    aux = scaner.nextInt();
                    if (aux < 0 || aux > 6)
                        break;
                    x.setEstrato(aux);
                    System.out.println("Ingrese el nuevo nombre");
                    x.setNombre(scaner.next());
                    System.out.println("Ingrese el nuevo saldo cuenta corriente");
                    x.setSaldo_cuenta_corriente(scaner.nextInt());
                    System.out.println("Ingrese el nuevo saldo cuenta ahorros");
                    x.setSaldo_cuenta_ahorros(scaner.nextInt());
                    System.out.println("Ingrese el nuevo tipo");
                    tmp = scaner.next().toUpperCase().charAt(0);
                    if (tipos.indexOf(tmp) == -1)
                        x.setTipo('Z');
                    else
                        x.setTipo(tmp);
                    System.out.println("Ingrese el nuevo estado");
                    tmp = scaner.next().toUpperCase().charAt(0);
                    if (states.indexOf(tmp) == -1)
                        x.setState('L');
                    else
                        x.setState(tmp);
                    System.out.println("Cliente modificado");
//				scaner.close();
                    break;
                } catch (Exception e) {
                    System.out.println("El dato que ingreso no es valido");
                }
            }
        }
    }

    public Cliente consultarCliente(int id) {
        for (Cliente x : clientes)
            if (x.getId() == id) {
                System.out.println(x);
                return x;
            }
        System.out.println("No se encotro el cliente :(");
        return null;
    }

    public Cliente consultarCliente(String name) {
        for (Cliente x : clientes)
            if (x.getNombre().equals(name)) {
                System.out.println(x);
                return x;
            }
        System.out.println("No se encotro el cliente :(");
        return null;
    }

    public int getGranTotal() {
        int total = 0;
        for (Cliente x : clientes)
            total += x.getSaldo_cuenta_ahorros() + x.getSaldo_cuenta_corriente();
        return total;
    }

    public void getTotalPorTipo() {
        SimulatedPointerArrayLinearList<Integer> tmp = new SimulatedPointerArrayLinearList<>(24);
        for (int i = 0; i < tipos.size(); i++)
            tmp.add(i, 0);
        int aux = 0;
        for (Cliente x : clientes) {
            aux = tipos.indexOf(x.getTipo());
            tmp.add(aux, tmp.remove(aux) + x.getSaldo_total());
        }
        for (int i = 0; i < tipos.size(); i++)
            System.out.println(tipos.get(i) + " Saldo total: " + tmp.get(i));
    }

    public void getTotalPorEstrato() {
        SimulatedPointerArrayLinearList<Integer> tmp = new SimulatedPointerArrayLinearList<>(6);
        for (int i = 0; i < 7; i++)
            tmp.add(i, 0);
        int aux = 0;
        for (Cliente x : clientes) {
            aux = x.getEstrato();
            tmp.add(aux, tmp.remove(aux) + x.getSaldo_total());
        }
        for (int i = 0; i < 7; i++)
            System.out.println("Estrato " + i + " Saldo total: " + tmp.get(i));
    }

    @SuppressWarnings("unused")
    private void saldoTotalDeCadaUno() {
        for (Cliente x : clientes)
            System.out.println(x.getNombre() + ", estado " + x.getState() + ", Saldo total " + x.getSaldo_total());
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Clientes: \n");
        for (Cliente x : clientes)
            s.append(x);

        return new String(s);
    }

    public static void main(String[] args) {
        BancoUnal banco = new BancoUnal();
        banco.read();
        while (true) {
            System.out.println("\n UNBanco SuperAstroEstructurador \n");
            System.out.println("Ingrese 1 para el ingreso de un nuevo cliente.");
            System.out.println("Ingrese 2 para hacer la modificaci�n de un cliente.");
            System.out.println("Ingrese 3 para hacer la eliminaci�n de un cliente.");
            System.out.println("Ingrese 4 para hacer la consulta de un cliente por id.");
            System.out.println("Ingrese 5 para hacer la consulta de un estudiante por nombre.");
            System.out.println("Ingrese 6 para hacer consultar el gran total.");
            System.out.println("Ingrese 7 para hacer consultar el gran total por tipo.");
            System.out.println("Ingrese 8 para hacer consultar el gran total por estrato.");
            System.out.println("Ingrese 9 para hacer consultar el gran total de cada cliente.");
            System.out.println("Ingrese 10 para mirar lo visible de cada clientes.");
            System.out.println("Ingrese 0 para guardar y salir del programa.");
            try {
                int option = 0;
                Scanner scan = new Scanner(System.in);
                option = scan.nextInt();
                switch (option) {
                    case 1:
                        Cliente x = new Cliente();
                        try {
                            int aux = 0;
                            char tmp;
                            System.out.println("Ingrese el estrato");
                            aux = scan.nextInt();
                            if (aux < 0 || aux > 6) {
                                System.out.println("Estrato no valido");
                                break;
                            }
                            x.setEstrato(aux);
                            System.out.println("Ingrese el nombre");
                            x.setNombre(scan.next());
                            System.out.println("Ingrese el saldo cuenta corriente");
                            x.setSaldo_cuenta_corriente(scan.nextInt());
                            System.out.println("Ingrese el saldo cuenta ahorros");
                            x.setSaldo_cuenta_ahorros(scan.nextInt());
                            System.out.println("Ingrese el tipo");
                            tmp = scan.next().toUpperCase().charAt(0);
                            if (banco.tipos.indexOf(tmp) == -1)
                                x.setTipo('Z');
                            else
                                x.setTipo(tmp);
                            System.out.println("Ingrese el estado");
                            tmp = scan.next().toUpperCase().charAt(0);
                            if (banco.states.indexOf(tmp) == -1)
                                x.setState('L');
                            else
                                x.setState(tmp);
                            banco.addCliente(x);
                            System.out.println("El cliente ha sido creado, su id es " + x.getId());
                            break;
                        } catch (Exception e) {
                            System.out.println("El dato que ingreso no es valido");
                            break;
                        }
                    case 2:
                        System.out.println("Ingrese el id.");
                        banco.editarCliente(scan.nextInt());
                        break;
                    case 3:
                        try {
                            System.out.println("Ingrese el id.");
                            banco.removeCliente(scan.nextInt());
                            break;
                        } catch (Exception e) {
                            System.out.println("El id es incorrecto.");
                            break;
                        }
                    case 4:
                        try {
                            System.out.println("Ingrese el id.");
                            banco.consultarCliente(scan.nextInt());
                            break;
                        } catch (Exception e) {
                            System.out.println("El id es incorrecto.");
                            break;
                        }
                    case 5:
                        System.out.println("Ingrese el nombre.");
                        banco.consultarCliente(scan.next());
                        break;
                    case 6:
                        System.out.println(banco.getGranTotal());
                        break;
                    case 7:
                        banco.getTotalPorTipo();
                        break;
                    case 8:
                        banco.getTotalPorEstrato();
                        break;
                    case 9:
                        System.out.println("Ingrese su clave.");
                        if (BancoUnal.claveDelJefe.equals(scan.next()))
                            banco.getTotalPorEstrato();
                        else
                            System.out.println("You are not the boss");
                        break;
                    case 10:
                        System.out.println(banco);
                        break;
                    case 0:
                        scan.close();
                        banco.save();
                        System.exit(0);
                    default:
                        System.out.println("Your option is not valid");
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("\n upps un error Inicie otra vez");
                break;
            }
        }
    }
}