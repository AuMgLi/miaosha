package com.geekq.miaosha.exception;

import com.geekq.miaosha.common.enums.ResultStatus;

public class GlobalException extends RuntimeException {


    private ResultStatus status;

    public GlobalException(ResultStatus status){
        super();
        this.status = status;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }
}
