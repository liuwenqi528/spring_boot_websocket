package com.party.build.rural.common.ensure;

/**
 *
 */
public class BooleanParam extends ParamBase<Boolean> {
    public BooleanParam(Boolean param) {
        super(param);
    }

    public ParamBase<Boolean> isTrue() {
        result = param;
        return this;
    }
}
