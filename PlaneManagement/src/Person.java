public class Person {
    private String name;
    private String surname;
    private String email;

    public Person (String name, String surname, String email){
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public void printPersonalInfo(){
        System.out.println("Name: " + name );
        System.out.println("Surname: " + surname );
        System.out.println("Email: " + email );

    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
}
