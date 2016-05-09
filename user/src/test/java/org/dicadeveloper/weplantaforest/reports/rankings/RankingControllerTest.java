package org.dicadeveloper.weplantaforest.reports.rankings;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.dicadeveloper.weplantaforest.WeplantaforestApplication;
import org.dicadeveloper.weplantaforest.testsupport.CleanDbRule;
import org.dicadeveloper.weplantaforest.testsupport.DbInjecter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = WeplantaforestApplication.class)
@IntegrationTest({ "spring.profiles.active=test" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RankingControllerTest {

    private MockMvc mockMvc;

    @Rule
    @Autowired
    public CleanDbRule _cleanDbRule;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    public DbInjecter _dbInjecter;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetBestUser() throws Exception {
        long timeOfPlanting = System.currentTimeMillis();
        _dbInjecter.injectTreeType("wood", "desc", 0.5);
        _dbInjecter.injectUser("Bert");
        _dbInjecter.injectTree("wood", "Bert", 10, timeOfPlanting);

        this.mockMvc.perform(get("/ranking/bestUser/{pageNr}/{pageSize}", 0, 4).accept("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].name").value("Bert"))
                    .andExpect(jsonPath("$.content[0].amount").value(10))
                    .andExpect(jsonPath("$.content[0].co2Saved").exists());
    }

    @Test
    public void testGetLastUser() throws Exception {
        _dbInjecter.injectUser("Adam", 90000L);
        _dbInjecter.injectUser("Bert", 70000L);
        _dbInjecter.injectUser("Claus", 60000L);
        _dbInjecter.injectUser("Dirk", 50000L);
        _dbInjecter.injectUser("Adam2", 40000L);
        _dbInjecter.injectUser("Bert2", 30000L);
        _dbInjecter.injectUser("Claus2", 20000L);
        _dbInjecter.injectUser("Dirk2", 10000L);
        _dbInjecter.injectUser("Adam3", 10000L);
        _dbInjecter.injectUser("Bert3", 10000L);
        _dbInjecter.injectUser("Claus3", 10000L);
        _dbInjecter.injectUser("Dirk3", 10000L);

        this.mockMvc.perform(get("/ranking/lastCreatedUser").accept("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.[0].name").value("Adam"))
                    .andExpect(jsonPath("$.[0].date").value("31.12.1969"))
                    .andExpect(jsonPath("$.[0].time").value("16:01:30"))
                    .andExpect(jsonPath("$.[11]").doesNotExist());
    }

    @Test
    public void testGetBestOrganizationTypeRanking() throws Exception {
        long timeOfPlanting = System.currentTimeMillis();

        _dbInjecter.injectTreeType("wood", "desc", 0.5);

        _dbInjecter.injectUser("Adam", 10000L, 0);
        _dbInjecter.injectUser("Bert", 10000L, 0);
        _dbInjecter.injectUser("money company", 10000L, 1);
        _dbInjecter.injectUser("no money company", 10000L, 2);
        _dbInjecter.injectUser("hogwarts", 10000L, 3);

        _dbInjecter.injectTree("wood", "Adam", 100, timeOfPlanting);
        _dbInjecter.injectTree("wood", "Bert", 80, timeOfPlanting);
        _dbInjecter.injectTree("wood", "money company", 50, timeOfPlanting);
        _dbInjecter.injectTree("wood", "no money company", 10, timeOfPlanting);
        _dbInjecter.injectTree("wood", "hogwarts", 10, timeOfPlanting);

        this.mockMvc.perform(
                get("/ranking/bestOrgType/{organizationType}/{pageNr}/{pageSize}", 0, 0, 4).accept("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalElements").value(2))
                    .andExpect(jsonPath("$.content[0].name").value("Adam"))
                    .andExpect(jsonPath("$.content[0].amount").value(100))
                    .andExpect(jsonPath("$.content[0].co2Saved").exists());
    }

    @Test
    public void testGetLastPlantedTrees() throws Exception {
        _dbInjecter.injectTreeType("wood", "desc", 0.5);

        _dbInjecter.injectUser("Adam", 90000L);
        _dbInjecter.injectUser("Bert", 90000L);
        _dbInjecter.injectUser("Claus", 90000L);

        _dbInjecter.injectProject("Project", "Adam", "very n1 project", true, 0, 0);

        _dbInjecter.injectPlantArticle("wood", "Project", 3.0);

        _dbInjecter.injectTreeToProject("wood", "Adam", 9, 900000L, "Project");
        _dbInjecter.injectTreeToProject("wood", "Bert", 8, 800000L, "Project");
        _dbInjecter.injectTreeToProject("wood", "Claus", 7, 700000L, "Project");
        _dbInjecter.injectTreeToProject("wood", "Adam", 6, 600000L, "Project");
        _dbInjecter.injectTreeToProject("wood", "Bert", 5, 500000L, "Project");
        _dbInjecter.injectTreeToProject("wood", "Claus", 4, 400000L, "Project");
        _dbInjecter.injectTreeToProject("wood", "Adam", 3, 300000L, "Project");
        _dbInjecter.injectTreeToProject("wood", "Bert", 2, 200000L, "Project");
        _dbInjecter.injectTreeToProject("wood", "Claus", 1, 100000L, "Project");
        _dbInjecter.injectTreeToProject("wood", "Adam", 1, 100000L, "Project");
        _dbInjecter.injectTreeToProject("wood", "Bert", 1, 100000L, "Project");
        _dbInjecter.injectTreeToProject("wood", "Claus", 1, 100000L, "Project");

        this.mockMvc.perform(get("/ranking/lastPlantedTrees").accept("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.[11]").doesNotExist())
                    .andExpect(jsonPath("$.[0].name").value("Adam"))
                    .andExpect(jsonPath("$.[0].amount").value(9))
                    .andExpect(jsonPath("$.[0].projectName").value("Project"));
    }

    @Test
    public void testGetBestTeams() throws Exception {
        long timeOfPlanting = System.currentTimeMillis();

        _dbInjecter.injectTreeType("wood", "desc", 0.5);

        _dbInjecter.injectUser("Adam", 90000L);
        _dbInjecter.injectUser("Bert", 90000L);
        _dbInjecter.injectUser("Claus", 90000L);

        _dbInjecter.injectTeam("avengers", "Adam");

        _dbInjecter.addUserToTeam("avengers", "Bert");

        _dbInjecter.injectTree("wood", "Adam", 100, timeOfPlanting);
        _dbInjecter.injectTree("wood", "Bert", 80, timeOfPlanting);
        _dbInjecter.injectTree("wood", "Claus", 80, timeOfPlanting);

        this.mockMvc.perform(get("/ranking/bestTeam/{pageNr}/{pageSize}", 0, 4).accept("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalElements").value(1))
                    .andExpect(jsonPath("$.content[0].name").value("avengers"))
                    .andExpect(jsonPath("$.content[0].amount").value(180))
                    .andExpect(jsonPath("$.content[0].co2Saved").exists());

    }

}
