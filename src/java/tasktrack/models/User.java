package tasktrack.models;

public abstract class User {
    protected int id;
    protected String name;
    protected String email;
    protected String password;

    public abstract boolean login();

    public void logout() {
        System.out.println(name + " logged out.");
    }

    public void viewProfile() {
        System.out.println("Nama: " + name + ", Email: " + email);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

}
