package Cinema.classProject;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    JFrame frame2a = new JFrame();
    private JFrame frame;
    private JFrame frame2 = new JFrame();
    private JFrame frame3 = new JFrame();
    private JFrame frame4 = new JFrame();
    private JPasswordField password;
    private JPanel panel;

    public void go() {
        frame = new JFrame();
        Box box = new Box(BoxLayout.Y_AXIS);
        JLabel label = new JLabel("Are you a:");
        JButton optionAdministrator = new JButton("Administrator");
        optionAdministrator.addActionListener(new optionAdministratorListener());
        JButton optionClient = new JButton("Client");
        optionClient.addActionListener(new optionClientListener());
        JButton save = new JButton("Save all");
        save.addActionListener(new SaveListener());
        JPanel panel = new JPanel();
        box.add(label);
        box.add(optionAdministrator);
        box.add(optionClient);
        panel.add(box);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.add(BorderLayout.SOUTH, save);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    class optionClientListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GridLayout grid = new GridLayout(2, 3);
            JPanel panel2 = new JPanel(grid);
            JButton consultFunction = new JButton("Consult functions");
            consultFunction.addActionListener(new consultFunctionListener());
            panel2.add(consultFunction);
            JButton consultCinema = new JButton("Consult cinemas");
            consultCinema.addActionListener(new consultCinemaListener());
            panel2.add(consultCinema);
            JButton consultClient = new JButton("Consult a client");
            consultClient.addActionListener(new consultClientListener());
            panel2.add(consultClient);
            JButton reserve = new JButton("Reserve");
            reserve.addActionListener(new ServiceListener("RESERVE"));
            panel2.add(reserve);
            JButton buy = new JButton("Buy");
            buy.addActionListener(new ServiceListener("BUY"));
            panel2.add(buy);
            JButton create = new JButton("Create a client");
            create.addActionListener(new CreateClientListener());
            panel2.add(create);
            JButton back = new JButton("Back");
            back.addActionListener(new BackListener(frame2, frame));
            frame2.add(BorderLayout.SOUTH, back);
            frame2.getContentPane().add(panel2);
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame2.setSize(500, 400);
            frame2.setVisible(true);
            frame.setVisible(false);
        }
    }

    class CreateClientListener implements ActionListener {
        JFrame frame3;

        public CreateClientListener() {
            frame3 = new JFrame();
            Box box = new Box(BoxLayout.Y_AXIS);
            JPanel panel3 = new JPanel();
            JLabel name = new JLabel("Ingrese su nombre");
            JTextField nField = new JTextField(20);
            box.add(name);
            box.add(nField);
            JLabel surname = new JLabel("Ingrese su apellido");
            JTextField sField = new JTextField(20);
            box.add(surname);
            box.add(sField);
            JLabel id = new JLabel("Ingrese su idNumber");
            JTextField idField = new JTextField(20);
            box.add(id);
            box.add(idField);
            JLabel code = new JLabel("Ingrese su codigo");
            JTextField cField = new JTextField(20);
            box.add(code);
            box.add(cField);
            JLabel email = new JLabel("Ingrese su email");
            JTextField eField = new JTextField(20);
            box.add(email);
            box.add(eField);
            JLabel birthday = new JLabel("Ingrese su fecha de nacimiento");
            JTextField bField = new JTextField(20);
            box.add(birthday);
            box.add(bField);
            JLabel balance = new JLabel("Ingrese la cantidad de dinero destinada a su tarjeta");
            JTextField baField = new JTextField(20);
            box.add(balance);
            box.add(baField);
            JButton create = new JButton("Create");
            create.addActionListener(new CreateListener(nField, sField, idField, cField,
                    eField, bField, baField, frame3));
            box.add(create);
            JButton back = new JButton("Back");
            back.addActionListener(new BackListener(frame3, frame));
            box.add(back);
            panel3.add(box);
            frame3.getContentPane().add(panel3);
            frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame3.setSize(500, 500);
        }

        public void actionPerformed(ActionEvent e) {
            frame3.setVisible(true);
            frame2.setVisible(false);
        }
    }

    class CreateListener implements ActionListener {
        JTextField name;
        JTextField surname;
        JTextField id;
        JTextField code;
        JTextField email;
        JTextField birthday;
        JTextField balance;
        JFrame frame;

        public CreateListener(JTextField name, JTextField surname,
                              JTextField id, JTextField code, JTextField email,
                              JTextField birthday, JTextField balance, JFrame frame) {
            this.name = name;
            this.surname = surname;
            this.id = id;
            this.code = code;
            this.email = email;
            this.birthday = birthday;
            this.balance = balance;
            this.frame = frame;
        }

        public void actionPerformed(ActionEvent e) {
            if (Controller.createClient(name.getText(), surname.getText(), id.getText(),
                    Integer.parseInt(code.getText()), email.getText(), birthday.getText(),
                    Integer.parseInt(balance.getText())))
                JOptionPane.showMessageDialog(frame, "Cliente creado satisfactoriamente");
            else
                JOptionPane.showMessageDialog(frame, "A ocurrido un error, por favor"
                        + " revise sus datos");
        }
    }

    class ServiceListener implements ActionListener {
        JFrame frame3;

        public ServiceListener(String action) {
            frame3 = new JFrame();
            Box box = new Box(BoxLayout.Y_AXIS);
            JPanel panel3 = new JPanel();
            JTextField lfield = new JTextField(20);
            JLabel label = new JLabel("Ingrese su idNumber");
            box.add(label);
            box.add(lfield);
            JLabel code = new JLabel("Ingrese su codigo");
            JTextField cfield = new JTextField(20);
            box.add(code);
            box.add(cfield);
            JLabel function = new JLabel("Ingrese la funcion");
            JTextField ffield = new JTextField(20);
            box.add(function);
            box.add(ffield);
            JLabel chair = new JLabel("Ingrese su silla separando la"
                    + " fila y la columna por una coma");
            JTextField chfield = new JTextField(20);
            box.add(chair);
            box.add(chfield);
            JButton go = new JButton("go");
            go.addActionListener(new GoListener(action, ffield, lfield, cfield, chfield));
            box.add(go);
            JButton back = new JButton("Back");
            back.addActionListener(new BackListener(frame3, frame));
            box.add(back);
            panel3.add(BorderLayout.CENTER, box);
            frame3.getContentPane().add(panel3);
            frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame3.setSize(500, 500);
        }

        public void actionPerformed(ActionEvent e) {
            Controller.printFunctions();
            frame3.setVisible(true);
            frame2.setVisible(false);
        }
    }

    class GoListener implements ActionListener {
        String action;
        JTextField function;
        JTextField id;
        JTextField code;
        JTextField chair;

        public GoListener(String action, JTextField function, JTextField id,
                          JTextField code, JTextField chair) {
            this.action = action;
            this.function = function;
            this.id = id;
            this.code = code;
            this.chair = chair;
        }

        public void actionPerformed(ActionEvent e) {
            boolean aux = Controller.checkService(action, function.getText(), id.getText(),
                    Integer.parseInt(code.getText()), chair.getText());
            if (aux)
                JOptionPane.showMessageDialog(frame3, "YOU " + action + " a chair");
            else {
                JOptionPane.showMessageDialog(frame3, "No se pudo realizar "
                        + "la reserva, revise sus datos o silla");
            }
        }
    }

    class optionAdministratorListener implements ActionListener {
        public optionAdministratorListener() {
            password = new JPasswordField(20);
            password.addActionListener(new passwordListener());
            Box box = new Box(BoxLayout.Y_AXIS);
            JLabel label = new JLabel("Write your password:");
            panel = new JPanel();
            box.add(label);
            box.add(password);
            panel.add(BorderLayout.CENTER, box);
            JButton back = new JButton("Back");
            back.addActionListener(new BackListener(frame2a, frame));
            box.add(back);
            frame2a.getContentPane().add(panel);
            frame2a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame2a.setSize(500, 500);
        }

        public void actionPerformed(ActionEvent e) {
            frame2a.setVisible(true);
            frame.setVisible(false);
        }
    }

    class passwordListener implements ActionListener {
        public passwordListener() {
            GridLayout grid = new GridLayout(2, 3);
            JPanel panel2 = new JPanel(grid);
            JButton consultFunction = new JButton("Consult functions");
            consultFunction.addActionListener(new consultFunctionListener());
            panel2.add(consultFunction);
            JButton consultCinema = new JButton("Consult cinemas");
            consultCinema.addActionListener(new consultCinemaListener());
            panel2.add(consultCinema);
            JButton modifyFunction = new JButton("Modify a function");
            modifyFunction.addActionListener(new modifyFunctionListener());
            panel2.add(modifyFunction);
            JButton modifyCinema = new JButton("Modify a cinema");
            modifyCinema.addActionListener(new modifyCinemaListener());
            panel2.add(modifyCinema);
            JButton consultClient = new JButton("Consult a client");
            consultClient.addActionListener(new consultClientListener());
            panel2.add(consultClient);
            JButton modifyClient = new JButton("Modify a client");
            modifyClient.addActionListener(new modifyClientListener());
            panel2.add(modifyClient);
            JButton back = new JButton("Back");
            back.addActionListener(new BackListener(frame4, frame));
            JPanel panel3 = new JPanel();
            panel3.add(back);
            frame4.getContentPane().add(BorderLayout.CENTER, panel2);
            frame4.getContentPane().add(BorderLayout.SOUTH, panel3);
            frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame4.setSize(550, 550);
        }

        public void actionPerformed(ActionEvent e) {
            String aux = String.valueOf(password.getPassword());
            password.setText("");
            password.requestFocus();
            boolean tmp = Controller.checkAdminPassword(aux, frame2a);
            if (tmp) {
                frame2a.setVisible(false);
                frame4.setVisible(true);
            }
        }
    }

    class consultFunctionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Controller.printFunctions();
        }
    }

    class consultCinemaListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Controller.printCinemas();
        }
    }

    class modifyFunctionListener implements ActionListener {
        JFrame frame3;

        public modifyFunctionListener() {
            frame3 = new JFrame();
            Box box = new Box(BoxLayout.Y_AXIS);
            JPanel panel3 = new JPanel();
            JLabel label = new JLabel("Ingrese el nombre de la funcion a cambiar");
            JTextField ffield = new JTextField(20);
            box.add(label);
            box.add(ffield);
            JLabel name = new JLabel("Ingrese el nuevo nombre");
            JTextField nfield = new JTextField(20);
            box.add(name);
            box.add(nfield);
            JLabel hour = new JLabel("Ingrese la nueva hora");
            JTextField hfield = new JTextField(20);
            box.add(hour);
            box.add(hfield);
            JLabel price = new JLabel("Ingrese el precio");
            JTextField pfield = new JTextField(20);
            box.add(price);
            box.add(pfield);
            JButton modify = new JButton("Modify");
            modify.addActionListener(new ModifyListener(ffield, nfield, hfield, pfield, frame3));
            box.add(modify);
            JButton back = new JButton("Back");
            back.addActionListener(new BackListener(frame3, frame));
            box.add(back);
            panel3.add(BorderLayout.CENTER, box);
            frame3.getContentPane().add(panel3);
            frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame3.setSize(500, 500);
        }

        public void actionPerformed(ActionEvent e) {
            frame3.setVisible(true);
            frame4.setVisible(false);
        }
    }

    class ModifyListener implements ActionListener {
        JTextField name;
        JTextField nName;
        JTextField hour;
        JTextField price;
        JFrame frame;

        public ModifyListener(JTextField name, JTextField nName,
                              JTextField hour, JTextField price, JFrame f) {
            this.name = name;
            this.nName = nName;
            this.hour = hour;
            this.price = price;
            frame = f;
        }

        public void actionPerformed(ActionEvent e) {
            if (Controller.modifyFunction(name.getText(), nName.getText(), hour.getText(),
                    Integer.parseInt(price.getText())))
                JOptionPane.showMessageDialog(frame, "la funcion se ha modificado correctamente");
            else
                JOptionPane.showMessageDialog(frame, "lo sentimos, un error, por favor revisar"
                        + " sus datos");
        }
    }

    class modifyCinemaListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame4, "Para modifiar un cinema debes "
                    + "hacerlo desde el archivo de texto");
        }
    }

    class consultClientListener implements ActionListener {
        JFrame frame3;

        public consultClientListener() {
            frame3 = new JFrame();
            frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame3.setSize(400, 300);
            JPanel panel3 = new JPanel();
            Box box = new Box(BoxLayout.Y_AXIS);
            JLabel label = new JLabel("Ingrese su idNumber");
            JTextField field = new JTextField(20);
            box.add(label);
            box.add(field);
            JLabel clabel = new JLabel("Ingrese su codigo");
            JTextField cfield = new JTextField(20);
            box.add(clabel);
            box.add(cfield);
            JButton advance = new JButton("Advance");
            advance.addActionListener(new AdvanceListener(field, cfield, frame3));
            box.add(advance);
            JButton back = new JButton("Back");
            back.addActionListener(new BackListener(frame3, frame));
            panel3.add(box);
            panel3.add(BorderLayout.SOUTH, back);
            frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame3.setSize(400, 300);
            frame3.getContentPane().add(panel3);
        }

        public void actionPerformed(ActionEvent e) {
            frame3.setVisible(true);
            frame2.setVisible(false);
            frame4.setVisible(false);
        }
    }

    class AdvanceListener implements ActionListener {
        JTextField id;
        JTextField code;
        JTextField nID;
        JTextField nCode;
        JTextField nemail;
        JTextField nBalance;
        JFrame frame;
        String action = "";

        public AdvanceListener(String action, JTextField id, JTextField code, JTextField nID,
                               JTextField nCode, JTextField nemail, JTextField nBalance, JFrame f) {
            this.id = id;
            this.code = code;
            this.nID = nID;
            this.nCode = nCode;
            this.nemail = nemail;
            this.nBalance = nBalance;
            this.action = action;
            frame = f;
        }

        public AdvanceListener(JTextField a, JTextField b, JFrame f) {
            id = a;
            code = b;
            frame = f;
        }

        public void actionPerformed(ActionEvent e) {
            if (action.equals("MODIFY"))
                Controller.checkClientID(id.getText(), Integer.parseInt(code.getText()),
                        nID.getText(), Integer.parseInt(nCode.getText()), nemail.getText(),
                        Integer.parseInt(nBalance.getText()), frame);
            else {
                Controller.checkClientID(id.getText(), Integer.parseInt(code.getText()), frame);
            }
        }
    }

    class modifyClientListener implements ActionListener {
        JFrame frame3;

        public modifyClientListener() {
            frame3 = new JFrame();
            Box box = new Box(BoxLayout.Y_AXIS);
            JPanel panel3 = new JPanel();
            JLabel label = new JLabel("Ingrese su idNumber");
            JTextField lfield = new JTextField(20);
            box.add(label);
            box.add(lfield);
            JLabel code = new JLabel("Ingrese su codigo");
            JTextField cfield = new JTextField(20);
            box.add(code);
            box.add(cfield);
            JLabel newId = new JLabel("Ingrese su nuevo idNumber");
            JTextField nfield = new JTextField(20);
            box.add(newId);
            box.add(nfield);
            JLabel newCode = new JLabel("Ingrese su nuevo codigo");
            JTextField ncfield = new JTextField(20);
            box.add(newCode);
            box.add(ncfield);
            JLabel email = new JLabel("Ingrese su nuevo correo");
            JTextField efield = new JTextField(20);
            box.add(email);
            box.add(efield);
            JLabel balance = new JLabel("Ingrese la cantidad de dinero que va a a�adir a su cuenta");
            JTextField bfield = new JTextField(20);
            box.add(balance);
            box.add(bfield);
            JButton advance = new JButton("Advance");
            advance.addActionListener(new AdvanceListener("MODIFY", lfield, cfield, nfield,
                    ncfield, efield, bfield, frame3));
            box.add(advance);
            JButton back = new JButton("Back");
            back.addActionListener(new BackListener(frame3, frame));
            box.add(back);
            panel3.add(BorderLayout.CENTER, box);
            frame3.getContentPane().add(panel3);
            frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame3.setSize(500, 500);
        }

        public void actionPerformed(ActionEvent e) {
            frame3.setVisible(true);
            frame4.setVisible(false);
        }
    }

    class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Controller.saveData();
        }
    }

    class BackListener implements ActionListener {
        JFrame frameA;
        JFrame frameB;

        public BackListener(JFrame frameA, JFrame frameB) {
            this.frameA = frameA;
            this.frameB = frameB;
        }

        public void actionPerformed(ActionEvent e) {
            frameA.setVisible(false);
            frameB.setVisible(true);
        }
    }
}