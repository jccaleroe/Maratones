package DataStructures.unal.datastructures.taller3;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {

    JFrame inicioFrame;
    JFrame userFrame;
    JFrame optionFrame;
    JFrame superUserFrame;
    JFrame administratorFrame;
    JFrame cargaFrame;
    JFrame superUserMenu;
    String station;
    int userCedula;
    String consultas;
    JTextField incomingC;
    JFrame consultaFrame;
    JFrame crearFrame;

    public void go() {
        inicioFrame = new JFrame("MOTO RUN RUN");
        Box box = new Box(BoxLayout.Y_AXIS);
        JPanel panel2 = new JPanel();
        JLabel guide = new JLabel("BIENVENIDOS A UNAL MOTOR RUN");
        panel2.add(guide);
        inicioFrame.add(BorderLayout.NORTH, panel2);
        JLabel login = new JLabel("Login:");
        box.add(login);
        JTextField loginF = new JTextField(20);
        box.add(loginF);
        JLabel password = new JLabel("Password:");
        box.add(password);
        JPasswordField passwordF = new JPasswordField(20);
        box.add(passwordF);
        JLabel guide2 = new JLabel("\n");
        box.add(guide2);
        JButton bottom = new JButton("Enter");
        bottom.addActionListener(new EnterListener(loginF, passwordF));
        box.add(bottom);
        JPanel panel = new JPanel();
        panel.add(box);
        inicioFrame.add(panel);
        inicioFrame.setVisible(true);
        inicioFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicioFrame.setSize(300, 300);
    }

    class EnterListener implements ActionListener {
        JTextField login;
        JPasswordField password;

        public EnterListener(JTextField a, JPasswordField b) {
            login = a;
            password = b;
        }

        public void actionPerformed(ActionEvent e) {
            int cedula;
            try {
                cedula = Integer.parseInt(login.getText());
            } catch (NumberFormatException z) {
                cedula = 0;
            }
            String pass = String.valueOf(password.getPassword());
            password.setText("");
            password.requestFocus();
            try {
                int type = MotoRUN.checkUserLogin(cedula, pass, true);
                User user;
                switch (type) {
                    case 2:
                        user = MotoRUN.getUsers().get(cedula);
                        JOptionPane.showMessageDialog(inicioFrame, "Binevenodo " + user.getNombre() +
                                ", Aqui esta tu perfil:\n" + user);
                        createSuperUserFrame();
                        break;
                    case 3:
                        user = MotoRUN.getUsers().get(cedula);
                        JOptionPane.showMessageDialog(inicioFrame, "Binevenodo " + user.getNombre() +
                                ", Aqui esta tu perfil:\n" + user);
                        createAdministratorFrame();
                        break;
                    default:
                        JOptionPane.showMessageDialog(inicioFrame, "Password incorrect");
                        break;
                }
            } catch (IllegalArgumentException ee) {
                JOptionPane.showMessageDialog(inicioFrame, ee.getMessage());
            }

        }
    }

    public void createSuperUserFrame() {
        superUserFrame = new JFrame("SuperUser");
        Box box = new Box(BoxLayout.Y_AXIS);
        JLabel guide = new JLabel("Ingrese la estacion en donde se encuentra:");
        box.add(guide);
        JLabel guide2 = new JLabel("\n");
        box.add(guide2);
        for (Station x : MotoRUN.getStations()) {
            if (!x.getId().equals("ET") && !x.getId().equals("TM")) {
                JButton aux = new JButton(x.getName());
                aux.addActionListener(new SuperUserMenuListener(x.getId()));
                box.add(aux);
            }
        }
        JPanel panel = new JPanel();
        panel.add(box);
        JButton back = new JButton("Back");
        back.addActionListener(new BackListener(inicioFrame, superUserFrame));
        superUserFrame.add(panel);
        superUserFrame.add(BorderLayout.SOUTH, back);
        superUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        superUserFrame.setSize(400, 500);
        inicioFrame.setVisible(false);
        superUserFrame.setVisible(true);
    }

    class SuperUserMenuListener implements ActionListener {
        String aux;

        SuperUserMenuListener(String x) {
            aux = x;
        }

        public void actionPerformed(ActionEvent e) {
            station = aux;
            superUserMenu = new JFrame("Super User " + station);
            Box box = new Box(BoxLayout.Y_AXIS);
            Box box2 = new Box(BoxLayout.Y_AXIS);
            Box box3 = new Box(BoxLayout.Y_AXIS);
            JLabel guide = new JLabel("Ingrese la opcion que desea relizar:");
            JPanel up = new JPanel();
            up.add(guide);
            superUserMenu.add(BorderLayout.NORTH, up);
            GridLayout grid = new GridLayout(1, 3);
            JPanel gridPanel = new JPanel(grid);
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            JButton cargar = new JButton("Cargar moto");
            // Anonymous Inner class
            cargar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    checkCargaFrame();
                }
            });
            box.add(cargar);
            JButton descargar = new JButton("Descargar moto");
            descargar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    checkDescargaFrame();
                }
            });
            box.add(descargar);
            JButton crearUsuario = new JButton("Crear Usuario");
            box.add(crearUsuario);
            crearUsuario.addActionListener(new CrearUsuarioListener(1));
            JButton editarUsuario = new JButton("Editar Usuario");
            box.add(editarUsuario);
            editarUsuario.addActionListener(new ModificarUsuarioListener());
            JButton histograma = new JButton("Histograma de prestamos");
            box2.add(histograma);
            histograma.addActionListener(new HistogramaListener());
            JButton editarMoto = new JButton("Editar Moto");
            box2.add(editarMoto);
            editarMoto.addActionListener(new ModificarMotoListener());
            JButton consultarMoto = new JButton("Consultar Moto");
            box2.add(consultarMoto);
            consultarMoto.addActionListener(new ConsultarMotoListener());
            JButton consultarUsuario = new JButton("Consultar usuario");
            box2.add(consultarUsuario);
            consultarUsuario.addActionListener(new ConsultarUsuarioListener());
            JButton promedioDeLlegadas = new JButton("Promedio de Tiempo en llegar a una estacion");
            box3.add(promedioDeLlegadas);
            promedioDeLlegadas.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    promedioTiempo();
                }
            });
            JButton listarMotos = new JButton("Listar Motos");
            box3.add(listarMotos);
            listarMotos.addActionListener(new ListarMotosListener());
            JButton listarEstaciones = new JButton("Listar Estaciones");
            box3.add(listarEstaciones);
            listarEstaciones.addActionListener(new ListarEstacionesListener());
            JButton listarUsuarios = new JButton("Listar Usuarios");
            listarUsuarios.addActionListener(new ListarUsuariosListener());
            box3.add(listarUsuarios);
            panel1.add(box);
            panel2.add(box2);
            panel3.add(box3);
            gridPanel.add(panel1);
            gridPanel.add(panel2);
            gridPanel.add(panel3);
            superUserMenu.add(gridPanel);
            superUserMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JButton back = new JButton("Back");
            back.addActionListener(new BackListener(inicioFrame, superUserMenu));
            superUserMenu.add(BorderLayout.SOUTH, back);
            superUserMenu.setSize(900, 310);
            superUserFrame.setVisible(false);
            superUserMenu.setVisible(true);
        }
    }

    public void promedioTiempo() {
        cargaFrame = new JFrame("Consulta de tiempo");
        Box box = new Box(BoxLayout.Y_AXIS);
        JLabel guide = new JLabel("Ingrese la estacion que desea saber el tiempo promedio:");
        box.add(guide);
        JLabel guide2 = new JLabel("\n");
        box.add(guide2);
        for (Station x : MotoRUN.getStations()) {
            if (!x.getId().equals("ET") && !x.getId().equals("TM")) {
                JButton aux = new JButton(x.getName());
                aux.addActionListener(new DestinoTiempoListener(x.getId()));
                box.add(aux);
            }
        }
        JPanel panel = new JPanel();
        panel.add(box);
        cargaFrame.add(panel);
        cargaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton back = new JButton("Back");
        back.addActionListener(new BackListener(superUserMenu, cargaFrame));
        cargaFrame.add(BorderLayout.SOUTH, back);
        cargaFrame.setSize(600, 300);
        superUserMenu.setVisible(false);
        cargaFrame.setVisible(true);
    }

    class DestinoTiempoListener implements ActionListener {
        String id;
        long time;
        int n;

        public DestinoTiempoListener(String x) {
            id = x;
            time = 0;
            n = 0;
        }

        public void actionPerformed(ActionEvent e) {

            for (ArrayList<Loan> x : MotoRUN.getLoans()) {
                for (Loan y : x) {
                    if (y.getStationA().equals(station) && y.getStationB().equals(id)) {
                        time += (y.getTimeB() - y.getTimeA());
                        n++;
                    }
                }
            }
            if (n != 0) {
                long i = (time / (n * 1000 * 60));
                JOptionPane.showMessageDialog(cargaFrame, "El tiempo promedio de llegada es : " + i + " minutos");
            } else
                JOptionPane.showMessageDialog(cargaFrame, "No hay prestamos hacia ese destino");
        }
    }

    class ListarMotosListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JFrame framel = new JFrame("Motos");
            JTextArea incoming = new JTextArea(15, 50);
            incoming.setLineWrap(true);
            incoming.setWrapStyleWord(true);
            incoming.setEditable(false);
            JScrollPane scroller = new JScrollPane(incoming);
            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            JPanel panel = new JPanel();
            panel.add(scroller);
            framel.add(panel);
            incoming.append(MotoRUN.getMotos().toString());
            framel.setSize(650, 400);
            framel.setVisible(true);
        }
    }

    class ListarEstacionesListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JFrame framel = new JFrame("Estaciones");
            JTextArea incoming = new JTextArea(15, 50);
            incoming.setLineWrap(true);
            incoming.setWrapStyleWord(true);
            incoming.setEditable(false);
            JScrollPane scroller = new JScrollPane(incoming);
            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            JPanel panel = new JPanel();
            panel.add(scroller);
            framel.add(panel);
            incoming.append(MotoRUN.getStations().toString());
            framel.setSize(650, 400);
            framel.setVisible(true);
        }
    }

    class CrearUsuarioListener implements ActionListener {
        int type;

        CrearUsuarioListener(int tip) {
            type = tip;
        }

        JTextField incoming;
        JPasswordField incoming2;
        JTextField incoming3;
        JTextField incoming4;
        JTextField incoming5;

        public void crear() {
            try {
                int c = Integer.parseInt(incoming.getText());
                if (MotoRUN.getUsers().get(c) == null) {
                    User user = new User(c, incoming4.getText(), String.valueOf(incoming2.getPassword()), incoming4.getText(), incoming3.getText(), type);
                    MotoRUN.getUsers().put(c, user);
                    JOptionPane.showMessageDialog(crearFrame, "Usuario creado :\n" + user);
                    MotoRUN.saveUsers();
                } else
                    JOptionPane.showMessageDialog(crearFrame, "El usuario ya existe");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(crearFrame, "La cedula es incorrecta");
            }
        }

        public void actionPerformed(ActionEvent e) {
            crearFrame = new JFrame("Crear usuario");
            JLabel guide = new JLabel("Por favor ingrese la cedula del usuario");
            incoming = new JTextField(20);
            JLabel guide2 = new JLabel("Por favor ingrese la clave del usuario");
            incoming2 = new JPasswordField(20);
            JLabel guide3 = new JLabel("Por favor ingrese el correo del usuario");
            incoming3 = new JTextField(20);
            JLabel guide4 = new JLabel("Por favor ingrese el nombre del usuario");
            incoming4 = new JTextField(20);
            JLabel guide5 = new JLabel("Por favor ingrese la carrera del usuario");
            incoming5 = new JTextField(20);
            JButton button = new JButton("Enter");
            Box box = new Box(BoxLayout.Y_AXIS);
            box.add(guide);
            box.add(incoming);
            box.add(guide2);
            box.add(incoming2);
            box.add(guide3);
            box.add(incoming3);
            box.add(guide4);
            box.add(incoming4);
            box.add(guide5);
            box.add(incoming5);
            box.add(button);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    crear();
                }
            });
            JPanel panel = new JPanel();
            panel.add(box);
            crearFrame.add(panel);
            crearFrame.setSize(400, 500);
            crearFrame.setVisible(true);
        }
    }

    class AgregarMotoListener implements ActionListener {

        JTextField incoming;
        JTextField incoming2;
        JTextField incoming3;
        JTextField incoming4;
        JTextField incoming5;
        JTextField incoming6;

        public void crear() {
            try {
                Long c = Long.parseLong(incoming.getText());
                int cc = Integer.parseInt(incoming2.getText());
                if (MotoRUN.getStations().get(incoming3.getText()) != null) {
                    Moto moto = MotoRUN.getMotos().get(c);
                    if (moto == null) {
                        moto = new Moto(c, incoming3.getText(), incoming4.getText(), incoming5.getText(),
                                incoming6.getText(), cc);
                        MotoRUN.getMotos().put(c, moto);
                        JOptionPane.showMessageDialog(crearFrame, "Moto creada :\n" + moto);
                    } else
                        JOptionPane.showMessageDialog(crearFrame, "La moto ya existe");
                } else
                    JOptionPane.showMessageDialog(crearFrame, "Esta estacion no existe");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(crearFrame, "El barCode o el costo es oncorrecto");
            }
        }

        public void actionPerformed(ActionEvent e) {
            crearFrame = new JFrame("Agregar Moto");
            JLabel guide = new JLabel("Por favor ingrese el barCode");
            incoming = new JTextField(20);
            JLabel guide2 = new JLabel("Por favor ingrese cuanto le costo la moto");
            incoming2 = new JTextField(20);
            JLabel guide3 = new JLabel("Por favor ingrese la ubicacion de la moto (el ID de la estacion)");
            incoming3 = new JTextField(20);
            JLabel guide4 = new JLabel("Por favor ingrese la marca de la moto");
            incoming4 = new JTextField(20);
            JLabel guide5 = new JLabel("Por favor ingrese la placa de la moto");
            incoming5 = new JTextField(20);
            JLabel guide6 = new JLabel("Por favor ingrese el estado de la moto (DI o PR o DA)");
            incoming6 = new JTextField(20);
            JButton button = new JButton("Enter");
            Box box = new Box(BoxLayout.Y_AXIS);
            box.add(guide);
            box.add(incoming);
            box.add(guide2);
            box.add(incoming2);
            box.add(guide3);
            box.add(incoming3);
            box.add(guide4);
            box.add(incoming4);
            box.add(guide5);
            box.add(incoming5);
            box.add(guide6);
            box.add(incoming6);
            box.add(button);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    crear();
                }
            });
            JPanel panel = new JPanel();
            panel.add(box);
            crearFrame.add(panel);
            crearFrame.setSize(600, 500);
            crearFrame.setVisible(true);
        }
    }

    class ModificarUsuarioListener implements ActionListener {
        JTextField incoming;
        JPasswordField incoming2;
        JTextField incoming3;
        JTextField incoming4;
        JTextField incoming5;
        JTextField incoming6;

        public void modificar() {
            try {
                int c = Integer.parseInt(incoming.getText());
                int i = MotoRUN.checkUserLogin(c, String.valueOf(incoming2.getPassword()), true);
                int cc = Integer.parseInt(incoming4.getText());
                if (i > 0 && i < 4) {
                    User user = MotoRUN.getUsers().remove(c);
                    user.setCorreo(incoming3.getText());
                    user.setCedula(cc);
                    user.setClave(incoming5.getText());
                    user.setNombre(incoming6.getText());
                    MotoRUN.getUsers().put(cc, user);
                    MotoRUN.saveUsers();
                    JOptionPane.showMessageDialog(crearFrame, "Usuario modificado :\n" + user);
                } else
                    JOptionPane.showMessageDialog(crearFrame, "El usuario no existe o la clave esta mal escrita");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(crearFrame, "Alguna de las cedulas es incorrecta");
            }
        }

        public void actionPerformed(ActionEvent e) {
            crearFrame = new JFrame("Modificar usuario");
            JLabel guide = new JLabel("Por favor ingrese la cedula del usuario");
            incoming = new JTextField(20);
            JLabel guide2 = new JLabel("Por favor ingrese la clave del usuario");
            incoming2 = new JPasswordField(20);
            JLabel guide3 = new JLabel("Por favor ingrese el nuevo correo del usuario");
            incoming3 = new JTextField(20);
            JLabel guide4 = new JLabel("Por favor ingrese la nueva cedula del usuario");
            incoming4 = new JTextField(20);
            JLabel guide5 = new JLabel("Por favor ingrese la nueva clave del usuario");
            incoming5 = new JTextField(20);
            JLabel guide6 = new JLabel("Por favor ingrese el nuevo nombre del usuario");
            incoming6 = new JTextField(20);
            JButton button = new JButton("Enter");
            Box box = new Box(BoxLayout.Y_AXIS);
            box.add(guide);
            box.add(incoming);
            box.add(guide2);
            box.add(incoming2);
            box.add(guide3);
            box.add(incoming3);
            box.add(guide4);
            box.add(incoming4);
            box.add(guide5);
            box.add(incoming5);
            box.add(guide6);
            box.add(incoming6);
            box.add(button);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modificar();
                }
            });
            JPanel panel = new JPanel();
            panel.add(box);
            crearFrame.add(panel);
            crearFrame.setSize(400, 500);
            crearFrame.setVisible(true);
        }
    }

    class ModificarMotoListener implements ActionListener {
        JTextField incoming;
        JTextField incoming3;
        JTextField incoming4;
        JTextField incoming5;
        JTextField incoming6;

        public void modificar() {
            try {
                Long c = Long.parseLong(incoming.getText());
                if (MotoRUN.getStations().get(incoming3.getText()) != null) {
                    Moto moto = MotoRUN.getMotos().remove(c);
                    if (moto != null) {
                        moto.setLocation(incoming3.getText());
                        moto.setBrand((incoming4.getText()));
                        moto.setPlate(incoming5.getText());
                        moto.setState(incoming6.getText());
                        MotoRUN.getMotos().put(c, moto);
                        MotoRUN.saveMotos();
                        MotoRUN.saveStations();
                        JOptionPane.showMessageDialog(crearFrame, "Moto modificada :\n" + moto);
                    } else
                        JOptionPane.showMessageDialog(crearFrame, "La moto no existe");
                } else
                    JOptionPane.showMessageDialog(crearFrame, "Esta estacion no existe");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(crearFrame, "El barCode es incorrecto");
            }
        }

        public void actionPerformed(ActionEvent e) {
            crearFrame = new JFrame("Modificar Moto");
            JLabel guide = new JLabel("Por favor ingrese el barCode");
            incoming = new JTextField(20);
            JLabel guide3 = new JLabel("Por favor ingrese la nueva ubicacion de la moto (el ID de la estacion)");
            incoming3 = new JTextField(20);
            JLabel guide4 = new JLabel("Por favor ingrese la nueva marca de la moto");
            incoming4 = new JTextField(20);
            JLabel guide5 = new JLabel("Por favor ingrese la nueva placa de la moto");
            incoming5 = new JTextField(20);
            JLabel guide6 = new JLabel("Por favor ingrese el nuevo estado de la moto (DI o PR o DA)");
            incoming6 = new JTextField(20);
            JButton button = new JButton("Enter");
            Box box = new Box(BoxLayout.Y_AXIS);
            box.add(guide);
            box.add(incoming);
            box.add(guide3);
            box.add(incoming3);
            box.add(guide4);
            box.add(incoming4);
            box.add(guide5);
            box.add(incoming5);
            box.add(guide6);
            box.add(incoming6);
            box.add(button);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modificar();
                }
            });
            JPanel panel = new JPanel();
            panel.add(box);
            crearFrame.add(panel);
            crearFrame.setSize(600, 500);
            crearFrame.setVisible(true);
        }
    }

    class ListarUsuariosListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JFrame framel = new JFrame("Usuarios");
            JTextArea incoming = new JTextArea(15, 60);
            incoming.setLineWrap(true);
            incoming.setWrapStyleWord(true);
            incoming.setEditable(false);
            JScrollPane scroller = new JScrollPane(incoming);
            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            JPanel panel = new JPanel();
            panel.add(scroller);
            framel.add(panel);
            incoming.append(MotoRUN.getUsers().toString());
            framel.setSize(800, 400);
            framel.setVisible(true);
        }
    }

    class ConsultarMotoListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JLabel guide = new JLabel("Por favor ingrese el codigo de barras");
            consultaFrame = new JFrame("Consultar moto");
            Box box = new Box(BoxLayout.Y_AXIS);
            incomingC = new JTextField(20);
            incomingC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    consultas = incomingC.getText();
                    try {
                        JOptionPane.showMessageDialog(consultaFrame, MotoRUN.getMotos().get(Long.parseLong(consultas)));
                    } catch (Exception z) {
                        JOptionPane.showMessageDialog(consultaFrame, "El codigo es incorrecto");
                    }
                }
            });
            box.add(guide);
            box.add(incomingC);
            JPanel panel = new JPanel();
            panel.add(box);
            consultaFrame.add(panel);
            consultaFrame.setSize(400, 200);
            consultaFrame.setVisible(true);
        }
    }

    class BorrarMotoListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            consultaFrame = new JFrame("Borrar moto");
            JLabel guide = new JLabel("Por favor ingrese el barCode: ");
            Box box = new Box(BoxLayout.Y_AXIS);
            incomingC = new JTextField(20);
            incomingC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    consultas = incomingC.getText();
                    try {
                        long x = Long.parseLong(consultas);
                        if (MotoRUN.getMotos().get(x) == null)
                            JOptionPane.showMessageDialog(consultaFrame, "no existe esta moto");
                        else {
                            int i = MotoRUN.getStations().get(MotoRUN.getMotos().get(x).getLocation()).getMotos();
                            MotoRUN.getStations().get(MotoRUN.getMotos().get(x).getLocation()).setMotos(--i);
                            JOptionPane.showMessageDialog(consultaFrame, "Moto removida :\n"
                                    + MotoRUN.getMotos().remove(x));
                            MotoRUN.saveMotos();
                            MotoRUN.saveStations();
                        }
                    } catch (Exception z) {
                        JOptionPane.showMessageDialog(consultaFrame, "El bar code es incorrecto");
                    }
                }
            });
            box.add(guide);
            box.add(incomingC);
            JPanel panel = new JPanel();
            panel.add(box);
            consultaFrame.add(panel);
            consultaFrame.setSize(400, 200);
            consultaFrame.setVisible(true);
        }

    }

    class ConsultarUsuarioListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JLabel guide = new JLabel("Por favor ingrese la cedula: ");
            consultaFrame = new JFrame("Consultar Usuario");
            Box box = new Box(BoxLayout.Y_AXIS);
            incomingC = new JTextField(20);
            incomingC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    consultas = incomingC.getText();
                    try {
                        if (MotoRUN.getUsers().get(Integer.parseInt(consultas)) == null)
                            JOptionPane.showMessageDialog(consultaFrame, "no existe");
                        else
                            JOptionPane.showMessageDialog(consultaFrame, MotoRUN.getUsers().get(Integer.parseInt(consultas)));
                    } catch (Exception z) {
                        JOptionPane.showMessageDialog(consultaFrame, "El codigo es incorrecto");
                    }
                }
            });
            box.add(guide);
            box.add(incomingC);
            JPanel panel = new JPanel();
            panel.add(box);
            consultaFrame.add(panel);
            consultaFrame.setSize(400, 200);
            consultaFrame.setVisible(true);
        }

    }

    class BorrarUsuarioListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            consultaFrame = new JFrame("Borrar Usuario");
            JLabel guide = new JLabel("Por favor ingrese la cedula: ");
            Box box = new Box(BoxLayout.Y_AXIS);
            incomingC = new JTextField(20);
            incomingC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    consultas = incomingC.getText();
                    try {
                        if (MotoRUN.getUsers().get(Integer.parseInt(consultas)) == null)
                            JOptionPane.showMessageDialog(consultaFrame, "no existe este usuario");
                        else {
                            JOptionPane.showMessageDialog(consultaFrame, "Usuario removido :\n"
                                    + MotoRUN.getUsers().remove(Integer.parseInt(consultas)));
                            MotoRUN.saveUsers();
                        }
                    } catch (Exception z) {
                        JOptionPane.showMessageDialog(consultaFrame, "La cedula es incorrecto");
                    }
                }
            });
            box.add(guide);
            box.add(incomingC);
            JPanel panel = new JPanel();
            panel.add(box);
            consultaFrame.add(panel);
            consultaFrame.setSize(400, 200);
            consultaFrame.setVisible(true);
        }

    }

    class ConsultarEstacionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel guide = new JLabel("Por favor ingrese el ID: ");
            consultaFrame = new JFrame("Consultar Estacion");
            Box box = new Box(BoxLayout.Y_AXIS);
            incomingC = new JTextField(20);
            incomingC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    consultas = incomingC.getText();
                    if (MotoRUN.getStations().get((consultas)) == null)
                        JOptionPane.showMessageDialog(consultaFrame, " no existe ");
                    else
                        JOptionPane.showMessageDialog(consultaFrame, MotoRUN.getStations().get(consultas));
                }
            });
            box.add(guide);
            box.add(incomingC);
            JPanel panel = new JPanel();
            panel.add(box);
            consultaFrame.add(panel);
            consultaFrame.setSize(400, 200);
            consultaFrame.setVisible(true);
        }
    }

    class BorrarEstacionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            consultaFrame = new JFrame("Borrar Estacion");
            JLabel guide = new JLabel("Por favor ingrese el ID: ");
            Box box = new Box(BoxLayout.Y_AXIS);
            incomingC = new JTextField(20);
            incomingC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    consultas = incomingC.getText();
                    if (MotoRUN.getStations().get(consultas) == null)
                        JOptionPane.showMessageDialog(consultaFrame, "no existe esta estacion");
                    else {
                        JOptionPane.showMessageDialog(consultaFrame, "Estacion removida :\n"
                                + MotoRUN.getStations().remove(consultas));
                        MotoRUN.saveStations();
                    }
                }
            });
            box.add(guide);
            box.add(incomingC);
            JPanel panel = new JPanel();
            panel.add(box);
            consultaFrame.add(panel);
            consultaFrame.setSize(400, 200);
            consultaFrame.setVisible(true);
        }

    }

    class ModificarEstacionListener implements ActionListener {
        JTextField incoming;
        JTextField incoming2;
        JTextField incoming3;
        JTextField incoming4;
        JTextField incoming5;
        JTextField incoming6;

        public void modificar() {
            Station station2 = MotoRUN.getStations().remove(incoming.getText());
            if (station2 != null) {
                try {
                    station2.setCapacity(Integer.parseInt(incoming4.getText()));
                    station2.setName(incoming3.getText());
                    station2.setId(incoming2.getText());
                    station2.setUbicacion(incoming5.getText());
                    MotoRUN.getStations().put(incoming2.getText(), station2);
                    MotoRUN.saveStations();
                    JOptionPane.showMessageDialog(crearFrame, "Estacion modificada :\n" + station2);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(crearFrame, "La capacidad es incorrecta");
                }
            } else
                JOptionPane.showMessageDialog(crearFrame, "La estacion no existe");
        }

        public void actionPerformed(ActionEvent e) {
            crearFrame = new JFrame("Modificar Estacion");
            JLabel guide = new JLabel("Por favor ingrese el ID");
            incoming = new JTextField(20);
            JLabel guide2 = new JLabel("Por favor ingrese el nuevo ID de la estacion");
            incoming2 = new JTextField(20);
            JLabel guide3 = new JLabel("Por favor ingrese el nuevo nombre de la Estacion");
            incoming3 = new JTextField(20);
            JLabel guide4 = new JLabel("Por favor ingrese la nueva capacidad de la Estacion");
            incoming4 = new JTextField(20);
            JLabel guide5 = new JLabel("Por favor ingrese la nueva ubicaci�n de la estaci�n");
            incoming5 = new JTextField(20);
            JButton button = new JButton("Enter");
            Box box = new Box(BoxLayout.Y_AXIS);
            box.add(guide);
            box.add(incoming);
            box.add(guide2);
            box.add(incoming2);
            box.add(guide3);
            box.add(incoming3);
            box.add(guide4);
            box.add(incoming4);
            box.add(guide5);
            box.add(incoming5);
            box.add(button);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    modificar();
                }
            });
            JPanel panel = new JPanel();
            panel.add(box);
            crearFrame.add(panel);
            crearFrame.setSize(600, 500);
            crearFrame.setVisible(true);
        }
    }

    class CrearEstacionListener implements ActionListener {
        JTextField incoming;
        JTextField incoming3;
        JTextField incoming4;
        JTextField incoming5;
        JTextField incoming6;

        public void crear() {
            Station station2 = MotoRUN.getStations().get(incoming.getText());
            if (station2 == null) {
                try {
                    station2 = new Station(incoming.getText(), incoming3.getText(), incoming5.getText(), Integer.parseInt(incoming4.getText()));
                    MotoRUN.getStations().put(incoming.getText(), station2);
                    MotoRUN.saveStations();
                    JOptionPane.showMessageDialog(crearFrame, "Estacion creada :\n" + station2);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(crearFrame, "La capacidad es incorrecta");
                }
            } else
                JOptionPane.showMessageDialog(crearFrame, "La estacion ya existe");
        }

        public void actionPerformed(ActionEvent e) {
            crearFrame = new JFrame("Crear Estacion");
            JLabel guide = new JLabel("Por favor ingrese el ID");
            incoming = new JTextField(20);
            JLabel guide3 = new JLabel("Por favor ingrese el nombre de la Estacion");
            incoming3 = new JTextField(20);
            JLabel guide4 = new JLabel("Por favor ingrese la capacidad de la Estacion");
            incoming4 = new JTextField(20);
            JLabel guide5 = new JLabel("Por favor ingrese la ubicaci�n de la estaci�n");
            incoming5 = new JTextField(20);
            JButton button = new JButton("Enter");
            Box box = new Box(BoxLayout.Y_AXIS);
            box.add(guide);
            box.add(incoming);
            box.add(guide3);
            box.add(incoming3);
            box.add(guide4);
            box.add(incoming4);
            box.add(guide5);
            box.add(incoming5);
            box.add(button);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    crear();
                }
            });
            JPanel panel = new JPanel();
            panel.add(box);
            crearFrame.add(panel);
            crearFrame.setSize(400, 500);
            crearFrame.setVisible(true);
        }
    }

    class ConsultarPrestamoListener implements ActionListener {
        JTextArea incoming;

        public ConsultarPrestamoListener() {
            incoming = new JTextArea(15, 60);
        }

        public void mostrar(ArrayList<Loan> loan) {
            incoming.setText(loan.toString());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel guide = new JLabel("Por favor ingrese la cedula del usuario: ");
            JLabel guide2 = new JLabel("\n");
            consultaFrame = new JFrame("Consultar Prestamos");
            Box box = new Box(BoxLayout.Y_AXIS);
            incomingC = new JTextField(20);
            incomingC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    consultas = incomingC.getText();
                    try {
                        int i = Integer.parseInt(consultas);
                        if (MotoRUN.getLoans().get(i) == null)
                            JOptionPane.showMessageDialog(consultaFrame, "El usuario no tiene prestamos");
                        else
                            mostrar(MotoRUN.getLoans().get(i));
                    } catch (Exception z) {
                        JOptionPane.showMessageDialog(consultaFrame, "La cedula es incorrecta");
                    }
                }
            });
            incoming.setLineWrap(true);
            incoming.setWrapStyleWord(true);
            incoming.setEditable(false);
            JScrollPane scroller = new JScrollPane(incoming);
            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            box.add(guide);
            box.add(incomingC);
            box.add(guide2);
            box.add(guide2);
            JPanel panel = new JPanel();
            box.add(scroller);
            panel.add(box);
            consultaFrame.add(panel);
            consultaFrame.setSize(800, 700);
            consultaFrame.setVisible(true);
        }
    }

    public void checkCargaFrame() {
        userFrame = new JFrame("Login");
        Box box = new Box(BoxLayout.Y_AXIS);
        JPanel panel2 = new JPanel();
        JLabel guide = new JLabel("Por favor ingrese su cedula y contrase�a");
        panel2.add(guide);
        userFrame.add(BorderLayout.NORTH, panel2);
        JLabel login = new JLabel("Login:");
        box.add(login);
        JTextField loginF = new JTextField(20);
        box.add(loginF);
        JLabel password = new JLabel("Password:");
        box.add(password);
        JPasswordField passwordF = new JPasswordField(20);
        box.add(passwordF);
        JLabel guide2 = new JLabel("\n");
        box.add(guide2);
        JButton bottom = new JButton("Enter");
        bottom.addActionListener(new EnterUserListener(loginF, passwordF));
        box.add(bottom);
        JPanel panel = new JPanel();
        panel.add(box);
        JButton back = new JButton("Back");
        back.addActionListener(new BackListener(superUserMenu, userFrame));
        userFrame.add(BorderLayout.SOUTH, back);
        userFrame.add(panel);
        userFrame.setVisible(true);
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setSize(300, 300);
        superUserMenu.setVisible(false);
    }

    class EnterUserListener implements ActionListener {
        JTextField login;
        JPasswordField password;

        public EnterUserListener(JTextField a, JPasswordField b) {
            login = a;
            password = b;
        }

        public void actionPerformed(ActionEvent e) {
            int cedula;
            try {
                cedula = Integer.parseInt(login.getText());
            } catch (NumberFormatException z) {
                cedula = 0;
            }
            String pass = String.valueOf(password.getPassword());
            password.setText("");
            password.requestFocus();
            try {
                int type = MotoRUN.checkUserLogin(cedula, pass, false);
                if (type > 0 && type < 4) {
                    userCedula = cedula;
                    User user = MotoRUN.getUsers().get(cedula);
                    JOptionPane.showMessageDialog(userFrame, "Binevenodo " + user.getNombre() +
                            ", Aqui esta tu perfil:\n" + user);
                    createCarga();
                } else
                    JOptionPane.showMessageDialog(userFrame, "Password incorrect");
            } catch (IllegalArgumentException ee) {
                JOptionPane.showMessageDialog(userFrame, ee.getMessage());
            }
        }
    }

    public void createCarga() {
        cargaFrame = new JFrame("Carga de Moto");
        Box box = new Box(BoxLayout.Y_AXIS);
        JLabel guide = new JLabel("Ingrese la estacion hacia donde se dirige:");
        box.add(guide);
        JLabel guide2 = new JLabel("\n");
        box.add(guide2);
        for (Station x : MotoRUN.getStations()) {
            if (!x.getId().equals(station) && !x.getId().equals("ET") && !x.getId().equals("TM")) {
                JButton aux = new JButton(x.getName());
                aux.addActionListener(new DestinoListener(x.getId()));
                box.add(aux);
            }
        }
        JPanel panel = new JPanel();
        panel.add(box);
        cargaFrame.add(panel);
        cargaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton back = new JButton("Back");
        back.addActionListener(new BackListener(superUserMenu, cargaFrame));
        cargaFrame.add(BorderLayout.SOUTH, back);
        cargaFrame.setSize(300, 300);
        userFrame.setVisible(false);
        cargaFrame.setVisible(true);
    }

    class DestinoListener implements ActionListener {
        String id;

        public DestinoListener(String x) {
            id = x;
        }

        public void actionPerformed(ActionEvent e) {
            try {
                JOptionPane.showMessageDialog(cargaFrame, MotoRUN.cargar(userCedula, station, id));
            } catch (IllegalArgumentException ee) {
                JOptionPane.showMessageDialog(cargaFrame, ee.getMessage());
            }
            superUserMenu.setVisible(true);
            cargaFrame.setVisible(false);
        }
    }

    public void checkDescargaFrame() {
        userFrame = new JFrame("Login");
        Box box = new Box(BoxLayout.Y_AXIS);
        JPanel panel2 = new JPanel();
        JLabel guide = new JLabel("Por favor ingrese su cedula");
        panel2.add(guide);
        userFrame.add(BorderLayout.NORTH, panel2);
        JLabel login = new JLabel("Login:");
        box.add(login);
        JTextField loginF = new JTextField(20);
        box.add(loginF);
        JLabel guide3 = new JLabel("�La moto esta en malas condiciones?");
        box.add(guide3);
        JCheckBox check = new JCheckBox();
        box.add(check);
        JLabel guide2 = new JLabel("\n");
        box.add(guide2);
        JButton bottom = new JButton("Enter");
        bottom.addActionListener(new EnterUserListener2(loginF, check));
        box.add(bottom);
        JPanel panel = new JPanel();
        panel.add(box);
        JButton back = new JButton("Back");
        back.addActionListener(new BackListener(superUserMenu, userFrame));
        userFrame.add(BorderLayout.SOUTH, back);
        userFrame.add(panel);
        userFrame.setVisible(true);
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setSize(500, 300);
        superUserMenu.setVisible(false);
    }

    class EnterUserListener2 implements ActionListener {
        JTextField login;
        JCheckBox check;

        EnterUserListener2(JTextField logi, JCheckBox boxi) {
            login = logi;
            check = boxi;
        }

        public void actionPerformed(ActionEvent e) {
            int cedula;
            try {
                cedula = Integer.parseInt(login.getText());
            } catch (NumberFormatException z) {
                cedula = 0;
            }
            try {
                String aux;
                if (check.isSelected())
                    aux = MotoRUN.descargar(cedula, station, true);
                else
                    aux = MotoRUN.descargar(cedula, station, false);

                JOptionPane.showMessageDialog(userFrame, aux);
            } catch (IllegalArgumentException ee) {
                JOptionPane.showMessageDialog(userFrame, ee.getMessage());
            }
            superUserMenu.setVisible(true);
            userFrame.setVisible(false);
        }
    }

    class HistogramaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame framel = new JFrame("Histograma");
            JTextArea incoming = new JTextArea(15, 60);
            incoming.setLineWrap(true);
            incoming.setWrapStyleWord(true);
            incoming.setEditable(false);
            JScrollPane scroller = new JScrollPane(incoming);
            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            JPanel panel = new JPanel();
            panel.add(scroller);
            framel.add(panel);
            Calendar cal = Calendar.getInstance();
            StringBuilder builder = new StringBuilder("06:00-07:00 ");
            StringBuilder builder2 = new StringBuilder("07:00-08:00 ");
            StringBuilder builder3 = new StringBuilder("08:00-09:00 ");
            StringBuilder builder4 = new StringBuilder("09:00-10:00 ");
            StringBuilder builder5 = new StringBuilder("10:00-11:00 ");
            StringBuilder builder6 = new StringBuilder("11:00-12:00 ");
            StringBuilder builder7 = new StringBuilder("12:00-13:00 ");
            StringBuilder builder8 = new StringBuilder("13:00-14:00 ");
            StringBuilder builder9 = new StringBuilder("14:00-15:00 ");
            StringBuilder builder10 = new StringBuilder("15:00-16:00 ");
            StringBuilder builder11 = new StringBuilder("16:00-17:00 ");
            StringBuilder builder12 = new StringBuilder("17:00-18:00 ");
            for (ArrayList<Loan> x : MotoRUN.getLoans()) {
                for (Loan y : x) {
                    cal.setTimeInMillis(y.getTimeA());
                    int i = cal.get(Calendar.HOUR_OF_DAY);
                    switch (i) {
                        case 6:
                            builder.append("*");
                            break;
                        case 7:
                            builder2.append("*");
                            break;
                        case 8:
                            builder3.append("*");
                            break;
                        case 9:
                            builder4.append("*");
                            break;
                        case 10:
                            builder5.append("*");
                            break;
                        case 11:
                            builder6.append("*");
                            break;
                        case 12:
                            builder7.append("*");
                            break;
                        case 13:
                            builder8.append("*");
                            break;
                        case 14:
                            builder8.append("*");
                            break;
                        case 15:
                            builder10.append("*");
                            break;
                        case 16:
                            builder11.append("*");
                            break;
                        case 17:
                            builder12.append("*");
                            break;
                    }
                }
            }
            incoming.append(builder.toString() + "\n");
            incoming.append(builder2.toString() + "\n");
            incoming.append(builder3.toString() + "\n");
            incoming.append(builder4.toString() + "\n");
            incoming.append(builder5.toString() + "\n");
            incoming.append(builder6.toString() + "\n");
            incoming.append(builder7.toString() + "\n");
            incoming.append(builder8.toString() + "\n");
            incoming.append(builder9.toString() + "\n");
            incoming.append(builder10.toString() + "\n");
            incoming.append(builder11.toString() + "\n");
            incoming.append(builder12.toString());
            framel.setSize(800, 400);
            framel.setVisible(true);
        }
    }

    class MasPrestamosListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MaxHeap<UserLoans> heap = new MaxHeap<>(10);
            for (ArrayList<Loan> x : MotoRUN.getLoans()) {
                heap.put(new UserLoans(x.get(0).getUser(), x.size()));
            }
            if (heap.isEmpty())
                JOptionPane.showMessageDialog(administratorFrame, "No hay prestamos :");
            else {
                User user = MotoRUN.getUsers().get((heap.removeMax().getCedula()));
                JOptionPane.showMessageDialog(administratorFrame, "El usuario con mas prestamos es :" + user);
            }
        }
    }

    class DemoraListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MaxHeap<UserLoans> heap = new MaxHeap<>(10);
            long time;
            int n;
            for (ArrayList<Loan> x : MotoRUN.getLoans()) {
                time = 0;
                n = 0;
                for (Loan y : x) {
                    time += (y.getTimeB() - y.getTimeA());
                    n++;
                }
                heap.put(new UserLoans(x.get(0).getUser(), (time / (n * 1000 * 60))));
            }

            if (heap.isEmpty())
                JOptionPane.showMessageDialog(administratorFrame, "No hay prestamos");
            else {
                User user = MotoRUN.getUsers().get((heap.removeMax().getCedula()));
                JOptionPane.showMessageDialog(administratorFrame, "El usuario que mas se demora es:" + user);
            }
        }
    }

    public void createAdministratorFrame() {
        administratorFrame = new JFrame("Administrator");
        Box box = new Box(BoxLayout.Y_AXIS);
        Box box2 = new Box(BoxLayout.Y_AXIS);
        Box box3 = new Box(BoxLayout.Y_AXIS);
        JLabel guide = new JLabel("Ingrese la opcion que desea realizar:");
        JPanel up = new JPanel();
        up.add(guide);
        administratorFrame.add(BorderLayout.NORTH, up);
        GridLayout grid = new GridLayout(1, 3);
        JPanel gridPanel = new JPanel(grid);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JButton crearS = new JButton("Crear Super Usuario");
        box.add(crearS);
        crearS.addActionListener(new CrearUsuarioListener(2));
        JButton borrarU = new JButton("Borrar Usuario");
        box.add(borrarU);
        borrarU.addActionListener(new BorrarUsuarioListener());
        JButton histograma = new JButton("Histograma de prestamos");
        box.add(histograma);
        histograma.addActionListener(new HistogramaListener());
        JButton demora = new JButton("Usuario que m�s se demora");
        box.add(demora);
        demora.addActionListener(new DemoraListener());
        JButton estacion = new JButton("Editar estacion");
        box.add(estacion);
        estacion.addActionListener(new ModificarEstacionListener());
        JButton agregarMoto = new JButton("Agregar Moto");
        box2.add(agregarMoto);
        agregarMoto.addActionListener(new AgregarMotoListener());
        JButton borrarMoto = new JButton("Borrar Moto");
        box2.add(borrarMoto);
        borrarMoto.addActionListener(new BorrarMotoListener());
        JButton consultarUsuario = new JButton("Consultar usuario");
        box2.add(consultarUsuario);
        consultarUsuario.addActionListener(new ConsultarUsuarioListener());
        JButton listarMotos = new JButton("Listar Motos");
        box2.add(listarMotos);
        listarMotos.addActionListener(new ListarMotosListener());
        JButton estacionN = new JButton("Crear estacion");
        box2.add(estacionN);
        estacionN.addActionListener(new CrearEstacionListener());
        JButton prestamos = new JButton("Buscar prestamos de un usuario");
        prestamos.addActionListener(new ConsultarPrestamoListener());
        box3.add(prestamos);
        JButton masPrestamos = new JButton("Usuario con m�s prestamos");
        masPrestamos.addActionListener(new MasPrestamosListener());
        box3.add(masPrestamos);
        JButton listarEstaciones = new JButton("Listar Estaciones");
        box3.add(listarEstaciones);
        listarEstaciones.addActionListener(new ListarEstacionesListener());
        JButton listarUsuarios = new JButton("Listar Usuarios");
        listarUsuarios.addActionListener(new ListarUsuariosListener());
        box3.add(listarUsuarios);
        JButton borrarEstacion = new JButton("Borrar estacion");
        box3.add(borrarEstacion);
        borrarEstacion.addActionListener(new BorrarEstacionListener());
        panel1.add(box);
        panel2.add(box2);
        panel3.add(box3);
        gridPanel.add(panel1);
        gridPanel.add(panel2);
        gridPanel.add(panel3);
        JButton back = new JButton("Back");
        back.addActionListener(new BackListener(inicioFrame, administratorFrame));
        administratorFrame.add(gridPanel);
        administratorFrame.add(BorderLayout.SOUTH, back);
        administratorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        administratorFrame.setSize(700, 310);
        inicioFrame.setVisible(false);
        administratorFrame.setVisible(true);
    }

    class BackListener implements ActionListener {
        JFrame visible;
        JFrame notVisible;

        public BackListener(JFrame a, JFrame b) {
            visible = a;
            notVisible = b;
        }

        public void actionPerformed(ActionEvent e) {
            notVisible.setVisible(false);
            visible.setVisible(true);
        }
    }
}
