package com.java1.employeeapi.employee;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SalaryPatchRequest(
        @NotNull @DecimalMin("0.00") BigDecimal salary
) {
}
