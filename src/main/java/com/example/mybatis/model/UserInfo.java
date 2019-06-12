package com.example.mybatis.model;

import com.example.mybatis.annotation.Column;
import com.example.mybatis.annotation.ColumnType;

import java.util.Date;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/6/11 14:38
 */
public class UserInfo {
    @Column("uid")
    @ColumnType("INTEGER")
    private Integer uid;
    @Column("user_name")
    @ColumnType("VARCHAR")
    private String userName;
    @Column("user_mobile")
    @ColumnType("VARCHAR")
    private String userMobile;
    @Column("user_password")
    @ColumnType("VARCHAR")
    private String userPassword;
    @Column("del_flag")
    @ColumnType("TINYINT")
    private Boolean delFlag;
    @Column("create_time")
    @ColumnType("TIMESTAMP")
    private Date createTime;
    @Column("update_time")
    @ColumnType("TIMESTAMP")
    private Date updateTime;

    public UserInfo() {

    }

    public UserInfo(Integer uid, String userName, String userMobile, String userPassword) {
        this.uid = uid;
        this.userName = userName;
        this.userMobile = userMobile;
        this.userPassword = userPassword;
    }

    public String toString() {
        return "" + uid + "," + userName + "," + userMobile + "," + userPassword + "," + delFlag + "," + createTime + "," + updateTime;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
