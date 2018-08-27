package com.party.build.rural.common.ensure;

import com.party.build.rural.common.exception.ArgumentException;
import com.party.build.rural.common.exception.DaoException;
import com.party.build.rural.common.exception.ServiceException;
import com.party.build.rural.common.exception.StateException;
import com.party.build.rural.common.exception.SystemException;
import com.party.build.rural.common.utils.SpringUtils;

/**
 *
 */
public abstract class ParamBase<T> {

    protected T param;

    //断言的最终结果，如果是false则抛出对应的异常
    protected boolean result;

    public ParamBase(T param) {
        this.param = param;
    }

    public void orThrowArgumentException(String key, String... params) {
        if (!result) {
            throw new ArgumentException(SpringUtils.getMessage(key, params));
        }
    }

    public void orThrowStateException(String key, String... params) {
        if (!result) {
            throw new StateException(SpringUtils.getMessage(key, params));
        }
    }

    public void orThrowSecurityException(String key, String... params) {
        if (!result) {
            throw new SecurityException(SpringUtils.getMessage(key, params));
        }
    }

    public void orThrowDaoException(String key, String... params) {
        if (!result) {
            throw new DaoException(SpringUtils.getMessage(key, params));
        }
    }

    public void orThrowServiceException(String key, String... params) {
        if (!result) {
            throw new ServiceException(SpringUtils.getMessage(key, params));
        }
    }

    public void orThrowSystemException(String key, String... params) {
        if (!result) {
            throw new SystemException(SpringUtils.getMessage(key, params));
        }
    }

    /**
     * 转换参数
     * @param message 消息
     * @param params 参数
     * @return 转换后的消息
     */
    private String translateErrorMsg(String message, Object... params) {
        return String.format(message, params);
    }
}
