import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.GenericArrayType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

public class GloboGymInterface {
    private JFrame frame;
    private JPanel panel;
    private JButton loginButton;
    private JComboBox<String> comboBox;
    private GloboGym globoGym;

    public GloboGymInterface(){
        showRoleSelection();
        globoGym = new GloboGym();
    }
    public GloboGymInterface(GloboGym gym){
        showRoleSelection();
        this.globoGym = gym;
    }

    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            String selectedRole = (String) comboBox.getSelectedItem();
            frame.getContentPane().removeAll();
            frame.revalidate();
            frame.repaint();

            if (selectedRole.equals("Klubowicz")) {
                showLoginScreen("Klubowicz");
            } else if (selectedRole.equals("Pracownik")) {
                showLoginScreen("Pracownik");
            } else if (selectedRole.equals("Manager")) {
                showLoginScreen("Manager");
            }
        }
    }

    private void showRoleSelection(){
        frame = new JFrame("GloboGym");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        String[] options = {"Klubowicz", "Pracownik", "Manager"};
        comboBox = new JComboBox<>(options);

        loginButton = new JButton("Wybierz");
        loginButton.addActionListener(new ButtonClickListener());

        panel.add(comboBox);
        panel.add(loginButton);

        frame.setVisible(true);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }
    private void showLoginScreen(String role){
        JLabel loginLabel = new JLabel("Login:");
        loginLabel.setLayout(new GridLayout(3,2));
        JTextField loginField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Hasło:");
        JPasswordField passwordField = new JPasswordField(20);
        JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
        JButton loginButton = new JButton("Zaloguj");
        JButton backButton = new JButton("Wróć");
        buttonsPanel.add(loginButton);
        buttonsPanel.add(backButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                String password = new String(passwordField.getPassword());

                boolean loginSuccessful = false;

                if (role.equals("Klubowicz")) {
                    loginSuccessful = globoGym.loginKlubowicz(login, password);
                } else if (role.equals("Pracownik")) {
                    loginSuccessful = globoGym.loginPracownik(login, password);
                } else if (role.equals("Manager")) {
                    loginSuccessful = globoGym.loginManager(login, password);
                }

                if (loginSuccessful && role.equals("Klubowicz")) {
                    JOptionPane.showMessageDialog(frame, "Zalogowano jako " + role);
                    showKlubowicOptions(login);
                } else if (loginSuccessful && role.equals("Pracownik")) {
                    JOptionPane.showMessageDialog(frame, "Zalogowano jako " + role);
                    showPracownikOptions();
                } else if (loginSuccessful && role.equals("Manager")) {
                    JOptionPane.showMessageDialog(frame, "Zalogowano jako " + role);
                    showManagerOptions();
                } else {
                    JOptionPane.showMessageDialog(frame, "Nieprawidłowy login lub hasło");
                }

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                showRoleSelection();
            }
        });

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3,2));
        loginPanel.add(loginLabel);
        loginPanel.add(loginField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(buttonsPanel);

        frame.setVisible(true);
        frame.getContentPane().add(loginPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    private void showKlubowicOptions(String login){
        frame.getContentPane().removeAll();

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton zapiszSieButton = new JButton("Zapisz się na zajęcia");
        zapiszSieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(zapiszSieButton);

        JButton wyswietlZajeciaButton = new JButton("Wyświetl zajęcia");
        wyswietlZajeciaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(wyswietlZajeciaButton);

        JButton wypiszSieButton = new JButton("Wypisz się z zajęć");
        wypiszSieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(wypiszSieButton);

        JButton zobaczStanKontaButton = new JButton("Zobacz stan konta");
        zobaczStanKontaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(zobaczStanKontaButton);

        JButton oplacKarnetButton = new JButton("Opłać karnet");
        oplacKarnetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(oplacKarnetButton);

        JButton zmienDaneButton = new JButton("Zmień dane osobowe");
        zmienDaneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(zmienDaneButton);

        JButton wyswietlStatystykiButton = new JButton("Wyświetl statystyki");
        wyswietlStatystykiButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(wyswietlStatystykiButton);

        JButton dodajZdjecieButton = new JButton("Dodaj zdjęcie profilowe");
        dodajZdjecieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(dodajZdjecieButton);

        JButton backButton = new JButton("Wróć");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        zapiszSieButton.addActionListener(e -> {
            frame.getContentPane().removeAll();

            JFrame newMemberFrame = new JFrame("Zapisz się na zajęcia");
            newMemberFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newMemberFrame.setSize(400, 300);
            frame.setLayout(new BorderLayout());

            JPanel inputPanel = new JPanel(new GridLayout(6, 2));

            JLabel nameLabel = new JLabel("Imię:");
            JTextField nameTextField = new JTextField(20);

            JLabel surnameLabel = new JLabel("Nazwisko:");
            JTextField surnameTextField = new JTextField(20);

            JLabel birthdateLabel = new JLabel();
            birthdateLabel.setLayout(new BorderLayout());
            JLabel label1 = new JLabel("Data urodzenia");
            JLabel label2 = new JLabel("(RRRR-MM-DD):");
            birthdateLabel.add(BorderLayout.NORTH, label1);
            birthdateLabel.add(BorderLayout.CENTER, label2);
            birthdateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JTextField birthdateTextField = new JTextField(20);

            JLabel classDateLabel = new JLabel("Data zajęć");
            JTextField classDateTextField = new JTextField(20);

            JLabel classNameLabel = new JLabel("Nazwa zajęć");
            JTextField classNameTextField = new JTextField(20);

            JButton addButton = new JButton("Dodaj");
            JButton backButton1 = new JButton("Wróć");
            JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
            buttonsPanel.add(addButton);
            buttonsPanel.add(backButton1);

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameTextField.getText();
                    String surname = surnameTextField.getText();
                    String birthdateString = birthdateTextField.getText();
                    String clasdateString = classDateTextField.getText();
                    String className = classNameTextField.getText();

                    Date birthdate = parseDate(birthdateString);
                    Date classDate = parseDate(clasdateString);

                    Klubowicz klubowicz = new Klubowicz(name, surname, birthdate);

                    String result;

                    if (birthdate != null && classDate != null) {
                        result = globoGym.addKlubowiczToClasses(klubowicz, classDate, className);
                        JOptionPane.showMessageDialog(frame, result);
                        if(result.equals("Zapisano na zajęcia pomyślnie")){
                            showKlubowicOptions(login);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Błąd: Nieprawidłowy format daty!");
                    }
                }
            });

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showKlubowicOptions(login);
                }
            });

            inputPanel.add(nameLabel);
            inputPanel.add(nameTextField);
            inputPanel.add(surnameLabel);
            inputPanel.add(surnameTextField);
            inputPanel.add(birthdateLabel);
            inputPanel.add(birthdateTextField);
            inputPanel.add(classDateLabel);
            inputPanel.add(classDateTextField);
            inputPanel.add(classNameLabel);
            inputPanel.add(classNameTextField);

            frame.add(inputPanel, BorderLayout.CENTER);
            frame.add(buttonsPanel, BorderLayout.SOUTH);

            frame.getContentPane().add(inputPanel);
            frame.setPreferredSize(new Dimension(300, 400));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
        });

        wyswietlZajeciaButton.addActionListener(e -> {
            displaySchedule(globoGym.getSchedule());
        });

        wypiszSieButton.addActionListener(e -> {
            frame.getContentPane().removeAll();

            JFrame newMemberFrame = new JFrame("Wypisz się z zajęć");
            newMemberFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newMemberFrame.setSize(400, 300);
            frame.setLayout(new BorderLayout());

            JPanel inputPanel = new JPanel(new GridLayout(6, 2));

            JLabel nameLabel = new JLabel("Imię:");
            JTextField nameTextField = new JTextField(20);

            JLabel surnameLabel = new JLabel("Nazwisko:");
            JTextField surnameTextField = new JTextField(20);

            JLabel birthdateLabel = new JLabel();
            birthdateLabel.setLayout(new BorderLayout());
            JLabel label1 = new JLabel("Data urodzenia");
            JLabel label2 = new JLabel("(RRRR-MM-DD):");
            birthdateLabel.add(BorderLayout.NORTH, label1);
            birthdateLabel.add(BorderLayout.CENTER, label2);
            birthdateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JTextField birthdateTextField = new JTextField(20);

            JLabel classDateLabel = new JLabel("Data zajęć");
            JTextField classDateTextField = new JTextField(20);

            JLabel classNameLabel = new JLabel("Nazwa zajęć");
            JTextField classNameTextField = new JTextField(20);

            JButton removeButton = new JButton("Usuń");
            JButton backButton1 = new JButton("Wróć");
            JPanel buttonsPanel = new JPanel(new GridLayout(1,3));
            buttonsPanel.add(removeButton);
            buttonsPanel.add(backButton1);


            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameTextField.getText();
                    String surname = surnameTextField.getText();
                    String birthdateString = birthdateTextField.getText();
                    String clasdateString = classDateTextField.getText();
                    String className = classNameTextField.getText();

                    Date birthdate = parseDate(birthdateString);
                    Date classDate = parseDate(clasdateString);

                    Klubowicz klubowicz = new Klubowicz(name, surname, birthdate);

                    String result;

                    if (birthdate != null && classDate != null) {
                        result = globoGym.removeKlubowiczFromClasses(klubowicz, classDate, className);
                        JOptionPane.showMessageDialog(frame, result);
                        if(result.equals("Usunięto pomyślnie")){
                            showPracownikOptions();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Błąd: Nieprawidłowy format daty!");
                    }
                }
            });

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showKlubowicOptions(login);
                }
            });

            inputPanel.add(nameLabel);
            inputPanel.add(nameTextField);
            inputPanel.add(surnameLabel);
            inputPanel.add(surnameTextField);
            inputPanel.add(birthdateLabel);
            inputPanel.add(birthdateTextField);
            inputPanel.add(classDateLabel);
            inputPanel.add(classDateTextField);
            inputPanel.add(classNameLabel);
            inputPanel.add(classNameTextField);

            frame.add(inputPanel, BorderLayout.CENTER);
            frame.add(buttonsPanel, BorderLayout.SOUTH);

            frame.getContentPane().add(inputPanel);
            frame.setPreferredSize(new Dimension(300, 400));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
        });

        zobaczStanKontaButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            JTextField loginField;
            JPasswordField passwordField;

            JFrame balanceFrame = new JFrame("Wypisz się z zajęć");
            balanceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            balanceFrame.setSize(400, 200);
            balanceFrame.setLocationRelativeTo(null);
            balanceFrame.setLayout(new FlowLayout());

            JLabel loginLabel = new JLabel("Login:");
            loginField = new JTextField(15);

            JLabel passwordLabel = new JLabel("Hasło:");
            passwordField = new JPasswordField(15);

            JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
            JButton showBalanceButton = new JButton("Zobacz Stan Konta");
            buttonsPanel.add(showBalanceButton);
            buttonsPanel.add(backButton);

            showBalanceButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String login = loginField.getText();
                    String password = new String(passwordField.getPassword());

                    if (login.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(balanceFrame, "Proszę wprowadzić login i hasło.", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
                    } else {
                        boolean loggedIn = globoGym.loginKlubowicz(login, password); // Przykładowe logowanie w GloboGym

                        if (loggedIn) {
                            float stanKonta = globoGym.getStanKontaKlubowicza(login); // Przykładowe pobranie stanu konta
                            JOptionPane.showMessageDialog(balanceFrame, "Stan konta klubowicza: " + stanKonta, "Stan konta", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(balanceFrame, "Błędne dane logowania.", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            balanceFrame.add(loginLabel);
            balanceFrame.add(loginField);
            balanceFrame.add(passwordLabel);
            balanceFrame.add(passwordField);
            balanceFrame.add(zobaczStanKontaButton);

            balanceFrame.setVisible(true);



//            JFrame frame = new JFrame("Stan konta");
//            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            frame.setLayout(new GridLayout(4, 2));
//
//            JLabel nameLabel = new JLabel("Imię:");
//            JTextField nameField = new JTextField();
//
//            JLabel surnameLabel = new JLabel("Nazwisko:");
//            JTextField surnameField = new JTextField();
//
//            JLabel birthdateLabel = new JLabel("Data urodzenia (dd-MM-yyyy):");
//            JTextField birthdateField = new JTextField();
//
//            JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
//            JButton searchButton = new JButton("Szukaj");
//            buttonsPanel.add(searchButton);
//            buttonsPanel.add(backButton);
//
//            searchButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String name = nameField.getText();
//                    String surname = surnameField.getText();
//                    String birthdateStr = birthdateField.getText();
//
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                    Date birthdate = null;
//
//                    try {
//                        birthdate = dateFormat.parse(birthdateStr);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//
//                    GloboGym globoGym = new GloboGym(); // Tworzenie instancji klasy GloboGym (zależnie od Twojej implementacji)
//                    Klubowicz foundKlubowicz = globoGym.findKlubowicz(name, surname, birthdate);
//
//                    if (foundKlubowicz != null) {
//                        float balance = foundKlubowicz.getBalance();
//                        JOptionPane.showMessageDialog(frame, "Stan konta dla " + name + " " + surname + ": " + balance);
//                    } else {
//                        JOptionPane.showMessageDialog(frame, "Klubowicz o podanych danych nie został znaleziony.");
//                    }
//                }
//            });
//
//            frame.add(nameLabel);
//            frame.add(nameField);
//            frame.add(surnameLabel);
//            frame.add(surnameField);
//            frame.add(birthdateLabel);
//            frame.add(birthdateField);
//            frame.add(searchButton);
//
//            frame.pack();
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);

        });

        oplacKarnetButton.addActionListener(e -> {
//            String password = new String(passwordField.getPassword());
//
//            if (login.isEmpty() || password.isEmpty()) {
//                JOptionPane.showMessageDialog(balanceFrame, "Proszę wprowadzić login i hasło.", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
//            } else {
//                boolean loggedIn = globoGym.loginKlubowicz(login, password);
//
//                if (loggedIn) {
//                    float stanKonta = globoGym.getStanKontaKlubowicza(login);
//
//                    if (stanKonta > 0) {
//                        JOptionPane.showMessageDialog(balanceFrame, "Karnet został już opłacony.", "Opłacanie Karnetu", JOptionPane.INFORMATION_MESSAGE);
//                    } else {
//                        globoGym.oplacKarnet(login);
//                        JOptionPane.showMessageDialog(balanceFrame, "Karnet został opłacony.", "Opłacanie Karnetu", JOptionPane.INFORMATION_MESSAGE);
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(balanceFrame, "Błędne dane logowania.", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
//                }
//            }
        });

        zmienDaneButton.addActionListener(e -> {

        });

        wyswietlStatystykiButton.addActionListener(e -> {

        });

        dodajZdjecieButton.addActionListener(e -> {

        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                showRoleSelection();
            }
        });

        optionsPanel.add(zapiszSieButton);
        optionsPanel.add(wyswietlZajeciaButton);
        optionsPanel.add(wypiszSieButton);
        optionsPanel.add(zobaczStanKontaButton);
        optionsPanel.add(oplacKarnetButton);
        optionsPanel.add(zmienDaneButton);
        optionsPanel.add(wyswietlStatystykiButton);
        optionsPanel.add(dodajZdjecieButton);
        optionsPanel.add(backButton);

        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        frame.getContentPane().add(optionsPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    private void showPracownikOptions(){
        frame.getContentPane().removeAll();

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton nowyKlubowiczButton = new JButton("Utwórz nowego klubowicza");
        nowyKlubowiczButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(nowyKlubowiczButton);

        JButton dodajZajeciaButton = new JButton("Dodaj nowe zajęcia do grafiku");
        dodajZajeciaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(dodajZajeciaButton);

        JButton edycjaPracownikaButton = new JButton();
        edycjaPracownikaButton.setLayout(new BorderLayout());
        JLabel label1 = new JLabel("Utwórz/zmień pracownika");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel label2 = new JLabel("przypisanego do zajęć");
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        edycjaPracownikaButton.add(BorderLayout.NORTH, label1);
        edycjaPracownikaButton.add(BorderLayout.CENTER, label2);
        edycjaPracownikaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(edycjaPracownikaButton);

        JButton usunZajeciaButton = new JButton("Usuń zajęcia z grafiku");
        usunZajeciaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(usunZajeciaButton);

        JButton dodajUsunKlubowiczaButton = new JButton("Dodaj/usuń klubowicza z zajęć");
        dodajUsunKlubowiczaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(dodajUsunKlubowiczaButton);

        JButton backButton = new JButton("Wróć");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(backButton);

        nowyKlubowiczButton.addActionListener(e -> {
            newMemberFrame(e, "Pracownik");
        });

        dodajZajeciaButton.addActionListener(e -> {
//            frame.getContentPane().removeAll();
//
//            JFrame newMemberFrame = new JFrame("Dodaj nowe zajęcia");
//            newMemberFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            newMemberFrame.setSize(400, 300);
//            frame.setLayout(new BorderLayout());
//
//            JPanel inputPanel = new JPanel(new GridLayout(4, 2));
//
//            JLabel classDateLabel = new JLabel();
//            classDateLabel.setLayout(new BorderLayout());
//            JLabel label3 = new JLabel("Data zajęć");
//            JLabel label4 = new JLabel("(RRRR-MM-DD):");
//            classDateLabel.add(BorderLayout.NORTH, label3);
//            classDateLabel.add(BorderLayout.CENTER, label4);
//            classDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//            JTextField classDateTextField = new JTextField(20);
//
//            JLabel classNameLabel = new JLabel("Nazwa zajęć");
//            JTextField classNameTextField = new JTextField(20);
//
//            JLabel roomLabel = new JLabel("Sala");
//            JTextField roomTextField = new JTextField(20);
//
//            JButton saveButton = new JButton("Zapisz");
//
//            saveButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String dateString = classDateTextField.getText();
//                    String className = classNameTextField.getText();
//                    String room = roomTextField.getText();
//
//                    Date classDate = parseDate(dateString);
//
//                    if (classDate != null) {
//                        globoGym.addClasses(classDate, className, room);
//                        JOptionPane.showMessageDialog(frame, "Dodano zajęcia pomyślnie");
//                        showPracownikOptions();
//                    } else {
//                        JOptionPane.showMessageDialog(frame, "Błąd: Nieprawidłowy format daty!");
//                    }
//                }
//            });
//
//            inputPanel.add(classDateLabel);
//            inputPanel.add(classDateTextField);
//            inputPanel.add(classNameLabel);
//            inputPanel.add(classNameTextField);
//            inputPanel.add(roomLabel);
//            inputPanel.add(roomTextField);
//
//            frame.add(inputPanel, BorderLayout.CENTER);
//            frame.add(saveButton, BorderLayout.SOUTH);
//
//            frame.getContentPane().add(inputPanel);
//            frame.pack();
//            frame.setLocationRelativeTo(null);
//            frame.revalidate();
//            frame.repaint();
//        });
//
//        edycjaPracownikaButton.addActionListener(e -> {
//            frame.getContentPane().removeAll();
//            JFrame newMemberFrame = new JFrame("Edytuj/Dodaj pracownika do zajęć");
//            newMemberFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            newMemberFrame.setSize(400, 300);
//            frame.setLayout(new BorderLayout());
//
//            JButton assignButton = new JButton("Przypisz pracownika do zajęć");
//            assignButton.addActionListener(new AssignButtonListener());
//
//            JButton editButton = new JButton("Edytuj pracownika przypisanego do zajęć");
//            editButton.addActionListener(new EditButtonListener());
//
//            JPanel panel = new JPanel();
//            panel.add(assignButton);
//            panel.add(editButton);
//            newMemberFrame.add(panel);
//
//            newMemberFrame.pack();
//            newMemberFrame.setVisible(true);
//            frame.add(newMemberFrame);
//            frame.pack();
//            frame.setVisible(true);
//
//        });
//
//        usunZajeciaButton.addActionListener(e -> {
//            frame.getContentPane().removeAll();
//
//            JFrame newMemberFrame = new JFrame("Usuń zajęcia");
//            newMemberFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            newMemberFrame.setSize(400, 300);
//            frame.setLayout(new BorderLayout());
//
////            JPanel inputPanel = new JPanel(new GridLayout(4, 2));
//            JPanel inputPanel = new JPanel();
//
//            JLabel classDateLabel = new JLabel();
//            classDateLabel.setLayout(new BorderLayout());
//            JLabel label3 = new JLabel("Data zajęć");
//            JLabel label4 = new JLabel("(RRRR-MM-DD):");
//            classDateLabel.add(BorderLayout.NORTH, label3);
//            classDateLabel.add(BorderLayout.CENTER, label4);
//            classDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//            JTextField classDateTextField = new JTextField(20);
//
//            JLabel classNameLabel = new JLabel("Nazwa zajęć");
//            JTextField classNameTextField = new JTextField(20);
//
//            JLabel roomLabel = new JLabel("Sala");
//            JTextField roomTextField = new JTextField(20);
//
//            JButton removeButton = new JButton("Usuń");
//
//            removeButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String dateString = classDateTextField.getText();
//                    String className = classNameTextField.getText();
//                    String room = roomTextField.getText();
//
//                    Date classDate = parseDate(dateString);
//
//                    if (classDate != null) {
//                        globoGym.removeClasses(classDate, className, room);
//                        JOptionPane.showMessageDialog(frame, "Usunięto zajęcia pomyślnie");
//                        showPracownikOptions();
//                    } else {
//                        JOptionPane.showMessageDialog(frame, "Błąd: Nieprawidłowy format daty!");
//                    }
//                }
//            });
//
//            inputPanel.add(classDateLabel);
//            inputPanel.add(classDateTextField);
//            inputPanel.add(classNameLabel);
//            inputPanel.add(classNameTextField);
//            inputPanel.add(roomLabel);
//            inputPanel.add(roomTextField);
//
//            frame.add(inputPanel, BorderLayout.CENTER);
//            frame.add(removeButton, BorderLayout.SOUTH);
//
//            frame.getContentPane().add(inputPanel);
////            frame.setPreferredSize(new Dimension(300, 400));
//            frame.pack();
//            frame.setLocationRelativeTo(null);
//            frame.revalidate();
//            frame.repaint();
        });

        dodajUsunKlubowiczaButton.addActionListener(e -> {
            addOrRemoveFromClasses(e, "Pracownik");
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                showRoleSelection();
            }
        });

        optionsPanel.setLayout(new GridLayout(5, 1));
        optionsPanel.add(nowyKlubowiczButton);
        optionsPanel.add(dodajZajeciaButton);
        optionsPanel.add(edycjaPracownikaButton);
        optionsPanel.add(usunZajeciaButton);
        optionsPanel.add(dodajUsunKlubowiczaButton);

        optionsPanel.setPreferredSize(new Dimension(300, 400));
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        frame.getContentPane().add(optionsPanel);
        frame.setPreferredSize(new Dimension(300, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    private void showManagerOptions(){
        frame.getContentPane().removeAll();

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton nowyKlubowiczButton = new JButton("Utwórz nowego klubowicza");
        nowyKlubowiczButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(nowyKlubowiczButton);

        JButton dodajUsunKlubowiczaButton = new JButton("Dodaj/usuń klubowicza z zajęć");
        dodajUsunKlubowiczaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(dodajUsunKlubowiczaButton);

        JButton utworzPracownikaButton = new JButton("Utwórz nowego pracownika");
        utworzPracownikaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(utworzPracownikaButton);

        JButton zmienCytatWiadomoscButton = new JButton("Zmień cytat/wiadomość");
        zmienCytatWiadomoscButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(zmienCytatWiadomoscButton);

        JButton wyswietlPracownikowKlubowiczowButton = new JButton("Wyświetl pracowników i klubowiczów");
        wyswietlPracownikowKlubowiczowButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(wyswietlPracownikowKlubowiczowButton);

        JButton wyswietlStatystykiButton = new JButton("Wyświetl statystyki");
        wyswietlStatystykiButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(wyswietlStatystykiButton);

        JButton wyswietlInfoButton = new JButton("Wyświetl informacje");
        wyswietlInfoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(wyswietlInfoButton);

        JButton uruchomSledzenieButton = new JButton("Uruchom śledzenie zmian");
        uruchomSledzenieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(uruchomSledzenieButton);

        JButton backButton = new JButton("Wróć");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(backButton);

        nowyKlubowiczButton.addActionListener(e -> {
            nowyKlubowiczButton.setVisible(false);
            newMemberFrame(e, "Manager");
        });

        dodajUsunKlubowiczaButton.addActionListener(e -> {
            addOrRemoveFromClasses(e, "Manager");
        });

        utworzPracownikaButton.addActionListener(e -> {
            utworzPracownikaButton.setVisible(false);
            newEmployeeFrame(e);
        });

        zmienCytatWiadomoscButton.addActionListener(e -> {
            frame.getContentPane().removeAll();

            JFrame newMemberFrame = new JFrame("Wiadomość");
            newMemberFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newMemberFrame.setSize(300, 200);
            frame.setLayout(new FlowLayout());

            JLabel label = new JLabel("Podaj treść nowej wiadomości:");
            JTextField textField = new JTextField(20);
            textField.setPreferredSize(new Dimension(250, 30));
            JButton saveButton = new JButton("Zapisz");

            frame.add(label);
            frame.add(textField);
            frame.add(saveButton);

            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String message = textField.getText();

                    globoGym.addManagerMessage(message);
                    JOptionPane.showMessageDialog(frame, "Zapisano pomyślnie");
                    showManagerOptions();
                }
            });
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
        });

        wyswietlPracownikowKlubowiczowButton.addActionListener(e -> {
            frame.getContentPane().removeAll();

            JFrame newMemberFrame = new JFrame("Lista klientów i pracowników");
            newMemberFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newMemberFrame.setSize(600, 400);
            frame.setLayout(new BorderLayout());

            JDialog dialog = new JDialog(frame, "Tabele klientów i pracowników", true);
            dialog.setSize(800, 600);

            JPanel panel = new JPanel(new GridLayout(2, 1));

            JTable clientsTable = createClientsTable();
            JScrollPane clientsScrollPane = new JScrollPane(clientsTable);
            panel.add(clientsScrollPane);

            JTable employeesTable = createEmployeesTable();
            JScrollPane employeesScrollPane = new JScrollPane(employeesTable);
            panel.add(employeesScrollPane);

            dialog.getContentPane().add(panel);
            dialog.setVisible(true);
        });

        wyswietlStatystykiButton.addActionListener(e -> {
            frame.getContentPane().removeAll();

            List<String> classTypes = getClassTypesFromSchedule();
            if (classTypes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Brak wpisanych typów zajęć w planie.");
                return;
            }

            JFrame frame = new JFrame("Statystyki");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new GridLayout(classTypes.size(), 1));

            for (String classType : classTypes) {
                int averageAttendeesPerDay = calculateAverageAttendeesPerDay();
                double paidToUnpaidRatio = calculatePaidToUnpaidRatio();
                int averageAttendeesForClassType = getTotalAttendeesForClassType(classType);

                JLabel label = new JLabel("Statystyki dla typu zajęć: " + classType);
                JLabel label1 = new JLabel("Średnia ilość klubowiczów zapisanych na zajęcia jednego dnia: " + averageAttendeesPerDay);
                JLabel label2 = new JLabel("Stosunek opłaconych do nieopłaconych klubowiczów: " + paidToUnpaidRatio);
                JLabel label3 = new JLabel("Średnia ilość klubowiczów zapisanych na podany typ zajęć: " + averageAttendeesForClassType);

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(4, 1));
                panel.add(label);
                panel.add(label1);
                panel.add(label2);
                panel.add(label3);

                frame.add(panel);
            }

            frame.pack();
            frame.setVisible(true);

        });

        wyswietlInfoButton.addActionListener(e -> {
//            frame.getContentPane().removeAll();
//            JFrame searchFrame = new JFrame("Wyszukiwanie");
//            searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            searchFrame.setLayout(new BorderLayout());
//
//            JPanel inputPanel = new JPanel();
//            JTextField keywordTextField = new JTextField(20);
//            JButton searchButton = new JButton("Szukaj");
//
//            inputPanel.add(new JLabel("Wpisz frazę:"));
//            inputPanel.add(keywordTextField);
//            inputPanel.add(searchButton);
//
//            searchFrame.add(inputPanel, BorderLayout.NORTH);
//
//            searchButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String keyword = keywordTextField.getText();
//
//                    List<Klubowicz> matchingKlubowiczList = searchMatchingKlubowicz(keyword);
//                    displayKlubowiczInfo(matchingKlubowiczList);
//
//                    List<Zajecia> matchingZajeciaList = searchMatchingZajecia(keyword);
//                    displayZajeciaInfo(matchingZajeciaList);
//
//                    List<String> matchingNazwyZajecList = searchMatchingNazwyZajec(keyword);
//                    displayNazwyZajecInfo(matchingNazwyZajecList);
//                }
//            });
//
//            searchFrame.pack();
//            searchFrame.setVisible(true);

        });

        uruchomSledzenieButton.addActionListener(e -> {

        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                showRoleSelection();
            }
        });

        optionsPanel.setLayout(new GridLayout(8, 1));
        optionsPanel.add(nowyKlubowiczButton);
        optionsPanel.add(dodajUsunKlubowiczaButton);
        optionsPanel.add(utworzPracownikaButton);
        optionsPanel.add(zmienCytatWiadomoscButton);
        optionsPanel.add(wyswietlPracownikowKlubowiczowButton);
        optionsPanel.add(wyswietlStatystykiButton);
        optionsPanel.add(wyswietlInfoButton);
        optionsPanel.add(uruchomSledzenieButton);

        optionsPanel.setPreferredSize(new Dimension(300, 400));
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        frame.getContentPane().add(optionsPanel);
        frame.setPreferredSize(new Dimension(300, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    public void newMemberFrame(ActionEvent e, String role){
        frame.getContentPane().removeAll();

        JFrame newMemberFrame = new JFrame("Utwórz nowego klubowicza");
        newMemberFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newMemberFrame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Imię:");
        JTextField nameTextField = new JTextField(20);

        JLabel surnameLabel = new JLabel("Nazwisko:");
        JTextField surnameTextField = new JTextField(20);

        JLabel birthdateLabel = new JLabel();
        birthdateLabel.setLayout(new BorderLayout());
        JLabel label1 = new JLabel("Data urodzenia");
        JLabel label2 = new JLabel("(RRRR-MM-DD):");
        birthdateLabel.add(BorderLayout.NORTH, label1);
        birthdateLabel.add(BorderLayout.CENTER, label2);
        birthdateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField birthdateTextField = new JTextField(20);

        JLabel loginLabel = new JLabel("Login:");
        JTextField loginTextFiled = new JTextField(20);

        JLabel passwordLabel = new JLabel("Hasło:");
        JPasswordField passwordField = new JPasswordField(20);

        JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
        JButton saveButton = new JButton("Zapisz");
        JButton backButton = new JButton("Wróć");
        buttonsPanel.add(saveButton);
        buttonsPanel.add(backButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String surname = surnameTextField.getText();
                String birthdateString = birthdateTextField.getText();
                String login = loginTextFiled.getText();
                String password = passwordField.getPassword().toString();

                Date birthdate = parseDate(birthdateString);
                if (birthdate != null) {
                    globoGym.registerKlubowicz(name, surname, birthdate, login, password);
                    JOptionPane.showMessageDialog(frame, "Zapisano pomyślnie!");
                    if(role.equals("Pracownik")){
                        showPracownikOptions();
                    }if(role.equals("Manager")){
                        showManagerOptions();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Błąd: Nieprawidłowy format daty!");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(role.equals("Pracownik")){
                    showPracownikOptions();
                } else if (role.equals("Manager")) {
                    showManagerOptions();
                }
            }
        });

        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);
        inputPanel.add(surnameLabel);
        inputPanel.add(surnameTextField);
        inputPanel.add(birthdateLabel);
        inputPanel.add(birthdateTextField);
        inputPanel.add(loginLabel);
        inputPanel.add(loginTextFiled);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);


        frame.getContentPane().add(inputPanel);
        frame.setPreferredSize(new Dimension(300, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    public void newEmployeeFrame(ActionEvent e){
        frame.getContentPane().removeAll();

        JFrame newMemberFrame = new JFrame("Utwórz nowego pracownika");
        newMemberFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newMemberFrame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2));

        JLabel nameLabel = new JLabel("Imię:");
        JTextField nameTextField = new JTextField(20);

        JLabel surnameLabel = new JLabel("Nazwisko:");
        JTextField surnameTextField = new JTextField(20);

        JLabel birthdateLabel = new JLabel();
        birthdateLabel.setLayout(new BorderLayout());
        JLabel label1 = new JLabel("Data urodzenia");
        JLabel label2 = new JLabel("(RRRR-MM-DD):");
        birthdateLabel.add(BorderLayout.NORTH, label1);
        birthdateLabel.add(BorderLayout.CENTER, label2);
        birthdateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField birthdateTextField = new JTextField(20);

        JLabel salaryLabel = new JLabel("Pensja:");
        JTextField salaryTextField = new JTextField(20);

        JLabel loginLabel = new JLabel("Login:");
        JTextField loginTextFiled = new JTextField(20);

        JLabel passwordLabel = new JLabel("Hasło:");
        JPasswordField passwordField = new JPasswordField(20);

        JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
        JButton saveButton = new JButton("Zapisz");
        JButton backButton = new JButton("Wróć");
        buttonsPanel.add(saveButton);
        buttonsPanel.add(backButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String surname = surnameTextField.getText();
                String birthdateString = birthdateTextField.getText();
                int salary = Integer.parseInt(salaryTextField.getText());
                String login = loginTextFiled.getText();
                String password = passwordField.getPassword().toString();

                Date birthdate = parseDate(birthdateString);
                if (birthdate != null) {
                    globoGym.registerPracownik(name, surname, birthdate, salary, login, password);
                    JOptionPane.showMessageDialog(frame, "Zapisano pomyślnie!");
                    showManagerOptions();
                } else {
                    JOptionPane.showMessageDialog(frame, "Błąd: Nieprawidłowy format daty!");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showManagerOptions();
            }
        });

        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);
        inputPanel.add(surnameLabel);
        inputPanel.add(surnameTextField);
        inputPanel.add(birthdateLabel);
        inputPanel.add(birthdateTextField);
        inputPanel.add(salaryLabel);
        inputPanel.add(salaryTextField);
        inputPanel.add(loginLabel);
        inputPanel.add(loginTextFiled);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);


        frame.getContentPane().add(inputPanel);
        frame.setPreferredSize(new Dimension(300, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addOrRemoveFromClasses(ActionEvent e, String role){
        frame.getContentPane().removeAll();

        JFrame newMemberFrame = new JFrame("Dodaj/usuń klubowicza z zajęć");
        newMemberFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newMemberFrame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Imię:");
        JTextField nameTextField = new JTextField(20);

        JLabel surnameLabel = new JLabel("Nazwisko:");
        JTextField surnameTextField = new JTextField(20);

        JLabel birthdateLabel = new JLabel();
        birthdateLabel.setLayout(new BorderLayout());
        JLabel label1 = new JLabel("Data urodzenia");
        JLabel label2 = new JLabel("(RRRR-MM-DD):");
        birthdateLabel.add(BorderLayout.NORTH, label1);
        birthdateLabel.add(BorderLayout.CENTER, label2);
        birthdateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField birthdateTextField = new JTextField(20);

        JLabel classDateLabel = new JLabel("Data zajęć");
        JTextField classDateTextField = new JTextField(20);

        JLabel classNameLabel = new JLabel("Nazwa zajęć");
        JTextField classNameTextField = new JTextField(20);

        JButton addButton = new JButton("Dodaj");
        JButton removeButton = new JButton("Usuń");
        JButton backButton = new JButton("Wróć");
        JPanel buttonsPanel = new JPanel(new GridLayout(1,3));
        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(backButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String surname = surnameTextField.getText();
                String birthdateString = birthdateTextField.getText();
                String clasdateString = classDateTextField.getText();
                String className = classNameTextField.getText();

                Date birthdate = parseDate(birthdateString);
                Date classDate = parseDate(clasdateString);

                Klubowicz klubowicz = new Klubowicz(name, surname, birthdate);

                String result;

                if (birthdate != null && classDate != null) {
                    result = globoGym.addKlubowiczToClasses(klubowicz, classDate, className);
                    JOptionPane.showMessageDialog(frame, result);
                    if(result.equals("Zapisano na zajęcia pomyślnie")){
                        if(role.equals("Pracownik")){
                            showPracownikOptions();
                        } else if (role.equals("Manager")) {
                            showManagerOptions();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Błąd: Nieprawidłowy format daty!");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String surname = surnameTextField.getText();
                String birthdateString = birthdateTextField.getText();
                String clasdateString = classDateTextField.getText();
                String className = classNameTextField.getText();

                Date birthdate = parseDate(birthdateString);
                Date classDate = parseDate(clasdateString);

                Klubowicz klubowicz = new Klubowicz(name, surname, birthdate);

                String result;

                if (birthdate != null && classDate != null) {
                    result = globoGym.removeKlubowiczFromClasses(klubowicz, classDate, className);
                    JOptionPane.showMessageDialog(frame, result);
                    if(result.equals("Usunięto pomyślnie")){
                            if(role.equals("Pracownik")){
                                showPracownikOptions();
                            } else if (role.equals("Manager")) {
                                showManagerOptions();
                            }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Błąd: Nieprawidłowy format daty!");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(role.equals("Pracownik")){
                    showPracownikOptions();
                } else if (role.equals("Manager")) {
                    showManagerOptions();
                }
            }
        });

        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);
        inputPanel.add(surnameLabel);
        inputPanel.add(surnameTextField);
        inputPanel.add(birthdateLabel);
        inputPanel.add(birthdateTextField);
        inputPanel.add(classDateLabel);
        inputPanel.add(classDateTextField);
        inputPanel.add(classNameLabel);
        inputPanel.add(classNameTextField);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(inputPanel);
        frame.setPreferredSize(new Dimension(300, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    public void displaySchedule(Map<Date, Map<String, String>> schedule){
        JFrame frame = new JFrame("Kalendarz");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(schedule.size(), 1));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Map.Entry<Date, Map<String, String>> entry : schedule.entrySet()){
            Date date = entry.getKey();
            Map<String, String> classInfo = entry.getValue();
            String className = classInfo.get("description");
            String room = classInfo.get("room");

            JLabel label = new JLabel();
            label.setText(dateFormat.format(date) + ": " + className + " - " + room);
            panel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(panel);

        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }

    private JTable createClientsTable() {
        String[] columnNames = {"Imię", "Nazwisko", "Data urodzenia"};
        String[][] data = new String[globoGym.getUsers().size()][3];

        int rowIndex = 0;
        for (Klubowicz klient : globoGym.getUsers()) {
            data[rowIndex][0] = klient.getName();
            data[rowIndex][1] = klient.getSurname();
            data[rowIndex][2] = klient.getBirthdate().toString();
            rowIndex++;
        }

        return new JTable(data, columnNames);
    }

    private JTable createEmployeesTable() {
        String[] columnNames = {"Imię", "Nazwisko", "Data urodzenia"};
        String[][] data = new String[globoGym.getEmployees().size()][3];

        int rowIndex = 0;
        for (Pracownik pracownik : globoGym.getEmployees()) {
            data[rowIndex][0] = pracownik.getName();
            data[rowIndex][1] = pracownik.getSurname();
            data[rowIndex][2] = pracownik.getBirthdate().toString();
            rowIndex++;
        }

        return new JTable(data, columnNames);
    }

    private int calculateAverageAttendeesPerDay() {
        int totalAttendees = 0;
        int daysWithAttendees = 0;

        for (List<Klubowicz> attendees : globoGym.getAttendees().values()) {
            totalAttendees += attendees.size();
            daysWithAttendees++;
        }

        if (daysWithAttendees > 0) {
            return totalAttendees / daysWithAttendees;
        } else {
            return 0;
        }
    }

    private double calculatePaidToUnpaidRatio() {
        int paidCount = 0;
        int unpaidCount = 0;

        for (List<Klubowicz> attendees : globoGym.getAttendees().values()) {
            for (Klubowicz klubowicz : attendees) {
                if (klubowicz.isPaid()) {
                    paidCount++;
                } else {
                    unpaidCount++;
                }
            }
        }

        if (unpaidCount > 0) {
            return (double) paidCount / unpaidCount;
        } else {
            return 0.0;
        }
    }

    private int getTotalAttendeesForClassType(String classType) {
        int totalClassAttendees = 0;
        int classCount = 0;

        for (List<Klubowicz> attendees : globoGym.getAttendees().values()) {
            for (Klubowicz klubowicz : attendees) {
                if (klubowicz.getClassName(globoGym).equals(classType)) {
                    totalClassAttendees++;
                }
            }
            classCount++;
        }

        if (classCount > 0) {
            return totalClassAttendees / classCount;
        } else {
            return 0;
        }
    }

    public List<String> getClassTypesFromSchedule() {
        List<String> classTypes = new ArrayList<>();

        for (Map<String, String> classInfo : globoGym.getSchedule().values()) {
            String description = classInfo.get("description");
            if (description != null) {
                String[] parts = description.split(" - ");
                if (parts.length > 1) {
                    String classType = parts[1];
                    if (!classTypes.contains(classType)) {
                        classTypes.add(classType);
                    }
                }
            }
        }

        return classTypes;
    }

//    public class KlubowiczeWindow {
//        public KlubowiczeWindow(List<Klubowicz> klubowicze) {
//            JFrame frame = new JFrame("Informacje o klubowiczach");
//            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            frame.setLayout(new BorderLayout());
//
//            KlubowiczTableModel tableModel = new KlubowiczTableModel(klubowicze);
//
//            JTable table = new JTable(tableModel);

//            JScrollPane scrollPane = new JScrollPane(table);
//
//            frame.add(scrollPane, BorderLayout.CENTER);
//
//            frame.pack();
//            frame.setVisible(true);
//        }
//    }

}

