package com.lukegjpotter.tools.fantasycyclingleaguetools.teams.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsersTeamsTest {

    private UsersTeams usersTeams;

    @BeforeEach
    void setUp() {
        usersTeams = new UsersTeams();
    }

    @Test
    void testIsRiderInUsersTeam_RiderIsInTeam() {
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider2");
        usersTeams.addUser("user2");
        usersTeams.addRidertoUsersTeam("user2", "2-rider1");
        usersTeams.addRidertoUsersTeam("user2", "2-rider2");

        assertTrue(usersTeams.isRiderInUsersTeam("user2", "2-rider2"));
    }

    @Test
    void testIsRiderInUsersTeam_transfer_RiderIsInTeam() {
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider2");
        usersTeams.addUser("user2");
        usersTeams.addRidertoUsersTeam("user2", "2-rider1");
        usersTeams.addRidertoUsersTeam("user2", "2-rider2");
        usersTeams.replaceRiderForUsersTeam("user1", "1-rider2", "NewRider");

        assertTrue(usersTeams.isRiderInUsersTeam("user1", "NewRider"));
    }

    @Test
    void testIsRiderInUsersTeam_RiderNotInTeam() {
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider2");
        usersTeams.addUser("user2");
        usersTeams.addRidertoUsersTeam("user2", "2-rider1");
        usersTeams.addRidertoUsersTeam("user2", "2-rider2");

        assertFalse(usersTeams.isRiderInUsersTeam("user1", "mystery rider"));
    }

    @Test
    void testGetUsernames() {
        usersTeams.addUser("user1");
        usersTeams.addUser("user2");
        List<String> expected = Arrays.asList("user1", "user2");
        List<String> actual = usersTeams.getUsernames();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testToString_EdgeCase_zeroTeams() {
        String expected = "";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testToString_EdgeCase_twoTeams_oneRiderOnFirstTeam() {
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider1");
        usersTeams.addUser("user2");

        String expected = "<table>" +
                "<tr><th width=\"100px\">user1</th><th width=\"100px\">user2</th></tr>" +
                "<tr><td>1-rider1</td><td>&nbsp;</td></tr>" +
                "</table>";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testToString_EdgeCase_twoTeams_oneRiderOnSecondTeam() {
        usersTeams.addUser("user1");
        usersTeams.addUser("user2");
        usersTeams.addRidertoUsersTeam("user2", "2-rider1");

        String expected = "<table>" +
                "<tr><th width=\"100px\">user1</th><th width=\"100px\">user2</th></tr>" +
                "<tr><td>&nbsp;</td><td>2-rider1</td></tr>" +
                "</table>";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }


    @Test
    void testToString_oneTeam_oneRider() {
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "1-rider1");

        String expected = "<table>" +
                "<tr><th width=\"100px\">user1</th></tr>" +
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
                "<tr><th width=\"100px\">user1</th><th width=\"100px\">user2</th></tr>" +
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
                "<tr><th width=\"100px\">user1</th><th width=\"100px\">user2</th><th width=\"100px\">user3</th></tr>" +
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
                "<tr><th width=\"100px\">user1</th></tr>" +
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
                "<tr><th width=\"100px\">user1</th><th width=\"100px\">user2</th></tr>" +
                "<tr><td>1-rider1</td><td bgcolor=\"#D7BDE2\">2-newrider1</td></tr>" +
                "<tr><td bgcolor=\"#D7BDE2\">1-newrider2</td><td>2-rider2</td></tr>" +
                "</table>";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testReplaceRiderForUsersTeam_oneTeam_oneRider_spaceInUsername() {
        usersTeams.addUser("Usery McUserFace");
        usersTeams.addRidertoUsersTeam("Usery McUserFace", "1-rider1");
        usersTeams.replaceRiderForUsersTeam("Usery McUserFace", "1-rider1", "newrider");

        String expected = "<table>" +
                "<tr><th width=\"100px\">Usery McUserFace</th></tr>" +
                "<tr><td bgcolor=\"#D7BDE2\">newrider</td></tr>" +
                "</table>";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testAlignTeams_EdgeCase_zeroTeam() {
        usersTeams.alignTeams();

        String expected = "";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testAlignTeams_oneTeam_oneRider() {
        usersTeams.addUser("Usery McUserFace");
        usersTeams.addRidertoUsersTeam("Usery McUserFace", "1-rider1");
        usersTeams.alignTeams();

        String expected = "<table>" +
                "<tr><th width=\"100px\">Usery McUserFace</th></tr>" +
                "<tr><td>1-rider1</td></tr>" +
                "</table>";

        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testAlignTeams_twoTeams_twoRiders() {
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "common");
        usersTeams.addRidertoUsersTeam("user1", "1-uncommon");
        usersTeams.addUser("user2");
        usersTeams.addRidertoUsersTeam("user2", "2-uncommon");
        usersTeams.addRidertoUsersTeam("user2", "common");
        usersTeams.alignTeams();

        String expected = "<table>" +
                "<tr><th width=\"100px\">user1</th><th width=\"100px\">user2</th></tr>" +
                "<tr><td>common</td><td>common</td></tr>" +
                "<tr><td>1-uncommon</td><td>2-uncommon</td></tr>" +
                "</table>";

        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testAlignTeams_threeTeams_fiveRiders() {
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "Roglic");
        usersTeams.addRidertoUsersTeam("user1", "Mas");
        usersTeams.addRidertoUsersTeam("user1", "Carapaz");
        usersTeams.addRidertoUsersTeam("user1", "Vine");
        usersTeams.addRidertoUsersTeam("user1", "Jai");
        usersTeams.addUser("user2");
        usersTeams.addRidertoUsersTeam("user2", "Mas");
        usersTeams.addRidertoUsersTeam("user2", "Roglic");
        usersTeams.addRidertoUsersTeam("user2", "De Gendt");
        usersTeams.addRidertoUsersTeam("user2", "Remco");
        usersTeams.addRidertoUsersTeam("user2", "Bennet");
        usersTeams.addUser("user3");
        usersTeams.addRidertoUsersTeam("user3", "Jai");
        usersTeams.addRidertoUsersTeam("user3", "Ayuso");
        usersTeams.addRidertoUsersTeam("user3", "Roglic");
        usersTeams.addRidertoUsersTeam("user3", "Remco");
        usersTeams.addRidertoUsersTeam("user3", "Pedro");
        usersTeams.alignTeams();

        String expected = "<table>" +
                "<tr><th width=\"100px\">user1</th><th width=\"100px\">user2</th><th width=\"100px\">user3</th></tr>" +
                "<tr><td>Roglic</td><td>Roglic</td><td>Roglic</td></tr>" +
                "<tr><td>Jai</td><td>&nbsp;</td><td>Jai</td></tr>" +
                "<tr><td>Mas</td><td>Mas</td><td>&nbsp;</td></tr>" +
                "<tr><td>&nbsp;</td><td>Remco</td><td>Remco</td></tr>" +
                "<tr><td>Carapaz</td><td>Bennet</td><td>Ayuso</td></tr>" +
                "<tr><td>Vine</td><td>De Gendt</td><td>Pedro</td></tr>" +
                "</table>";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testAlignTeams_transfers_threeTeams_fiveRiders() {
        // Setup
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "Roglic");
        usersTeams.addRidertoUsersTeam("user1", "Mas");
        usersTeams.addRidertoUsersTeam("user1", "Carapaz");
        usersTeams.addRidertoUsersTeam("user1", "Vine");
        usersTeams.addRidertoUsersTeam("user1", "Jai");
        usersTeams.addUser("user2");
        usersTeams.addRidertoUsersTeam("user2", "Mas");
        usersTeams.addRidertoUsersTeam("user2", "Roglic");
        usersTeams.addRidertoUsersTeam("user2", "De Gendt");
        usersTeams.addRidertoUsersTeam("user2", "Remco");
        usersTeams.addRidertoUsersTeam("user2", "Bennet");
        usersTeams.addUser("user3");
        usersTeams.addRidertoUsersTeam("user3", "Jai");
        usersTeams.addRidertoUsersTeam("user3", "Ayuso");
        usersTeams.addRidertoUsersTeam("user3", "Roglic");
        usersTeams.addRidertoUsersTeam("user3", "Remco");
        usersTeams.addRidertoUsersTeam("user3", "Pedro");
        // Change - add the transfers
        usersTeams.replaceRiderForUsersTeam("user1", "Vine", "Carthy");
        usersTeams.replaceRiderForUsersTeam("user2", "Remco", "Jai");
        usersTeams.replaceRiderForUsersTeam("user3", "Pedro", "Carthy");
        // Execute
        usersTeams.alignTeams();

        String expected = "<table>" +
                "<tr><th width=\"100px\">user1</th><th width=\"100px\">user2</th><th width=\"100px\">user3</th></tr>" +
                "<tr><td>Jai</td><td bgcolor=\"#D7BDE2\">Jai</td><td>Jai</td></tr>" +
                "<tr><td>Roglic</td><td>Roglic</td><td>Roglic</td></tr>" +
                "<tr><td bgcolor=\"#D7BDE2\">Carthy</td><td>&nbsp;</td><td bgcolor=\"#D7BDE2\">Carthy</td></tr>" +
                "<tr><td>Mas</td><td>Mas</td><td>&nbsp;</td></tr>" +
                "<tr><td>Carapaz</td><td>Bennet</td><td>Ayuso</td></tr>" +
                "<tr><td>&nbsp;</td><td>De Gendt</td><td>Remco</td></tr>" +
                "</table>";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }

    @Test
    void testAlignTeams_transfersMultiplePerUser_threeTeams_fiveRiders() {
        // Setup
        usersTeams.addUser("user1");
        usersTeams.addRidertoUsersTeam("user1", "Roglic");
        usersTeams.addRidertoUsersTeam("user1", "Mas");
        usersTeams.addRidertoUsersTeam("user1", "Carapaz");
        usersTeams.addRidertoUsersTeam("user1", "Vine");
        usersTeams.addRidertoUsersTeam("user1", "Jai");
        usersTeams.addUser("user2");
        usersTeams.addRidertoUsersTeam("user2", "Mas");
        usersTeams.addRidertoUsersTeam("user2", "Roglic");
        usersTeams.addRidertoUsersTeam("user2", "De Gendt");
        usersTeams.addRidertoUsersTeam("user2", "Remco");
        usersTeams.addRidertoUsersTeam("user2", "Bennet");
        usersTeams.addUser("user3");
        usersTeams.addRidertoUsersTeam("user3", "Jai");
        usersTeams.addRidertoUsersTeam("user3", "Ayuso");
        usersTeams.addRidertoUsersTeam("user3", "Roglic");
        usersTeams.addRidertoUsersTeam("user3", "Remco");
        usersTeams.addRidertoUsersTeam("user3", "Pedro");
        // Change - add the transfers
        usersTeams.replaceRiderForUsersTeam("user1", "Vine", "Carthy");
        usersTeams.replaceRiderForUsersTeam("user2", "Remco", "Jai");
        usersTeams.replaceRiderForUsersTeam("user3", "Pedro", "Carthy");
        usersTeams.replaceRiderForUsersTeam("user3", "Remco", "Arensman");
        // Execute
        usersTeams.alignTeams();

        String expected = "<table>" +
                "<tr><th width=\"100px\">user1</th><th width=\"100px\">user2</th><th width=\"100px\">user3</th></tr>" +
                "<tr><td>Jai</td><td bgcolor=\"#D7BDE2\">Jai</td><td>Jai</td></tr>" +
                "<tr><td>Roglic</td><td>Roglic</td><td>Roglic</td></tr>" +
                "<tr><td bgcolor=\"#D7BDE2\">Carthy</td><td>&nbsp;</td><td bgcolor=\"#D7BDE2\">Carthy</td></tr>" +
                "<tr><td>Mas</td><td>Mas</td><td>&nbsp;</td></tr>" +
                "<tr><td>Carapaz</td><td>Bennet</td><td bgcolor=\"#D7BDE2\">Arensman</td></tr>" +
                "<tr><td>&nbsp;</td><td>De Gendt</td><td>Ayuso</td></tr>" +
                "</table>";
        String actual = usersTeams.toString();

        assertEquals(expected, actual, "Expected not equals actual");
    }
}