package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.service;

import com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.component.TransferSeleniumComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FantasyCyclingLeagueTransferService {

    @Autowired
    TransferSeleniumComponent transferSeleniumComponent;

    public String getTransfers() {
        return transferSeleniumComponent.getTransfers();
    }
}
