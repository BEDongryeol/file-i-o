package io.file.common.validation.validator;

import io.file.common.util.DateTimeUtil;
import io.file.common.validation.annotation.MaxTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Copyright whatap Inc since 2023/03/18
 * Created by Larry on 2023/03/18
 * Email : inwoo.server@gmail.com
 */
public class MaxTimeValidator implements ConstraintValidator<MaxTime, Long> {

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return aLong <= DateTimeUtil.getNowUnixTime();
    }

}
