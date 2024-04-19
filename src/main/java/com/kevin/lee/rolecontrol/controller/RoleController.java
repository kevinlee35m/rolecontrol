package com.kevin.lee.rolecontrol.controller;

import com.kevin.lee.rolecontrol.mapper.ResourceMapper;
import com.kevin.lee.rolecontrol.mapper.UserResourceMapper;
import com.kevin.lee.rolecontrol.repository.po.UserResourcePO;
import com.kevin.lee.rolecontrol.util.GsonUtil;
import com.kevin.lee.rolecontrol.validator.RoleControlValidator;
import com.kevin.lee.rolecontrol.vo.UserAddVO;
import com.kevin.lee.rolecontrol.vo.UserVO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 2/24/23 12:17 PM
 */
@RestController
public class RoleController {

    @Resource
    private RoleControlValidator validator;

    @Resource
    private UserResourceMapper userResourceMapper;

    @Resource
    private ResourceMapper resourceMapper;

    /**
     * 给用户做资源授权
     *
     * @return
     */
    @PostMapping(value = "/admin/addUser")
    public String addUser(@RequestBody UserAddVO userAddVO, @RequestHeader("Logged-inUser") String strLoginUser) {
        //请求解析
        UserVO loginUser = GsonUtil.fromJson(strLoginUser, UserVO.class);
        if (loginUser == null) {
            return "please login first";
        }

        //参数校验
        String validateMsg = validator.validateAddUser(loginUser, userAddVO);
        if (!StringUtils.isEmpty(validateMsg)) {
            return validateMsg;
        }

        //数据插入
        //1.过滤用户已有资源
        List<Long> existUserResourceIds = userResourceMapper.queryUserResourceIds(userAddVO.getUserId());
        List<Long> fResourceIds = userAddVO.getResourceIds().stream().filter(new Predicate<Long>() {
            @Override
            public boolean test(Long aLong) {
                return existUserResourceIds.contains(aLong);
            }
        }).collect(Collectors.toList());
        if (fResourceIds.size() == 0) {
            return "grant success";
        }

        //2.过滤无效资源
        List<Long> allResourceIds = resourceMapper.queryAllResourceId();
        List<Long> filteredResourceIds = fResourceIds.stream().filter(new Predicate<Long>() {
            @Override
            public boolean test(Long aLong) {
                return allResourceIds.contains(aLong);
            }
        }).collect(Collectors.toList());
        if (filteredResourceIds.size() == 0) {
            return "resource is invalid";
        }

        List<UserResourcePO> userResourcePOs = filteredResourceIds.stream().map(id -> UserResourcePO.builder().userId(userAddVO.getUserId()).resourceId(id).build()).collect(Collectors.toList());
        boolean success = userResourceMapper.batchAdd(userResourcePOs);
        return success ? "grant success" : "occur error, please try again";
    }

    /**
     * 查询资源
     *
     * @return
     */
    @GetMapping(value = "/user/resource/{id}")
    public String getResource(@PathVariable("id") Long id, @RequestHeader("Logged-inUser") String strLoginUser) {
        //请求解析
        UserVO loginUser = GsonUtil.fromJson(strLoginUser, UserVO.class);
        if (loginUser == null) {
            return "please login first";
        }

        //参数校验
        String validateMsg = validator.validateGetResource(loginUser, id);
        if (!StringUtils.isEmpty(validateMsg)) {
            return validateMsg;
        }

        //查询登录用户是否有访问权限
        List<Long> resourceIds = userResourceMapper.queryUserResourceIds(loginUser.getUserId());
        return resourceIds.contains(id) ? "login user has access" : "login user has no access";
    }

}
