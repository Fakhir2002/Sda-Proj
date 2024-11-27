package OOP;

public abstract class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Abstract method to be implemented by subclasses for updating details
    public abstract void updateDetails();

    // Factory Method to create different user types
    public static User createUser(String userType, String username, String password) {
        switch (userType.toLowerCase()) {
            case "doctor":
                return new Doctor(username, password);
            case "patient":
                return new Patient(username, password);
            case "admin":
                return new Admin(username, password);
            case "staff":
               return new Staff(username, password);
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }
}
