package io.file.common.validation.annotation;

import io.file.common.validation.validator.MaxCountValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright whatap Inc since 2023/03/17
 * Created by Larry on 2023/03/17
 * Email : inwoo.server@gmail.com
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxCountValidator.class)
public @interface MaxCount {

    String message() default "1024개 이하의 요청만 가능합니다.";

    Class[] groups() default {};

    Class[] payload() default {};

}
