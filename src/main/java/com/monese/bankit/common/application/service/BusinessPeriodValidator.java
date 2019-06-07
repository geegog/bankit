package com.monese.bankit.common.application.service;

import com.monese.bankit.common.domain.BusinessPeriod;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Service
public class BusinessPeriodValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return BusinessPeriod.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BusinessPeriod bp = (BusinessPeriod) o;

        if(bp.getStartDate() == null)
            errors.rejectValue("startDate", "startDate cannot be null");
        if(bp.getEndDate() == null)
            errors.rejectValue("endDate", "endDate cannot be null");
        if(bp.getStartDate() != null && bp.getEndDate() != null) {
            if (!bp.getStartDate().isBefore(bp.getEndDate()))
                errors.rejectValue("startDate", "startDate must ocurrs before endDate");
            if (bp.getStartDate().isBefore(LocalDate.now()))
                errors.rejectValue("startDate", "startDate must be in the future");
            if (bp.getEndDate().isBefore(LocalDate.now()))
                errors.rejectValue("endDate", "endDate must be in the future");
        }
    }
}
