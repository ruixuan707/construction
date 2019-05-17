package com.monco.core.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.monco.core.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Auther: monco
 * @Date: 2019/5/17 10:44
 * @Description: 考勤记录
 */
@Entity
@Getter
@Setter
@Table(name = "sys_attendance")
public class Attendance extends BaseEntity<Long> {

    private static final long serialVersionUID = -1657740909119952522L;

    /**
     * 年份
     */
    @Excel(name = "年份", orderNum = "1")
    private Integer year;

    /**
     * 月份
     */
    @Excel(name = "月份", orderNum = "2")
    private Integer month;

    /**
     * 出勤天数
     */
    @Excel(name = "出勤天数", orderNum = "3")
    private Integer attendDays;

    /**
     * 绑定用户
     */
    @Excel(name = "工号", orderNum = "4")
    private Long userId;

    /**
     * 登录名
     */
    private String username;

    /**
     * 昵称
     */
    @Excel(name = "姓名", orderNum = "5")
    private String nickName;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 是否结算
     */
    private Integer settleStatus;

    /**
     * 薪水
     */
    private BigDecimal salary;
}
