package com.gong.service.sys;


import com.gong.dao.sys.UserDao;
import com.gong.model.sys.Role;
import com.gong.model.sys.User;
import com.gong.service.BaseService;
import com.gong.utils.Md5util;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<UserDao,User> {
    public List<Role> getRoleListByUserId(String userId){
        return baseMapper.getRoleListByUserId(userId);
    }
    public boolean validatePassword(String plainPassword,User user) {
        String hashPassword = Md5util.md5(plainPassword,user.getLoginName(),2);
        return user.getPassword().equals(hashPassword);
    }
    public void updatePassword(User user,String newPassword){
        String hashPassword = Md5util.md5(newPassword,user.getLoginName(),2);
        user.setPassword(hashPassword);
        updateById(user);
    }
}
