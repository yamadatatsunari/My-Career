package AMS.Management;

public class Employee {
    private String position;
    private String name;
    private String email;
    private String status;

    public Employee(String position, String name, String email, String status) {
        this.position = position;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }
}

