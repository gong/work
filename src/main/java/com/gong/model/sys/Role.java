package com.gong.model.sys;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gong.service.sys.MenuService;
import com.gong.service.sys.RoleMenuService;
import com.gong.service.sys.RoleService;
import com.gong.utils.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//表示角色
@TableName("role")
public class Role implements Serializable{
    @TableField(exist = false)
    private static final long serialVersionUID=1L;
    @TableField(exist = false)
    transient private RoleMenuService roleMenuService= SpringContextHolder.getBean(RoleMenuService.class);
    @TableField(exist = false)
    transient private MenuService menuService=SpringContextHolder.getBean(MenuService.class);
    @TableId(type = IdType.UUID,value = "role_id")
    private String roleId;//角色id
    private String name;//角色名字
    private Integer useable;//是否可用
    private String description;//角色描述
    private Integer deleteFlag;//删除标记

    @TableField(exist = false)
    private List<User>  userList=new ArrayList<>();//角色拥有的用户列表
    @TableField(exist = false)
    private List<Menu> menuList=new ArrayList<>();//角色拥有的菜单列表

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public List<Menu> getMenuList() {
        EntityWrapper<RoleMenu> ew=new EntityWrapper<>();
        ew.where("role_id={0}",roleId);
        List<RoleMenu> roleMenus=roleMenuService.selectList(ew);
        ArrayList<String> menus=new ArrayList<>();
        for (RoleMenu roleMenu:roleMenus){
            menus.add(roleMenu.getMenuId());
        }
        if (menus.size()>0)
          menuList=menuService.selectBatchIds(menus);
        return menuList;
    }

    public Integer getUseable() {
        return useable;
    }

    public void setUseable(Integer useable) {
        this.useable = useable;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId='" + roleId + '\'' +
                ", name='" + name + '\'' +
                ", useable=" + useable +
                ", description='" + description + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", userList=" + userList +
                ", menuList=" + menuList +
                '}';
    }


}
