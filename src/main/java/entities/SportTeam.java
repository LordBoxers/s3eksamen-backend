package entities;

import dtos.SportDTO;
import dtos.SportTeamDTO;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Mibse
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "SportTeam.deleteAllRows", query = "DELETE from SportTeam"),
})
public class SportTeam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String teamName;
    
    private int pricePerYear;
    
    private int minAge;
    
    private int maxAge;
    
    
    
    @ManyToOne
    private Sport sport;

    public SportTeam(String teamName, int pricePerYear, int minAge, int maxAge) {
        this.teamName = teamName;
        this.pricePerYear = pricePerYear;
        this.minAge = minAge;
        this.maxAge = maxAge;
//        this.sport = new Sport();
    }

    public SportTeam() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getPricePerYear() {
        return pricePerYear;
    }

    public void setPricePerYear(int pricePerYear) {
        this.pricePerYear = pricePerYear;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }


    void setSport(Sport sport) {
        this.sport = sport;
    }

    public Sport getSport() {
        return sport;
    }

    
}
