package com.gong.model.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



//表示用户
@TableName("user")
public class User implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID,value = "user_id")
    private String userId;//用户id
    private String loginName;//登录名
    private String name;//用户名
    private String gender;//性别
    private Date birthDate;//出生日期
    private String password;//密码
    private String phone;//电话
    private Integer deleteFlag;//删除标记
    private String extraMenuId;//除了角色下的菜单 额外拥有的菜单

    @TableField(exist = false)
    private List<Role> roleList=new ArrayList<>();//用户拥有的角色

    public String getExtraMenuId() {
        return extraMenuId;
    }

    public void setExtraMenuId(String extraMenuId) {
        this.extraMenuId = extraMenuId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public boolean isAdmin(){
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(String id){
        return id != null && "1".equals(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", extraMenuId='" + extraMenuId + '\'' +
                ", roleList=" + roleList +
                '}';
    }

}
