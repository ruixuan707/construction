package com.monco.core.page;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: monco
 * @Date: 2019/5/18 00:21
 * @Description:
 */
@Data
public class ErrorPage implements Serializable {

    private static final long serialVersionUID = -6329737020568010423L;

    private String errorMsg;

}
