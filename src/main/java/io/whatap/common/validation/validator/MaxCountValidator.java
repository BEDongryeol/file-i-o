package io.whatap.common.validation.validator;

import io.whatap.common.validation.annotation.MaxCount;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Copyright whatap Inc since 2023/03/17
 * Created by Larry on 2023/03/17
 * Email : inwoo.server@gmail.com
 */
public class MaxCountValidator implements ConstraintValidator<MaxCount, Integer> {

    @Override
    public boolean isValid(Integer count, ConstraintValidatorContext constraintValidatorContext) {
        return count >= 0 && count <= 1024;
    }
}
