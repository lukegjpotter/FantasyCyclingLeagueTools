package com.lukegjpotter.tools.fantasycyclingleaguetools.teams.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UsersTeamsTest {

    private UsersTeams usersTeams;

    @BeforeEach
    void setUp() {
        usersTeams = new UsersTeams();
    }

    @Test
    void testToString_oneTeam_oneRider() {
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider1");

        String expected = "<table>" +
                "<tr><th>user1</th></tr>" +
                "<tr><td>1-rider1</td></tr>" +
                "</table>";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testToString_twoTeams_twoRiders() {
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider2");
        usersTeams.addUser("user2");
        usersTeams.addRidertoUsersTeam("user2", "2-rider1");
        usersTeams.addRidertoUsersTeam("user2", "2-rider2");

        String expected = "<table>" +
                "<tr><th>user1</th><th>user2</th></tr>" +
                "<tr><td>1-rider1</td><td>2-rider1</td></tr>" +
                "<tr><td>1-rider2</td><td>2-rider2</td></tr>" +
                "</table>";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testToString_threeTeams_threeRiders() {
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider2");
        usersTeams.addRidertoUsersTeam("user1", "1-rider3");
        usersTeams.addUser("user2");
        usersTeams.addRidertoUsersTeam("user2", "2-rider1");
        usersTeams.addRidertoUsersTeam("user2", "2-rider2");
        usersTeams.addRidertoUsersTeam("user2", "2-rider3");
        usersTeams.addUser("user3");
        usersTeams.addRidertoUsersTeam("user3", "3-rider1");
        usersTeams.addRidertoUsersTeam("user3", "3-rider2");
        usersTeams.addRidertoUsersTeam("user3", "3-rider3");

        String expected = "<table>" +
                "<tr><th>user1</th><th>user2</th><th>user3</th></tr>" +
                "<tr><td>1-rider1</td><td>2-rider1</td><td>3-rider1</td></tr>" +
                "<tr><td>1-rider2</td><td>2-rider2</td><td>3-rider2</td></tr>" +
                "<tr><td>1-rider3</td><td>2-rider3</td><td>3-rider3</td></tr>" +
                "</table>";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testReplaceRiderForUsersTeam_oneTeam_oneRider() {
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider1");
        usersTeams.replaceRiderForUsersTeam("user1", "1-rider1", "newrider");

        String expected = "<table>" +
                "<tr><th>user1</th></tr>" +
                "<tr><td bgcolor=\"#D7BDE2\">newrider</td></tr>" +
                "</table>";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testReplaceRiderForUsersTeam_twoTeams_twoRiders() {
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider2");
        usersTeams.addUser("user2");
        usersTeams.addRidertoUsersTeam("user2", "2-rider1");
        usersTeams.addRidertoUsersTeam("user2", "2-rider2");

        usersTeams.replaceRiderForUsersTeam("user1", "1-rider2", "1-newrider2");
        usersTeams.replaceRiderForUsersTeam("user2", "2-rider1", "2-newrider1");


        String expected = "<table>" +
                "<tr><th>user1</th><th>user2</th></tr>" +
                "<tr><td>1-rider1</td><td bgcolor=\"#D7BDE2\">2-newrider1</td></tr>" +
                "<tr><td bgcolor=\"#D7BDE2\">1-newrider2</td><td>2-rider2</td></tr>" +
                "</table>";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }
}