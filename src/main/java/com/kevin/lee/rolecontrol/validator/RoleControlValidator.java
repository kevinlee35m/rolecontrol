package com.kevin.lee.rolecontrol.validator;

import com.kevin.lee.rolecontrol.RoleEnum;
import com.kevin.lee.rolecontrol.mapper.ResourceMapper;
import com.kevin.lee.rolecontrol.mapper.UserMapper;
import com.kevin.lee.rolecontrol.repository.po.ResourcePO;
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
    private ResourceMapper resourceMapper;


    public String validateAddUser(UserVO loginUser, UserAddVO userAddVO) {
        if (loginUser.getUserId() == null || loginUser.getUserId() <= 0) {
            return "login user param is wrong";
        }

        if (userAddVO == null || userAddVO.getUserId() == null || userAddVO.getUserId() <= 0 || CollectionUtils.isEmpty(userAddVO.getResourceIds())) {
            return "param is wrong";
        }

        UserPO loginUserPo = userMapper.getUser(loginUser.getUserId());
        if (loginUserPo.getRole() != RoleEnum.ADMIN.role) {
            return "login user has no right to grant";
        }

        UserPO addUserPo = userMapper.getUser(userAddVO.getUserId());
        if (addUserPo == null) {
            return "unknown granting user";
        }

        return "";
    }

    public String validateGetResource(UserVO loginUser, Long resourceId) {
        if (loginUser.getUserId() == null || loginUser.getUserId() <= 0) {
            return "login user param is wrong";
        }

        //参数校验
        if (resourceId == null || resourceId <= 0) {
            return "param is wrong";
        }

        ResourcePO resourcePO = resourceMapper.getResource(resourceId);
        return resourcePO != null ? "" : "resourceDesc is not exist";
    }

}
