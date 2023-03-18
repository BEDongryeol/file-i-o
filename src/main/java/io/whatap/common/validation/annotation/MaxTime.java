package io.whatap.common.validation.annotation;

import io.whatap.common.validation.validator.MaxTimeValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright whatap Inc since 2023/03/18
 * Created by Larry on 2023/03/18
 * Email : inwoo.server@gmail.com
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxTimeValidator.class)
public @interface MaxTime {

    String message() default "현재 시간 이후의 로그는 조회할 수 없습니다.";

    Class[] groups() default {};

    Class[] payload() default {};
}
