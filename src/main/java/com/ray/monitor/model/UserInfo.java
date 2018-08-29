package com.ray.monitor.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by rui on 2018/8/12.
 */
@Entity
public class UserInfo implements Serializable {


    private static final long serialVersionUID = 4344665541172157520L;
    @Id
    //数据库在插入数据时，会自动给主键赋值
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;// 用户id

    @Column(unique = true,nullable = false)
    private String username;// 帐号

    @Column(nullable = false)
    private String realName;// 名称（昵称或者真实姓名，不同系统不同定义）

    @Column(nullable = false)
    private String password; // 密码;

    @Column(nullable = false)
    private String salt;// 加密密码的盐

    @Column(nullable = false)
    private String mobile;

    private String email;

    private Date generationTime;


    @ManyToOne(fetch = FetchType.EAGER) // 立即从数据库中进行加载数据
    @JoinTable(name = "UserRoleMap", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = {
            @JoinColumn(name = "roleId") })
    private RoleInfo roleInfo;// 一个用户具有多个角色

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "UserAreaMap", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = {
            @JoinColumn(name = "areaId  ") })
    private Area area;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public RoleInfo getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(RoleInfo roleInfo) {
        this.roleInfo = roleInfo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getGenerationTime() {
        return generationTime;
    }

    public void setGenerationTime(Date generationTime) {
        this.generationTime = generationTime;
    }

    public String getCredentialsSalt() {
        return this.username + this.salt;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

}