package com.monco.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Auther: monco
 * @Date: 2019/3/24 16:26
 * @Description:
 */
@Data
@Entity
@Table(name = "sys_user")
public class User extends BaseEntity<Long> {

    private static final long serialVersionUID = 5064674569927783963L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证
     */
    private String identityCode;

    /**
     * 手机号
     */
    private String phoneCode;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 出生地点
     */
    private String address;

    /**
     * 照片
     */
    private String pic;

    /**
     * 微信号
     */
    private String weChatCode;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户类型 0 普通用户 1 物业管理员  2 管理员
     */
    private Integer userType;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 血型
     */
    private String blood;

    /**
     * 教育程度
     */
    private String education;

    /**
     * 工作
     */
    private String job;
    private Long jobId;

    /**
     * 个性签名
     */
    private String signature;

}
