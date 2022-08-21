package com.lukegjpotter.tools.fantasycyclingleaguetools.teams.model;

import java.util.ArrayList;
import java.util.List;

public class UsersTeams {

    private List<List<String>> usersTeams;

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

    @Override
    public String toString() {

        StringBuilder output = new StringBuilder("<table><tr>");

        // Username
        for (List<String> usernames : usersTeams) {
            output.append("<th>").append(usernames.get(0)).append("</th>");
        }
        output.append("</tr>");

        // Riders
        for (int riderPos = 1; riderPos < usersTeams.get(0).size(); riderPos++) {
            output.append("<tr>");
            for (int teamPos = 0; teamPos < usersTeams.size(); teamPos++) {
                output.append("<td>").append(usersTeams.get(teamPos).get(riderPos)).append("</td>");
            }
            output.append("</tr>");
        }

        return output.append("</table>").toString();
    }
}
