package com.gong.controller.sys;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gong.controller.BaseController;
import com.gong.model.sys.Menu;
import com.gong.service.sys.MenuService;
import com.gong.shiro.AuthRealm;
import com.gong.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping(value = "/sys/menu")
public class MenuController extends BaseController{
    @Autowired
    MenuService menuService;
    @Autowired
    private AuthRealm authRealm;
    @RequiresPermissions("user")
    @RequestMapping(value = "tree")
    public String tree() {
        return "sys/menuTree";
    }

    @RequiresPermissions("sys:menu:view")
    @RequestMapping("list")
    public String list(){
        UserUtils.removeCache("menuList");//清除菜单的缓存
        return "sys/menuList";
    }

    @RequiresPermissions("sys:menu:edit")
    @RequestMapping("add")
    public String add(Menu menu,@RequestParam(value = "id",required = false)String id,Model model, RedirectAttributes redirectAttributes){
        try {
            if (StringUtils.isNoneBlank(menu.getName())){
                EntityWrapper<Menu> ew=new EntityWrapper<>();
                ew.where("name={0}",menu.getName()).and("url={0}",menu.getUrl());
                if(menuService.selectOne(ew)==null){
                    menu.setDeleteFlag(0);
                    if (menu.getIsShow()==null)
                        menu.setIsShow(0);
                    menuService.insert(menu);
                    redirectAttributes.addFlashAttribute("message","保存成功！");
                    return "redirect:/sys/menu/list";
                }else{
                    model.addAttribute("message","已经存在这个菜单名或者链接！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message","保存失败！");
        }
        return "sys/menuAdd";
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value="update")
    public String update(Menu menu,@RequestParam(value = "id",required = false)String id,Model model,RedirectAttributes redirectAttributes){
        try {
            if (StringUtils.isNoneBlank(id)){
                menu=menuService.selectById(id);
                model.addAttribute("menu",menu);
            }else if (menu!=null){
                menuService.updateById(menu);
                redirectAttributes.addFlashAttribute("message","保存成功！");
                return "redirect:/sys/menu/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message","保存失败！");
        }
        return "sys/menuUpdate";
    }

    @RequiresPermissions("sys:menu:edit")
    @RequestMapping("delete")
    public String delete(@RequestParam(value = "id")String id,RedirectAttributes redirectAttributes){
        Menu menu=menuService.selectById(id);
        menu.setDeleteFlag(1);
        menuService.updateById(menu);
        authRealm.clearCached();
        UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/sys/menu/list";
    }
}
