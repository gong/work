package com.gong.model.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

//菜单资源
@TableName("menu")
public class Menu implements Serializable{
    @TableField(exist = false)
    private static final long serialVersionUID=1L;
    @TableId(type = IdType.UUID,value = "menu_id")
    private String menuId;//菜单编号
    private String parentId;//父级编号
    private String name;//名字
    private String icon;//图标
    private String url;//链接
    private String target;//_blank mainFrame
    private Integer sort;//排序
    private Integer isShow;//是否在菜单中显示1 显示 0 不显示
    private String permission;//权限标识
    private Integer deleteFlag;//删除标识


    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (menuId != null ? !menuId.equals(menu.menuId) : menu.menuId != null) return false;
        if (parentId != null ? !parentId.equals(menu.parentId) : menu.parentId != null) return false;
        if (name != null ? !name.equals(menu.name) : menu.name != null) return false;
        if (icon != null ? !icon.equals(menu.icon) : menu.icon != null) return false;
        if (url != null ? !url.equals(menu.url) : menu.url != null) return false;
        if (target != null ? !target.equals(menu.target) : menu.target != null) return false;
        if (sort != null ? !sort.equals(menu.sort) : menu.sort != null) return false;
        if (isShow != null ? !isShow.equals(menu.isShow) : menu.isShow != null) return false;
        if (permission != null ? !permission.equals(menu.permission) : menu.permission != null) return false;
        return deleteFlag != null ? deleteFlag.equals(menu.deleteFlag) : menu.deleteFlag == null;
    }

    @Override
    public int hashCode() {
        int result = menuId != null ? menuId.hashCode() : 0;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (isShow != null ? isShow.hashCode() : 0);
        result = 31 * result + (permission != null ? permission.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuId='" + menuId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", target='" + target + '\'' +
                ", sort=" + sort +
                ", isShow=" + isShow +
                ", permission='" + permission + '\'' +
                ", deleteFlag=" + deleteFlag +
                '}';
    }
}
