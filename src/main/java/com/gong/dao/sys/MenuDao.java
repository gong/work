package com.gong.dao.sys;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gong.model.sys.Menu;

import java.util.List;

public interface MenuDao extends BaseMapper<Menu>{
    List<Menu> getMenuListByUser(String userId);
    List<Menu> getAllMenuList();
}
