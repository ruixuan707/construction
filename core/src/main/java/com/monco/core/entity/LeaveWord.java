package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Auther: monco
 * @Date: 2019/5/5 02:45
 * @Description: 留言模块
 */
@Entity
@Getter
@Setter
@Table(name = "sys_leave_word")
public class LeaveWord extends BaseEntity<Long> {

    private static final long serialVersionUID = -7736133082837814109L;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 标题
     */
    private String title;

    /**
     * 留言类型 1 留言 2 报修 3 投诉
     */
    private Integer wordType;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 通过状态 0 未通过 1 已通过
     */
    private Integer passStatus;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 名字
     */
    private String nickName;

    /**
     * 回复人名字
     */
    private String replyName;

    /**
     * 留言人手机号
     */
    private String leavePhone;

    /**
     * 图片
     */
    private String pic;
}
