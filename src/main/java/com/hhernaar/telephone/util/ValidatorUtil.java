package com.hhernaar.telephone.util;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.hhernaar.telephone.exception.BadRequestException;

/**
 * Utility Class to validate Objects
 */
@Component
public class ValidatorUtil {

  @Autowired
  private Validator validator;

  /**
   * Generic method that calls spring validator.
   * 
   * @param target The object target to validate.
   * @param clazzName The class name of the object target.
   * @throws BadRequestException If errors are present.
   * 
   * @author hhernaar.
   * @since v1.
   */
  public void validate(Object target, String clazzName) throws BadRequestException {
    Errors errors = new BeanPropertyBindingResult(target, clazzName);
    validator.validate(target, errors);
    if (errors.hasErrors()) {
      throw new BadRequestException(errors.getFieldErrors().stream()
          .map(field -> field.getField() + " : " + field.getDefaultMessage())
          .collect(Collectors.toList()));
    }
  }

}
