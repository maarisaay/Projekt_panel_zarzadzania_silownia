import javax.swing.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GloboGym gym = new GloboGym();

                String name1 = "Jan";
                String surname1 = "Kowalski";
                Date birthdate1 = new Date(2003 - 1900, 6, 28); // 28.07.2003
                String login1 = "jan";
                String password1 = "qwer123";

                String name2 = "Alicja";
                String surname2 = "Nowak";
                Date birthdate2 = new Date(2003 - 1900, 6, 28); // 28.07.2003
                String login2 = "ala";
                String password2 = "qwer123";

                String name3 = "Anna";
                String surname3 = "Bąk";
                Date birthdate3 = new Date(2003 - 1900, 6, 28); // 28.07.2003
                String login3 = "anna";
                String password3 = "qwer123";

                gym.registerKlubowicz(name1, surname1, birthdate1, login1, password1);
                gym.registerPracownik(name2, surname2, birthdate2, 3500, login2, password2);
                gym.registerManager(name3, surname3, birthdate3, 5000, "Good", login3, password3);

                SwingUtilities.invokeLater(() -> {
                    GloboGymInterface gymInterface = new GloboGymInterface(gym);
                });

            }
        });
    }
}