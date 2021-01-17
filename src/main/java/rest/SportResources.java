package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.SportDTO;
import dtos.SportTeamDTO;
import entities.Sport;
import entities.User;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import utils.EMF_Creator;
import facades.SportFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("sport")
public class SportResources {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final SportFacade FACADE =  SportFacade.getSportFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String hello() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @Path("allSports")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allSports() {

        List<SportDTO> sDTO = FACADE.getAllSports();
        return GSON.toJson(sDTO);
    }
    
    @Path("allSportTeams")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allSportTeams() {

        List<SportTeamDTO> sDTO = FACADE.getAllSportTeams();
        return GSON.toJson(sDTO);
    }
    
    @Path("/addSport")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addSport(String sprt) throws NotFoundException, MissingInputException {
        SportDTO sport = GSON.fromJson(sprt, SportDTO.class);
        SportDTO sDTO = FACADE.addSport(sport);
        return GSON.toJson(sDTO);
    }
    
    @Path("/addSportTeam")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addSportTeam(String sprtTeam) throws NotFoundException, MissingInputException {
        SportTeamDTO sportTeam = GSON.fromJson(sprtTeam, SportTeamDTO.class);
        SportTeamDTO stDTO = FACADE.addSportTeam(sportTeam);
        return GSON.toJson(stDTO);
    }
    
    
}
