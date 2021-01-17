package facades;

import utils.EMF_Creator;
import entities.Sport;
import entities.SportTeam;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class SportFacadeTest {

    private static EntityManagerFactory emf;
    private static SportFacade facade;
    
    private Sport football;
    private Sport handball;
    private Sport baseball;
    private SportTeam fTeam1;
    private SportTeam hTeam1;
    private SportTeam bTeam1;

    public SportFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = SportFacade.getSportFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            football = new Sport("Football", "Sport with football");
            handball = new Sport("Handball","Sport with handball");
            baseball = new Sport("Baseball","Sport with baseball");
            fTeam1 = new SportTeam("Team1", 500, 13, 18);
            hTeam1 = new SportTeam("Team2", 500, 13, 18);
            bTeam1 = new SportTeam("Team3", 500, 13, 18);
            football.addTeam(fTeam1);
            handball.addTeam(hTeam1);
            baseball.addTeam(bTeam1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.createNamedQuery("SportTeam.deleteAllRows").executeUpdate();
            em.createNamedQuery("Sport.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(football);
            em.persist(handball);
            em.persist(baseball);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    
    @Test
    public void testGetAllSports() {
        int actualSize = facade.getAllSports().size();
        int expectedSize = 3;
        assertEquals(expectedSize, actualSize, "Expects three Sports");
    }
    
    @Test
    public void testAddSport() {
        EntityManager em = emf.createEntityManager();
        Sport floorball = new Sport("Floorball","Sport with baseball");
        SportTeam flTeam1 = new SportTeam("Team12", 530, 9, 11);
        try {
            em.getTransaction().begin();
        floorball.addTeam(flTeam1);
            em.persist(floorball);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        int expectedSports = 4;
        assertEquals(expectedSports, facade.getAllSports().size(), "Expects fire persons");
    }

}
