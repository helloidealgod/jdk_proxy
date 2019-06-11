package com.example.mybatis;

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

    public UserInfo(){

    }
    public UserInfo(Integer uid,String userName,String userMobile,String userPassword){
        this.uid = uid;
        this.userName = userName;
        this.userMobile = userMobile;
        this.userPassword = userPassword;
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
}
