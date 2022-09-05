package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserTransfer {

    private final String username;
    private int usedTransfers;
    private final List<String> transfers;
    private static final int MAX_TRANSFERS = 45;

    public UserTransfer(String username) {
        this.username = username;
        usedTransfers = 0;
        this.transfers = new ArrayList<>();
    }

    public void setUsedTransfers(int usedTransfers) {
        this.usedTransfers = usedTransfers;
    }

    public void addTransfer(String transfer) {
        transfers.add(transfer);
    }

    public boolean hasTransfers() {
        return !transfers.isEmpty();
    }

    public void reverseTransferOrder() {
        Collections.reverse(transfers);
    }

    @Override
    public String toString() {

        StringBuilder transfersAsString = new StringBuilder();
        if (hasTransfers()) transfers.forEach(transfer -> transfersAsString.append(transfer).append("<br>"));
        int remainingTransfers = MAX_TRANSFERS - usedTransfers;

        return ("<strong>" + username + "</strong><br>"
                + "Today: " + transfers.size() + " | Remaining: " + remainingTransfers + "<br>"
                + transfersAsString).trim();
    }
}
