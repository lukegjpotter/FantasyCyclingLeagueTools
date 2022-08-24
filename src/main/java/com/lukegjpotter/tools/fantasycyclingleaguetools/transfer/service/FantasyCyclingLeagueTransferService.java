package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.service;

import com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.component.TransferSeleniumComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FantasyCyclingLeagueTransferService {

    @Autowired
    private TransferSeleniumComponent transferSeleniumComponent;

    public String getTransfers() {
        // ToDo - Refactor, use POJOs and separate the HTML Wrapping.
        return transferSeleniumComponent.getTransfers();
    }
}
