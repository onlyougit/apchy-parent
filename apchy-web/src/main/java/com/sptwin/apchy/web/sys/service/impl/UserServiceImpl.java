package com.sptwin.apchy.web.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.model.UserCustom;
import com.sptwin.apchy.web.sys.mapper.UserCustomMapper;
import com.sptwin.apchy.web.sys.mapper.UserMapper;
import com.sptwin.apchy.web.sys.mapper.UserRoleCustomMapper;
import com.sptwin.apchy.web.sys.service.UserService;
import com.sptwin.spchy.model.common.ApplicationError;
import com.sptwin.spchy.model.common.PageBean;
import com.sptwin.spchy.model.common.Pagination;
import com.sptwin.spchy.model.common.ResponseJson;
import com.sptwin.spchy.model.enums.UserLockStatus;
import com.sptwin.spchy.model.utils.EncryptUtil;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Value("${user.default-pw}")
    private String defaultPw;
    @Autowired
    private UserCustomMapper userCustomMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleCustomMapper userRoleCustomMapper;

    @Override
    public ResponseJson<Object> changePassword(UserCustom userCustom) {
        ResponseJson<Object> responseJson = new ResponseJson<>();
        User user = userMapper.selectByPrimaryKey(userCustom.getId());
        String oldPw = EncryptUtil.entryptPassword( userCustom.getPassword(),user.getSalt());
        String newPw = EncryptUtil.entryptPassword( userCustom.getUserNewPw(),user.getSalt());
        if (!user.getPassword().equalsIgnoreCase(oldPw)) {
            responseJson.setCode(ApplicationError.PASSWORD_ERROR.getCode());
            responseJson.setMsg(ApplicationError.PASSWORD_ERROR.getMessage());
            return responseJson;
        }
        userCustom.setPassword(newPw);
        userMapper.updateByPrimaryKeySelective(userCustom);
        return responseJson;
    }

    @Override
    public Map queryUser(UserCustom userCustom, Pagination grid) {
        Map map = new HashMap();
        PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
        List<UserCustom> userCustomList = userCustomMapper.queryUser(userCustom);
        userCustomList.forEach(w->{
            List<Role> roleList = w.getRoleList();
            if(null != roleList && roleList.size()>0){
                String roleName = roleList.stream().map(x->x.getRoleName()).collect(Collectors.joining(","));
                w.setRoleNames(roleName);
            }
        });
        PageBean<UserCustom> pb = new PageBean(userCustomList);
        map.put("data", pb.getList());
        map.put("total", pb.getTotal());
        return map;
    }
    @Transactional
    @Override
    public ResponseJson<Object> insertOrEditUser(UserCustom userCustom,ResponseJson<Object> responseJson) {
        if(null != userCustom){
            Date date = new Date();
            if(null == userCustom.getId()){//添加用户
                //判断用户名是否存在
                int count = userCustomMapper.isExistUserName(userCustom.getUserName());
                if(count > 0){
                    responseJson.setCode(ApplicationError.USERNAME_EXISTED.getCode());
                    responseJson.setMsg(ApplicationError.USERNAME_EXISTED.getMessage());
                    return responseJson;
                }
                String salt = userCustom.getUserName()+new SecureRandomNumberGenerator().nextBytes().toHex();
                userCustom.setPassword(EncryptUtil.entryptPassword( defaultPw,salt));
                userCustom.setSalt(salt);
                userCustom.setLocked(UserLockStatus.UNLOCKED.ordinal());
                userCustom.setGmtCreate(date);
                userCustomMapper.insertSelective(userCustom);
            }else{//更新用户
                //更新真实姓名
                User user = new User();
                user.setGmtModified(date);
                user.setId(userCustom.getId());
                user.setRealName(userCustom.getRealName());
                userMapper.updateByPrimaryKeySelective(user);
                //更新用户机器表
                userRoleCustomMapper.batchDeleteByUserId(userCustom.getId());
            }
            if(!StringUtils.isEmpty(userCustom.getRoleNames())){
                List idList = Arrays.asList(userCustom.getRoleNames().split(","));
                userRoleCustomMapper.batchInsert(userCustom.getId(),idList);
            }
        }
        return responseJson;

    }
    @Override
    public UserCustom editQuery(Long id) {
        UserCustom userCustom = userCustomMapper.queryUserById(id);
        if(null != userCustom){
            List<Role> roleList = userCustom.getRoleList();
            if(null != roleList && roleList.size() > 0){
                String roleNames = roleList.stream().map(w->w.getRoleName()).collect(Collectors.joining(","));
                String roleIds = roleList.stream().map(w->w.getId().toString()).collect(Collectors.joining(","));
                userCustom.setRoleNames(roleNames);
                userCustom.setRoleIds(roleIds);
            }

        }
        return userCustom;
    }

    @Override
    public void deleteUser(Long id) {
        User user = new User();
        user.setGmtModified(new Date());
        user.setId(id);
        user.setLocked(UserLockStatus.LOCKED.ordinal());
        userMapper.updateByPrimaryKeySelective(user);
    }

}
