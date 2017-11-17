package com.gong.controller.sys;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gong.controller.BaseController;
import com.gong.model.sys.Menu;
import com.gong.model.sys.RoleMenu;
import com.gong.model.sys.Role;
import com.gong.service.sys.RoleMenuService;
import com.gong.service.sys.RoleService;
import com.gong.shiro.AuthRealm;
import com.gong.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController{
    @Autowired
    RoleService roleService;
    @Autowired
    private AuthRealm authRealm;
    @Autowired
    RoleMenuService roleMenuService;
    @RequiresPermissions("sys:role:view")
    @RequestMapping("list")
    public String list(Role role,@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
    @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
    Model model){
        Page<Role> page=new Page<>(pageNo,pageSize);
        EntityWrapper<Role> ew=new EntityWrapper<>();
        ew.and("delete_flag={0}",0);
        if (StringUtils.isNoneBlank(role.getName()))
            ew.where("name={0}",role.getName());
        page=roleService.selectPage(page,ew);
        model.addAttribute("page",page);
        return "sys/roleList";
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value="add")
    public String add(Role role,@RequestParam(value = "menus",required = false)String[] menus,Model model,RedirectAttributes redirectAttributes){
        try {
            if (StringUtils.isNoneBlank(role.getName())){
               EntityWrapper<Role> ew=new EntityWrapper<>();
               ew.where("name={0}",role.getName());
               if(roleService.selectOne(ew)==null){
                   role.setDeleteFlag(0);
                   if (role.getUseable()==null)
                       role.setUseable(0);
                   roleService.insert(role);
                   ArrayList<RoleMenu> roleMenus=new ArrayList<>();
                   if(menus!=null){
                       for (String menuId:menus){
                           RoleMenu roleMenu=new RoleMenu();
                           roleMenu.setRoleId(role.getRoleId());
                           roleMenu.setMenuId(menuId);
                           roleMenus.add(roleMenu);
                       }
                       roleMenuService.insertBatch(roleMenus);
                   }
                   redirectAttributes.addFlashAttribute("message","保存成功！");
                   return "redirect:/sys/role/list";
               }else{
                   model.addAttribute("message","已经存在这个角色！");
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message","保存失败！");
        }
        return "sys/roleAdd";
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value="update")
    public String update(Role role,@RequestParam(value = "id",required = false)String id,@RequestParam(value = "menus",required = false)String[] menus,Model model,RedirectAttributes redirectAttributes){
        try {
              if (StringUtils.isNoneBlank(id)){
                  role=roleService.selectById(id);
                  if (role!=null){
                      ArrayList<String> menuStr=new ArrayList<>();
                      List<Menu> menuList=role.getMenuList();
                      if (menuList.size()>0){
                        for (Menu menu:menuList){
                            menuStr.add(menu.getName());
                        }
                      }
                      model.addAttribute("menuStr",menuStr);
                  }
                  model.addAttribute("role",role);
              }else if(role!=null){
                  roleService.updateById(role);
                  ArrayList<RoleMenu> roleMenus=new ArrayList<>();
                  EntityWrapper<RoleMenu> ew=new EntityWrapper<>();
                  ew.where("role_id={0}",role.getRoleId());
                  roleMenuService.delete(ew);
                  if(menus!=null){
                      for (String menuId:menus){
                          RoleMenu roleMenu=new RoleMenu();
                          roleMenu.setRoleId(role.getRoleId());
                          roleMenu.setMenuId(menuId);
                          roleMenus.add(roleMenu);
                      }
                      roleMenuService.insertBatch(roleMenus);
                  }
                  UserUtils.removeCache("roleList");
                  redirectAttributes.addFlashAttribute("message","保存成功！");
                  return "redirect:/sys/role/list";
              }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message","保存失败！");
        }
        return "sys/roleUpdate";
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping("delete")
    public String delete(@RequestParam(value = "id")String id,RedirectAttributes redirectAttributes){
        Role role=roleService.selectById(id);
        role.setDeleteFlag(1);
        roleService.updateById(role);
        authRealm.clearCached();
        UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/sys/role/list";
    }
}
