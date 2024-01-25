import javax.swing.*;
import java.util.*;
import java.util.HashMap;

public class GloboGym {
    private List<Klubowicz> users;
    private List<Pracownik> employees;

    private List<Manager> managers;
    private Map<String, String> klubowicze;
    private Map<String, String> pracownicy;
    private Map<String, String> managerowie;
    private Map<Date, Map<String, String>> schedule;
    private Map<Date, Pracownik> assignedEmployees;
    private Map<Date, List<Klubowicz>> attendees;
    private String managerMessage;

    public GloboGym(){
        users = new ArrayList<>();
        employees = new ArrayList<>();
        managers = new ArrayList<>();
        klubowicze = new HashMap<>();
        pracownicy = new HashMap<>();
        managerowie = new HashMap<>();
        schedule = new HashMap<>();
        assignedEmployees = new HashMap<>();
        attendees = new HashMap<>();
    }

    public void registerKlubowicz(String name, String surname, Date birthdate, String login, String password){
        if(password.length() < 5){
            System.out.println("Za krotkie haslo");
            return;
        }
        Klubowicz newMember  = new Klubowicz(name, surname, birthdate);
        users.add(newMember);
        klubowicze.put(login, password);
    }

    public void registerPracownik(String name, String surname, Date birthdate, int salary, String login, String password){
        if(password.length() < 5){
            System.out.println("Za krotkie haslo");
            return;
        }
        Pracownik newEmployee = new Pracownik(name, surname, birthdate, salary);
        employees.add(newEmployee);
        pracownicy.put(login, password);
    }

    public void registerManager(String name, String surname, Date birthdate, int salary, String managementStyle, String login, String password){
        if(password.length() < 5){
            System.out.println("Za krotkie haslo");
            return;
        }
        Manager newManager = new Manager(name, surname, birthdate, salary, managementStyle);
        managers.add(newManager);
        managerowie.put(login, password);
    }

    public boolean loginKlubowicz(String login, String password){
        if(klubowicze.containsKey(login) && klubowicze.get(login).equals(password)){
            return true;
        }
        return false;
    }

    public boolean loginPracownik(String login, String haslo) {
        if (pracownicy.containsKey(login) && pracownicy.get(login).equals(haslo)) {
            return true;
        }
        return false;
    }

    public boolean loginManager(String login, String haslo) {
        if (managerowie.containsKey(login) && managerowie.get(login).equals(haslo)) {
            return true;
        }
        return false;
    }

    public void addClasses(Date date, String description, String room){
        Map<String, String> classInfo = new HashMap<>();
        classInfo.put("description", description);
        classInfo.put("room", room);
        schedule.put(date, classInfo);
    }


    public void removeClasses(Date date, String description, String room){
        Map<String, String> classInfo = schedule.get(date);
        if(classInfo != null && classInfo.get("description").equals(description) && classInfo.get("room").equals(room)){
            schedule.remove(date);
        }
    }

    public String getDescriptionForClass(Date date) {
        Map<String, String> classInfo = schedule.get(date);
        if (classInfo != null) {
            return classInfo.get("description");
        }
        return null;
    }

    public String getRoomForClass(Date date) {
        Map<String, String> classInfo = schedule.get(date);
        if (classInfo != null) {
            return classInfo.get("room");
        }
        return null;
    }

    public boolean isClass(Date date, String class_name){
        Date currentDate = new Date();
        Map<String, String> classInfo = schedule.get(date);

        if (classInfo != null && classInfo.containsValue(class_name) && date.after(currentDate)) {
            return true;
        }

        return false;
    }

    public String addKlubowiczToClasses(Klubowicz klubowicz, Date date, String className){
        if(schedule.containsKey(date)){
            Map<String, String> classMap = schedule.get(date);
            if(classMap.containsKey(className)){
                if(klubowicz.isPaid()){
                    attendees.computeIfAbsent(date, k-> new ArrayList<>()).add(klubowicz);
                    return "Zapisano na zajęcia pomyślnie";
                }else{
                    return "Klubowicz nie ma opłaconych zajęć";
                }
            }else{
                return "Niepoprawna nazwa zajęć";
            }
        }else {
            return "Brak zajęć w tym dniu";
        }
    }

    public String removeKlubowiczFromClasses(Klubowicz klubowicz, Date date, String className){
        if (attendees.containsKey(date)) {
            List<Klubowicz> membersList = attendees.get(date);
            if (membersList.contains(klubowicz)) {
                membersList.remove(klubowicz);
                return "Usunięto pomyślnie";
            } else {
                return "Klubowicz nie jest zapisany na te zajęcia";
            }
        } else {
            return "Brak zapisanych klubowiczów na te zajęcia";
        }
    }

    public Map<Date, Map<String, String>> getSchedule(){
        return schedule;
    }

//    public Klubowicz findKlubowicz(String name, String surname, Date birthdate){
//        for (Klubowicz klubowicz : klubowicze.values()) {
//            if (klubowicz.getSurname().equals(surname)) {
//                if (klubowicz.getName().equals(name) && klubowicz.getBirthdate().equals(birthdate)) {
//                    return klubowicz;
//                }
//            }
//        }
//        return null;
//    }

    public String getLogin(String login) {
        if(klubowicze.containsKey(login)){
            return klubowicze.get(login);
        }
        return null;
    }

    public float getStanKontaKlubowicza(String login) {
        for (Klubowicz klubowicz : users) {
            if (getLogin(login).equals(login)) {
                return klubowicz.getBalance();
            }
        }
        return 0;
    }

    public boolean payMembershipFee(String login) {
        for (Klubowicz klubowicz : users) {
            if (getLogin(login).equals(login)) {
                if (klubowicz.isPaid()) {
                    return false;
                } else {
                    klubowicz.setPaid(true);
                    return true;
                }
            }
        }
        return false;
    }

    public void addManagerMessage(String message){
        managerMessage = message;
    }

    public String getManagerMessage(){
        return managerMessage;
    }

    public List<Klubowicz> getUsers() {
        return users;
    }

    public List<Pracownik> getEmployees() {
        return employees;
    }

    public Map<Date, List<Klubowicz>> getAttendees() {
        return attendees;
    }

//    public int getTotalAttendeesForClassType(String classType) {
//        int totalClassAttendees = 0;
//
//        for (Map.Entry<Date, Map<String, String>> entry : this.getSchedule().entrySet()) {
//            String description = entry.getValue().get("description");
//            if (description != null && description.equals(classType)) {
//                List<Klubowicz> attendees = this.getAttendees().get(entry.getKey());
//                if (attendees != null) {
//                    totalClassAttendees += attendees.size();
//                }
//            }
//        }
//
//        return totalClassAttendees;
//    }

//    private Set<String> getClassTypesFromSchedule() {
//        Set<String> classTypes = new HashSet<>();
//        for (Map.Entry<Date, Map<String, String>> entry : this.getSchedule().entrySet()) {
//            String description = entry.getValue().get("description");
//            if (description != null) {
//                classTypes.add(description);
//            }
//        }
//        return classTypes;
//    }
//
//    private List<Klubowicz> searchMatchingKlubowicz(String keyword) {
//        List<Klubowicz> matchingKlubowiczList = new ArrayList<>();
//
//        for (Klubowicz klubowicz : users) {
//            String imie = klubowicz.getName().toLowerCase();
//            String nazwisko = klubowicz.getSurname().toLowerCase();
//
//            if (imie.contains(keyword.toLowerCase()) || nazwisko.contains(keyword.toLowerCase())) {
//                matchingKlubowiczList.add(klubowicz);
//            }
//        }
//
//        return matchingKlubowiczList;
//    }
//
//    private List<Zajecia> searchMatchingZajecia(String keyword) {
//        List<Zajecia> matchingZajeciaList = new ArrayList<>();
//
//        return matchingZajeciaList;
//    }
//
//    private void displayZajeciaInfo(List<Zajecia> zajeciaList) {
//    }
//
//    private List<String> searchMatchingNazwyZajec(String keyword) {
//        List<String> matchingNazwyZajecList = new ArrayList<>();
//
//
//        return matchingNazwyZajecList;
//    }
//
//    private void displayNazwyZajecInfo(List<String> nazwyZajecList) {
//
//    }


//    public static void assignEmployeeToClass(String date, String className, String employeeName, GloboGym globoGym) {
//        for (Date key : globoGym.getSchedule().keySet()) {
//            if (key.equals(date)) {
//                Map<String, String> classInfo = globoGym.getSchedule().get(key);
//                if (classInfo.get("description").equals(className)) {
//                    for (Pracownik employee : globoGym.getEmployees()) {
//                        if (employee.getName().equals(employeeName)) {
//                            assignedEmployees.put(date, employee);
//                            return;
//                        }
//                    }
//                }
//            }
//        }
//    }

}
