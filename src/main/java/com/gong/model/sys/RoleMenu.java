package com.gong.model.sys;


import com.baomidou.mybatisplus.annotations.TableName;

@TableName("role_menu")
public class RoleMenu {

    private String roleId;
    private String menuId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }



}