package com.brittany.sprinboot_manager_task.validators;

import com.brittany.sprinboot_manager_task.services.UserServiceI;
import com.brittany.sprinboot_manager_task.validators.annotations.IsExistEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistByEmailValidation implements ConstraintValidator<IsExistEmail, String> {

    private final UserServiceI userService;
    
    public ExistByEmailValidation(UserServiceI userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userService!=null) {
            return !userService.existsByEmail(value);
        }
        return true;
        
    }

}
