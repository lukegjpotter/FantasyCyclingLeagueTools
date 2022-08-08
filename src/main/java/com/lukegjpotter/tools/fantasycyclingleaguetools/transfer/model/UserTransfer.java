package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.model;

import java.util.ArrayList;
import java.util.List;

public class UserTransfer {

    private final String username;
    private final List<String> transfers;

    public UserTransfer(String username) {
        this.username = username;
        this.transfers = new ArrayList<>();
    }

    public void addTransfer(String transfer) {
        transfers.add(transfer);
    }

    public boolean hasTransfers() {
        return !transfers.isEmpty();
    }

    @Override
    public String toString() {

        StringBuilder transfersAsString = new StringBuilder();
        if (hasTransfers()) transfers.forEach(transfer -> transfersAsString.append(transfer).append("<br>"));

        return (username + " (" + transfers.size() + ")<br>" + transfersAsString).trim();
    }
}
