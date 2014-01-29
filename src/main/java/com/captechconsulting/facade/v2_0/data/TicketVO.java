package com.captechconsulting.facade.v2_0.data;

import com.captechconsulting.facade.v2_0.validators.Email;

public class TicketVO extends com.captechconsulting.facade.v1_0.data.TicketVO {

    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
