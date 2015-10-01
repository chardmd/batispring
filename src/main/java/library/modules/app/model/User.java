package library.modules.app.model;


public class User {

    private int id;
    private String username;
    private String passwordDigest;
    private String email;
    private Long maxCaloriesPerDay;

    public User() {

    }

    public User(String username, String passwordDigest, String email, Long maxCaloriesPerDay) {
        this.username = username;
        this.passwordDigest = passwordDigest;
        this.email = email;
        this.maxCaloriesPerDay = maxCaloriesPerDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public void setPasswordDigest(String passwordDigest) {
        this.passwordDigest = passwordDigest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMaxCaloriesPerDay() {
        return maxCaloriesPerDay;
    }

    public void setMaxCaloriesPerDay(Long maxCaloriesPerDay) {
        this.maxCaloriesPerDay = maxCaloriesPerDay;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", maxCaloriesPerDay=" + maxCaloriesPerDay +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
