package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author Mibse
 */
@Entity
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String name;
    
    private String email;
    
    private int phone;
    
    private int age;

    @ManyToMany(mappedBy = "memberinfo", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private boolean payed;
    
    @ManyToMany(mappedBy = "memberinfo", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Date datePayed;

    public Player(String name, String email, int phone, int age, boolean payed, Date datePayed) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.payed = payed;
        this.datePayed = datePayed;
    }

    public Player() {
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public Date getDatePayed() {
        return datePayed;
    }

    public void setDatePayed(Date datePayed) {
        this.datePayed = datePayed;
    }
    
    
    
    
    
    
}
