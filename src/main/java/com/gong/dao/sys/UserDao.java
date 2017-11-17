package com.gong.dao.sys;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gong.model.sys.Role;
import com.gong.model.sys.User;

import java.util.List;

public interface UserDao extends BaseMapper<User>{
    List<Role> getRoleListByUserId(String userId);
}
