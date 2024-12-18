package com.bits.job.portal.enums;
import com.bits.job.portal.model.Role;

public enum ROLE {
    EMPLOYEE,
    EMPLOYER,
    ADMIN;

    public Role getRole() {
        return Role.builder().name(this).build();
    }
}
