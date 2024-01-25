import java.util.Date;

public class Manager {
    private String name;
    private String surname;
    private int id;
    private Date birthdate;
    private int salary;
    private String managementStyle;


    private static int lastId = 4548;

    public Manager(String name, String surname, Date birthdate, int salary, String managementStyle){
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.id = lastId++;
        this.salary = salary;
        this.managementStyle = managementStyle;
    }
}
