package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.service;

import com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.component.TransferSeleniumComponent;
import com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.model.UserTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FantasyCyclingLeagueTransferService {

    @Autowired
    private TransferSeleniumComponent transferSeleniumComponent;

    public String getTransfers() {
        // ToDo - Refactor, use POJOs and separate the HTML Wrapping.
        List<UserTransfer> usersAndTransfers = transferSeleniumComponent.getTransfers();

        // Prepare Output
        StringBuilder output = new StringBuilder("<html><head><title>Transfers</title></head><body><p>Transfers: Out -> In<br><br>");
        usersAndTransfers.forEach(userTransfer -> output.append(userTransfer).append("<br><br>"));
        output.append("</p></body></html>");

        return output.toString();
    }
}
