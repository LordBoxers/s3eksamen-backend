package dtos;

import entities.Sport;
import entities.SportTeam;
import java.util.ArrayList;

/**
 *
 * @author Mibse
 */
public class SportTeamDTO {
    
    private String teamName;
    
    private int pricePerYear;
    
    private int minAge;
    
    private int maxAge;
    
//    private Sport sport;

    public SportTeamDTO(SportTeam sportTeam) {
        this.teamName = sportTeam.getTeamName();
        this.pricePerYear = sportTeam.getPricePerYear();
        this.minAge = sportTeam.getMinAge();
        this.maxAge = sportTeam.getMaxAge();
//        this.sport = sportTeam.getSport();
        
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
    
    
}
