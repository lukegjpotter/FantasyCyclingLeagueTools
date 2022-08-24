package com.lukegjpotter.tools.fantasycyclingleaguetools.teams.component;

import com.lukegjpotter.tools.fantasycyclingleaguetools.teams.model.UsersTeams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TransferMergeComponent {

    private final Logger logger = LoggerFactory.getLogger(TransferMergeComponent.class);

    public UsersTeams mergeTransfersIntoUsersTeams(String transfersHtmlSource, UsersTeams usersTeams) {
        logger.info("Merging Transfers");

        int substringStart = "<html><head><title>Transfers</title></head><body><p>Transfers: Out -> In<br><br>".length() + 1;
        int substringEnd = transfersHtmlSource.length() - "</p></body></html>".length();
        transfersHtmlSource = transfersHtmlSource.substring(substringStart, substringEnd);
        transfersHtmlSource = transfersHtmlSource.replace("<br>", "\n");
        Scanner transfersScanner = new Scanner(transfersHtmlSource);
        String username = "", riderOut = "", riderIn = "";

        while (transfersScanner.hasNextLine()) {

            String line = transfersScanner.nextLine();

            if (line.contains("(")) {
                username = line.split(" ")[0].trim();
            } else if (line.contains("->")) {
                riderOut = line.split("->")[0].trim();
                riderIn = line.split("->")[1].trim();
            }

            if (!username.isEmpty() && !riderOut.isEmpty() && !riderIn.isEmpty()) {
                usersTeams.replaceRiderForUsersTeam(username, riderOut, riderIn);
                riderOut = "";
                riderIn = "";
            }
        }
        transfersScanner.close();
        logger.info("Finished Merging Transfers");

        return usersTeams;
    }
}
