package Editor.Project.Entity;

import jakarta.persistence.*;

@Entity// this anonation  converts this class into db table and map obj-->table
@Table(name="users")
public class Users {

    @Id// this shows the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// u dont create id and GenerationType.IDENTITY means db auto -increments
    private Long id;

    private String username;
    private String email;
    private String password;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
