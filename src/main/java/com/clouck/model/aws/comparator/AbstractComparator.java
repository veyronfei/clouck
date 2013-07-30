package com.clouck.model.aws.comparator;

import java.util.Objects;

import org.apache.commons.lang.Validate;

public abstract class AbstractComparator {

    protected void validateNotEqual(Object o1, Object o2) {
        Validate.isTrue(!Objects.equals(o1, o2));
    }
}
