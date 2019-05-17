package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Auther: monco
 * @Date: 2019/5/17 13:02
 * @Description: 薪水
 */
@Entity
@Getter
@Setter
@Table(name = "sys_salary")
public class Salary extends BaseEntity<Long> {

    private static final long serialVersionUID = -987971862516492408L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 薪水
     */
    private BigDecimal salary;

    /**
     * 名称
     */
    private String nickName;

    /**
     * 年
     */
    private Integer year;

    /**
     * 月
     */
    private Integer month;

    /**
     * 是否结算
     */
    private Integer settleStatus;
}
