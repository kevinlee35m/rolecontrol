package com.kevin.lee.rolecontrol.validator;

import com.kevin.lee.rolecontrol.RoleEnum;
import com.kevin.lee.rolecontrol.mapper.UserMapper;
import com.kevin.lee.rolecontrol.mapper.UserResourceMapper;
import com.kevin.lee.rolecontrol.repository.po.UserPO;
import com.kevin.lee.rolecontrol.vo.UserAddVO;
import com.kevin.lee.rolecontrol.vo.UserVO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 4/19/24 1:43 PM
 */
@Component
public class RoleControlValidator {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserResourceMapper userResourceMapper;


    public String validateAddUser(UserVO loginUser, UserAddVO userAddVO) {
        if (loginUser.getUserId() == null || loginUser.getUserId() <= 0) {
            return "登录账户请求参数异常";
        }

        if (userAddVO == null || userAddVO.getUserId() == null || userAddVO.getUserId() <= 0 || CollectionUtils.isEmpty(userAddVO.getResourceIds())) {
            return "请求参数异常";
        }

        UserPO loginUserPo = userMapper.getUser(loginUser.getUserId());
        if (loginUserPo.getRole() != RoleEnum.ADMIN.role) {
            return "登录账户无添加用户权限";
        }

        UserPO addUserPo = userMapper.getUser(userAddVO.getUserId());
        if (addUserPo == null) {
            return "请先注册授权用户";
        }

        return "";
    }

    public String validateGetResource(UserVO loginUser, Long resourceId) {
        if (loginUser.getUserId() == null || loginUser.getUserId() <= 0) {
            return "登录账户请求参数异常";
        }

        //参数校验
        if (resourceId == null || resourceId <= 0) {
            return "请求参数异常";
        }



        return "";
    }

}
