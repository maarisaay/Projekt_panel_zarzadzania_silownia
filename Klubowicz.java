import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Klubowicz {

    private String name;
    private String surname;
    private int id;
    private Date birthdate;
    private boolean isPaid;
    private static int lastId = 0;
    private String profileImage;

    private float balance;

    public Klubowicz(String name, String surname, Date birthdate){
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.id = lastId++;
        this.isPaid = false;
        this.profileImage = "";
        this.balance = 0;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSurname(){
        return surname;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public int getId(){
        return id;
    }

    public Date getBirthdate(){
        return birthdate;
    }

    public boolean isPaid(){
        return isPaid;
    }

    public void setPayment(boolean isPaid){
        this.isPaid = isPaid;
    }

//    public void signUpForClasses(Date date, String class_name, GloboGym globoGym){
//        if(isPaid && globoGym.isClass(date, class_name)){
//            globoGym.
//        }
//    }

    public float getBalance(){
        return balance;
    }

    public void setPaid(boolean b) {
        isPaid = b;
    }

    public String getClassName(GloboGym globoGym) {
        String className = "";

        for (Map<String, String> classInfo : globoGym.getSchedule().values()) {
            String description = classInfo.get("description");
            if (description != null) {
                String[] parts = description.split(" - ");
                if (parts.length > 0) {
                    className = parts[0];
                    break;
                }
            }
        }
        return className;
    }
}
