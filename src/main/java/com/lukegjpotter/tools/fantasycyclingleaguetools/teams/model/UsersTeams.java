package com.lukegjpotter.tools.fantasycyclingleaguetools.teams.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * UserTeams.java
 *
 * @Author Luke GJ Potter - @lukegjpotter
 * <p>
 * This class handles the User Teams for the Teams Endpoint.
 * Ensure that alignTeams is called, before the toString method is called.
 */
public class UsersTeams {

    private List<List<String>> usersTeams;
    private static final String TRANSFER_SIGNAL = "tx->";
    private final String htmlEncodedSpace = "&nbsp;";

    private final Logger logger = LoggerFactory.getLogger(UsersTeams.class);

    public UsersTeams() {
        usersTeams = new ArrayList<>();
    }

    public void addUser(String username) {

        List<String> user = new ArrayList<>();
        user.add(username);
        usersTeams.add(user);
    }

    public boolean addRidertoUsersTeam(String username, String ridername) {

        for (List<String> team : usersTeams) {
            if (team.get(0).equals(username)) {
                team.add(ridername);
                return true;
            }
        }
        return false;
    }

    public boolean replaceRiderForUsersTeam(String username, String riderOut, String riderIn) {
        for (List<String> team : usersTeams) {
            if (team.get(0).equalsIgnoreCase(username)) {
                for (int i = 1; i < team.size(); i++) {
                    if (team.get(i).equals(riderOut)) {
                        team.set(i, TRANSFER_SIGNAL + riderIn);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isRiderInUsersTeam(String username, String riderName) {
        for (List<String> team : usersTeams) {
            if (team.get(0).equalsIgnoreCase(username)) {
                for (int i = 1; i < team.size(); i++) {
                    if (riderNameWithoutTransferSignal(team.get(i)).equals(riderName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<String> getUsernames() {
        List<String> usernames = new ArrayList<>();
        usersTeams.forEach(teams -> usernames.add(teams.get(0)));

        return usernames;
    }

    @Override
    public String toString() {
        if (usersTeams.isEmpty()) return "";

        StringBuilder output = new StringBuilder("<table>");

        // Usernames - header row
        output.append("<tr>");
        for (String username : getUsernames()) {
            output.append("<th width=\"100px\">").append(username).append("</th>");
        }
        output.append("</tr>");

        // Riders
        int maxSizeOfTeam = 0;
        for (List<String> userTeam : usersTeams) {
            int sizeOfCurrentTeam = userTeam.size();
            if (sizeOfCurrentTeam > maxSizeOfTeam) maxSizeOfTeam = sizeOfCurrentTeam;
        }

        for (int riderPos = 1; riderPos < maxSizeOfTeam; riderPos++) {
            output.append("<tr>");
            for (List<String> usersTeam : usersTeams) {
                String riderName;
                try {
                    riderName = usersTeam.get(riderPos);
                } catch (IndexOutOfBoundsException ioobException) {
                    // No need to do anything, as riderName is already empty;
                    riderName = htmlEncodedSpace;
                }
                if (riderName.startsWith(TRANSFER_SIGNAL)) {
                    riderName = riderName.substring(TRANSFER_SIGNAL.length());
                    output.append("<td bgcolor=\"#D7BDE2\">");
                } else {
                    output.append("<td>");
                }
                output.append(riderName).append("</td>");
            }
            output.append("</tr>");
        }

        return output.append("</table>").toString();
    }

    public void alignTeams() {
        logger.info("Aligning Teams");

        if (usersTeams.isEmpty()) {
            logger.info("Team list is empty, finishing.");
            return;
        }

        Map<String, List<String>> transferRecordsMap = new HashMap<>();
        Map<String, Integer> riderToOccurrencesMap = new TreeMap<>();

        /* Count Unique Riders and count Occurrences
         * Extract the TRANSFER_SIGNAL, store records and reapply them later. */
        for (List<String> userTeam : usersTeams) {
            for (int riderPos = 1; riderPos < userTeam.size(); riderPos++) {
                String riderName = userTeam.get(riderPos);

                if (riderName.startsWith(TRANSFER_SIGNAL)) {
                    riderName = riderNameWithoutTransferSignal(riderName);
                    String username = userTeam.get(0);

                    if (!transferRecordsMap.containsKey(username)) {
                        // create the list in the record.
                        transferRecordsMap.put(username, new ArrayList<>());
                    }
                    transferRecordsMap.get(username).add(riderName);
                }

                int currentOccurrencesOfRider = 0;
                if (riderToOccurrencesMap.containsKey(riderName)) {
                    currentOccurrencesOfRider = riderToOccurrencesMap.get(riderName);
                }
                riderToOccurrencesMap.put(riderName, ++currentOccurrencesOfRider);
            }
        }

        // Init the alignedUsersTeams.
        UsersTeams alignedUsersTeams = new UsersTeams();
        getUsernames().forEach(alignedUsersTeams::addUser);

        /* If value (currentOccurrencesOfRider) in EntrySet = numberOfTeamsRiderIsIn countdown.
         * Then, using the isRiderInUsersTeam, add to each alignedUsersTeams, ensure to printout empty columns.
         * When numberOfTeamsRiderIsIn is 1, print them all out without spaces beside them. */
        for (int numberOfTeamsRiderIsIn = usersTeams.size(); numberOfTeamsRiderIsIn > 1; numberOfTeamsRiderIsIn--) {
            for (Map.Entry<String, Integer> riderToOccurrencesEntry : riderToOccurrencesMap.entrySet()) {
                if (riderToOccurrencesEntry.getValue() == numberOfTeamsRiderIsIn) {
                    String riderName = riderToOccurrencesEntry.getKey();

                    for (String username : getUsernames()) {
                        if (isRiderInUsersTeam(username, riderName)) {
                            alignedUsersTeams.addRidertoUsersTeam(username, riderName);
                        } else {
                            alignedUsersTeams.addRidertoUsersTeam(username, htmlEncodedSpace);
                        }
                    }
                }
            }
        }
        for (Map.Entry<String, Integer> riderToOccurrencesEntry : riderToOccurrencesMap.entrySet()) {
            if (riderToOccurrencesEntry.getValue() == 1) {
                String riderName = riderToOccurrencesEntry.getKey();

                for (String username : getUsernames()) {
                    if (isRiderInUsersTeam(username, riderName)) {
                        alignedUsersTeams.addRidertoUsersTeam(username, riderName);
                    }
                }
            }
        }

        // Reapply the TRANSFER_SIGNAL.
        for (String username : transferRecordsMap.keySet()) {
            for (String riderName : transferRecordsMap.get(username)) {
                alignedUsersTeams.replaceRiderForUsersTeam(username, riderName, riderName);
            }
        }

        // Reassign the AlignedUsers teams.
        usersTeams = alignedUsersTeams.usersTeams;
        logger.info("Finished Aligning Teams");
    }

    private String riderNameWithoutTransferSignal(String rider) {
        if (rider.startsWith(TRANSFER_SIGNAL)) {
            return rider.substring(TRANSFER_SIGNAL.length());
        }
        return rider;
    }
}
