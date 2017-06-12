package com.cinema.view;

import com.cinema.model.Ticket;
import javafx.scene.layout.VBox;

/**
 * Created by Dominik on 04.06.2017.
 * Simple class require to connect Ticket object with view.
 */
public class TicketView {

    private VBox layout;
    private Ticket ticket;

    public VBox getLayout() {
        return layout;
    }

    public void setLayout(VBox layout) {
        this.layout = layout;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
