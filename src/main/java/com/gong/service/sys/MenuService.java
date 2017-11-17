package com.gong.service.sys;

import com.gong.dao.sys.MenuDao;
import com.gong.model.sys.Menu;
import com.gong.model.sys.User;
import com.gong.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class MenuService extends BaseService<MenuDao,Menu> {
    @Autowired
    UserService userService;
    public List<Menu> getMenuListByUser(String userId){
        List<Menu> menuList;
        menuList=baseMapper.getMenuListByUser(userId);
        User user=userService.selectById(userId);
        String[] menuIds=user.getExtraMenuId().split(",");
        for (String menuId:menuIds){
            if (!menuList.contains(selectById(menuId)))
              menuList.add(selectById(menuId));
        }
        return menuList;
    }
    public List<Menu> getAllMenuList(){
        return baseMapper.getAllMenuList();
    }
}
