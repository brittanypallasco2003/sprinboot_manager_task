package com.brittany.sprinboot_manager_task.validators;

import com.brittany.sprinboot_manager_task.validators.annotations.IsExistEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistByEmailValidation implements ConstraintValidator<IsExistEmail, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValid'");
    }

}
