package com.comp3000.project.cms.BusinessLogic.Registration;

import com.comp3000.project.cms.DAC.RegApplication;

public class RegistrationStatus {
    private String error;
    private boolean successful;
    private RegApplication regApplication;

    private RegistrationStatus(RegApplication regApplication, String error, boolean successful) {
        this.error = error;
        this.successful = successful;
        this.regApplication = regApplication;
    }

    public static RegistrationStatus ok(RegApplication regApplication) {
        return new RegistrationStatus(regApplication, "", true);
    }

    public static RegistrationStatus failed(RegApplication regApplication, String error) {
        return new RegistrationStatus(regApplication, error, false);
    }

    public String getError() {
        return error;
    }
    public boolean isSuccessful() {
        return successful;
    }
}
