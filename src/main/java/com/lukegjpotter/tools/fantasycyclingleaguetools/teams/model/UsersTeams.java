package com.lukegjpotter.tools.fantasycyclingleaguetools.teams.model;

import java.util.*;

public class UsersTeams {

    private List<List<String>> usersTeams;
    private static final String TRANSFER_SIGNAL = "tx->";

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
            output.append("<th>").append(username).append("</th>");
        }
        output.append("</tr>");

        // Riders
        int maxSizeOfTeam = 0;
        // usersTeams.get(0).size()
        for (List<String> userTeam : usersTeams) {
            int sizeOfCurrentTeam = userTeam.size();
            if (sizeOfCurrentTeam > maxSizeOfTeam) maxSizeOfTeam = sizeOfCurrentTeam;
        }

        for (int riderPos = 1; riderPos < maxSizeOfTeam; riderPos++) {
            output.append("<tr>");
            for (int teamPos = 0; teamPos < usersTeams.size(); teamPos++) {
                String ridername = "";
                try {
                    ridername = usersTeams.get(teamPos).get(riderPos);
                } catch (IndexOutOfBoundsException ioobException) {
                    // No need to do anything, as ridername is already empty;
                }
                if (ridername.startsWith(TRANSFER_SIGNAL)) {
                    ridername = ridername.substring(TRANSFER_SIGNAL.length());
                    output.append("<td bgcolor=\"#D7BDE2\">");
                } else {
                    output.append("<td>");
                }
                output.append(ridername).append("</td>");
            }
            output.append("</tr>");
        }

        return output.append("</table>").toString();
    }

    public void alignTeams() {
        if (usersTeams.isEmpty()) return;

        Map<String, String> transferRecordsMap = new HashMap<>();
        Map<String, Integer> riderToOccurrencesMap = new TreeMap<>();

        /* Count Unique Riders and count Occurrences
         * Extract the TRANSFER_SIGNAL, store records and reapply them later. */
        for (List<String> userTeam : usersTeams) {
            for (int riderPos = 1; riderPos < userTeam.size(); riderPos++) {
                String riderName = userTeam.get(riderPos);

                if (riderName.startsWith(TRANSFER_SIGNAL)) {
                    riderName = riderNameWithoutTransferSignal(riderName);
                    transferRecordsMap.put(userTeam.get(0), riderName);
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
                            alignedUsersTeams.addRidertoUsersTeam(username, "");
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
        transferRecordsMap.keySet().forEach(username -> alignedUsersTeams.replaceRiderForUsersTeam(username, transferRecordsMap.get(username), transferRecordsMap.get(username)));

        // Reassign the AlignedUsers teams.
        usersTeams = alignedUsersTeams.usersTeams;
    }

    private String riderNameWithoutTransferSignal(String rider) {
        if (rider.startsWith(TRANSFER_SIGNAL)) {
            return rider.substring(TRANSFER_SIGNAL.length());
        }
        return rider;
    }
}
