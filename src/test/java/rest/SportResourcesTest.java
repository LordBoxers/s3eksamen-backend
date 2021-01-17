package rest;

import entities.Sport;
import entities.SportTeam;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class SportResourcesTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    
    private Sport football;
    private Sport handball;
    private Sport baseball;
    private SportTeam fTeam1;
    private SportTeam hTeam1;
    private SportTeam bTeam1;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
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

    @Test
    public void testServerIsUp() {
        given().when().get("/sport").then().statusCode(200);
    }
    
    private static String securityToken;

    private static void login(String username, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", username, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }
    
    @Test
    public void testAddSport() {
        login("user", "test1");
        String json = String.format("{\n" +
"    \"name\": \"user12312\",\n" +
"    \"description\": \"ehehe\"\n" +
"}");
        given()
                .contentType("application/json")
                .body(json)
                .when().post("/sport/addSport")
                .then()
                .statusCode(200)
                .body("name", equalTo("user12312"));
    }
    
    //This test assumes the database contains two rows
//    @Test
//    public void testGetAllSports() throws Exception {
//        given()
//                .contentType("application/json")
//                .get("/sport/all").then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("name", equalTo("Football"));
//    }
//
//    @Test
//    public void testCount() throws Exception {
//        given()
//                .contentType("application/json")
//                .get("/xxx/count").then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("count", equalTo(2));
//    }
}
