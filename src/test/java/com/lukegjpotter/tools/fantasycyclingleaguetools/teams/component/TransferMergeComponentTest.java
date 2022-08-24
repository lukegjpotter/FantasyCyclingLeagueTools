package com.lukegjpotter.tools.fantasycyclingleaguetools.teams.component;

import com.lukegjpotter.tools.fantasycyclingleaguetools.teams.model.UsersTeams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransferMergeComponentTest {

    @Autowired
    private TransferMergeComponent transferMergeComponent;
    private UsersTeams usersTeams;

    @BeforeEach
    void setUp() {
        usersTeams = new UsersTeams();
    }

    @Test
    void mergeTransfersIntoUsersTeams() {
        usersTeams.addUser("Sean");
        usersTeams.addRidertoUsersTeam("Sean", "Roglic");
        usersTeams.addRidertoUsersTeam("Sean", "Alaphilippe");
        usersTeams.addRidertoUsersTeam("Sean", "Bennett");
        usersTeams.addUser("Kay");
        usersTeams.addRidertoUsersTeam("Kay", "Stewart");
        usersTeams.addRidertoUsersTeam("Kay", "Higuita");
        usersTeams.addRidertoUsersTeam("Kay", "Hayter");
        usersTeams.addUser("Luke");
        usersTeams.addRidertoUsersTeam("Luke", "Bennett");
        usersTeams.addRidertoUsersTeam("Luke", "Merlier");
        usersTeams.addRidertoUsersTeam("Luke", "Teunissen");

        String transfersHtmlSource = "\n" +
                "<html><head><title>Transfers</title></head><body><p>Transfers: Out -> In<br><br>" +
                "SEAN (1)<br>Bennett -> Lutsenko<br><br><br>" +
                "KAY (2)<br>Stewart -> Yates<br>Higuita -> De Gendt<br><br><br>" +
                "LUKE (3)<br>Bennett -> Evenepoel<br>Merlier -> Herrada<br>Teunissen -> Alaphilippe<br><br><br>" +
                "</p></body></html>";

        String expected =
                "<table>" +
                        "<tr><th>Sean</th><th>Kay</th><th>Luke</th></tr>" +
                        "<tr><td>Roglic</td><td bgcolor=\"#D7BDE2\">Yates</td><td bgcolor=\"#D7BDE2\">Evenepoel</td></tr>" +
                        "<tr><td>Alaphilippe</td><td bgcolor=\"#D7BDE2\">De Gendt</td><td bgcolor=\"#D7BDE2\">Herrada</td></tr>" +
                        "<tr><td bgcolor=\"#D7BDE2\">Lutsenko</td><td>Hayter</td><td bgcolor=\"#D7BDE2\">Alaphilippe</td></tr>" +
                        "</table>";

        String actual = transferMergeComponent.mergeTransfersIntoUsersTeams(transfersHtmlSource, usersTeams).toString();

        assertEquals(expected, actual); //, "Expected not equals Actual.");
    }
}