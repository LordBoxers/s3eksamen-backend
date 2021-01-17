package facades;

import dtos.SportDTO;
import dtos.SportTeamDTO;
import entities.Sport;
import entities.SportTeam;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mibse
 */
public class SportFacade {

    private static SportFacade instance;
    private static EntityManagerFactory emf;

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static SportFacade getSportFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SportFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<SportDTO> getAllSports() {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            ArrayList<SportDTO> sportDTOList = new ArrayList<SportDTO>();

            TypedQuery<Sport> query = em.createQuery("SELECT s FROM Sport s", entities.Sport.class);
            List<Sport> Sports = query.getResultList();

            for (Sport s : Sports) {
                SportDTO sDTO = new SportDTO(s);
                sportDTOList.add(sDTO);
            }
            em.getTransaction().commit();

            return sportDTOList;
        } finally {
            em.close();
        }

    }

    public List<SportTeamDTO> getAllSportTeams(){
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            ArrayList<SportTeamDTO> sportTeamDTOList = new ArrayList<SportTeamDTO>();

            TypedQuery<SportTeam> query = em.createQuery("SELECT st FROM SportTeam st", entities.SportTeam.class);
            List<SportTeam> SportTeams = query.getResultList();

            for (SportTeam st : SportTeams) {
                SportTeamDTO stDTO = new SportTeamDTO(st);
                sportTeamDTOList.add(stDTO);
                
            }
            em.getTransaction().commit();
            return sportTeamDTOList;

        } finally {
            em.close();
        }

    }
    
    public SportDTO addSport(SportDTO sDTO) throws MissingInputException, NotFoundException {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();

        Sport sport = new Sport(sDTO.getName(), sDTO.getDescription());


        
        em.persist(sport);
        em.getTransaction().commit();
        return new SportDTO(sport);
    }
    public SportTeamDTO addSportTeam(SportTeamDTO stDTO) throws MissingInputException, NotFoundException {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();

        SportTeam sportTeam = new SportTeam(stDTO.getTeamName(), stDTO.getPricePerYear(), stDTO.getMinAge(), stDTO.getMaxAge());

        em.persist(sportTeam);
        em.getTransaction().commit();
        return new SportTeamDTO(sportTeam);
    }
    
}
