import java.util.Date;

public class Pracownik {
    private String name;
    private String surname;
    private int id;
    private Date birthdate;
    private int salary;
    private static int lastId = 99999;
    public Pracownik(String name, String surname, Date birthdate, int salary){
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.id = lastId--;
        this.salary = salary;
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

    public int getSalary(){
        return salary;
    }

    public void setSalary(int salary){
        this.salary = salary;
    }


}
