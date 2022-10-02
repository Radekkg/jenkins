package com.capgemini.pdp.controller.common;

import java.time.Instant;

public class Error {

    private Instant date;

    private String errorMessage;

    public Error(final String errorMessage) {
        this.date = Instant.now();
        this.errorMessage = errorMessage;
    }

    public Instant getDate() {
        return this.date;
    }

    public void setDate(final Instant date) {
        this.date = date;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
