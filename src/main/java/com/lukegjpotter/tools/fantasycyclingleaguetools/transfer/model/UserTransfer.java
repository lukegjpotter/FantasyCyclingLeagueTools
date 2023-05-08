package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserTransfer {

    private final String username;
    private int totalUsedTransfers;
    private final List<String> transfers;
    private static final int MAX_TRANSFERS = 45;

    public UserTransfer(String username) {
        this.username = username;
        totalUsedTransfers = 0;
        this.transfers = new ArrayList<>();
    }

    public void setTotalUsedTransfers(int totalUsedTransfers) {
        this.totalUsedTransfers = totalUsedTransfers;
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
        int remainingTransfers = MAX_TRANSFERS - totalUsedTransfers;

        return ("<strong>" + username + "</strong><br>"
                + "Today: " + transfers.size() + " | Remaining: " + remainingTransfers + "<br>"
                + transfersAsString).trim();
    }
}
