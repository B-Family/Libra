package com.bfamily.assets.wrappers;

import com.bfamily.beans.ResponseBean;
import org.springframework.http.ResponseEntity;

public class ResponseEntityWrapper<T> extends ResponseEntity<T>
{
    public ResponseEntityWrapper(ResponseBean responseBean)
    {
        super((T) responseBean, responseBean.getHeaders(), responseBean.getStatus());
    }
}