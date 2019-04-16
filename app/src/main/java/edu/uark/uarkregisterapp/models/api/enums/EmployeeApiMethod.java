package edu.uark.uarkregisterapp.models.api.enums;

import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

public enum EmployeeApiMethod implements PathElementInterface {
    NONE(""),
    SIGN_IN("signin"),
    ACTIVE_EXISTS("activeexists"),
    BY_HIGHEST_SALES("byHighestSales");

    @Override
    public String getPathValue() {
        return value;
    }

    private String value;

    EmployeeApiMethod(String value) {
        this.value = value;
    }
}
