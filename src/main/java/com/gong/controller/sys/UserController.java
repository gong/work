package com.gong.controller.sys;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gong.controller.BaseController;
import com.gong.model.sys.User;
import com.gong.service.sys.UserService;
import com.gong.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/sys/user")
public class UserController extends BaseController{
    @Autowired
    UserService userService;

    @RequiresPermissions("user")
    @RequestMapping("modifypwd")
    public String modifypwd(@RequestParam(value = "oldPassword",required = false) String oldPassword,
    @RequestParam(value = "newPassword",required = false) String newPassword,Model model){
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
            if (userService.validatePassword(oldPassword,user)){
                userService.updatePassword(user,newPassword);
                model.addAttribute("message", "修改密码成功");
            }else{
                model.addAttribute("message", "修改密码失败，旧密码错误");
            }
        }
        //model.addAttribute("user", user);
        return "sys/modifypwd";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping("list")
    public String list(User user,@RequestParam(value = "pageNo",defaultValue = "1",required = false) int pageNo,
                       @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
                       Model model){
        System.out.println(pageNo+"-"+pageSize);
        Page<User> page=new Page<>(pageNo,pageSize);
        EntityWrapper<User> ew=new EntityWrapper<>();
        ew.and("delete_flag={0}",0);
        if (StringUtils.isNoneBlank(user.getLoginName())&&StringUtils.isNoneBlank(user.getName()))
            ew.where("login_name={0}",user.getLoginName()).and("name={0}",user.getName());
        else if (StringUtils.isNoneBlank(user.getLoginName()))
            ew.where("login_name={0}",user.getLoginName());
        else if (StringUtils.isNoneBlank(user.getName()))
            ew.where("name={0}",user.getName());
        page=userService.selectPage(page,ew);
        model.addAttribute("page",page);
        return "sys/userList";
    }

    @RequiresPermissions("sys:user:edit")
    @RequestMapping("delete")
    public String delete(@RequestParam(value = "id")String id, RedirectAttributes redirectAttributes){
        if (id.equals("1")){
            redirectAttributes.addFlashAttribute("message","删除失败，不能删除超级系统管理员！");
        }else{
            User user=userService.selectById(id);
            user.setDeleteFlag(1);
            userService.updateById(user);
            redirectAttributes.addFlashAttribute("message","删除成功");
        }
        return "redirect:/sys/user/list";
    }
}
