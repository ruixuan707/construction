package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Auther: monco
 * @Date: 2019/4/26 00:03
 * @Description: 公告消息管理模块
 */
@Entity
@Getter
@Setter
@Table(name = "sys_message")
public class Message extends BaseEntity<Long> {

    private static final long serialVersionUID = 1215455638632774015L;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 图片
     */
    private String pic;

    /**
     * 标题
     */
    private String title;

    /**
     * 消息类型 0 公告 1 新闻 2 消息
     */
    private Integer messageType;

    /**
     * 新闻资讯类型  1 物业新闻 2 地产新闻 3 行业动态 4 美好期刊 5 电商资讯 6 媒体报道
     */
    private Integer category;

    /**
     * 起始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 名字
     */
    private String nickName;

    /**
     * 点击数量
     */
    private Integer clickNumber;

}
