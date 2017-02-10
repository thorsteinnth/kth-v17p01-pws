package bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User
{
    private final int id;
    private String username;
    private String password;
    private String token;

    // Need to have a parameterless constructor too, for XML marshalling probably
    public User()
    {
        this.id = -1;
    }

    public User(int id)
    {
        this.id = id;
    }

    @XmlElement
    public int getId()
    {
        return id;
    }

    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlElement
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
