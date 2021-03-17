package com.comp3000.project.cms.BusinessLogic.Registration;

import com.comp3000.project.cms.DAC.RegApplication;

public abstract class RegistrationHandler {
    protected RegistrationHandler next;

    void setNext(RegistrationHandler next) {
        this.next = next;
    }

    public abstract RegistrationStatus handle(RegApplication ap);
}
