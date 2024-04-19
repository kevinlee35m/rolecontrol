package com.kevin.lee.rolecontrol.controller;

import com.kevin.lee.rolecontrol.mapper.UserMapper;
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


    public

    /**
     * 给用户做资源授权
     *
     * @return
     */
    @PostMapping(value = "/admin/addUser")
    public String addUser(@RequestBody UserAddVO userAddVO, @RequestHeader("Logged-in User") String strLoginUser) {
        //请求解析
        UserVO loginUser = GsonUtil.fromJson(strLoginUser, UserVO.class);
        if (loginUser == null) {
            return "请先登录";
        }

        //参数校验
        String validateMsg = validator.validateAddUser(loginUser, userAddVO);
        if (!StringUtils.isEmpty(validateMsg)) {
            return validateMsg;
        }

        //数据插入
        List<Long> existResourceIds = userResourceMapper.queryUserResourceIds(userAddVO.getUserId());
        List<Long> filteredResourceIds = userAddVO.getResourceIds().stream().filter(new Predicate<Long>() {
            @Override
            public boolean test(Long aLong) {
                return existResourceIds.contains(aLong);
            }
        }).collect(Collectors.toList());
        if (filteredResourceIds.size() == 0) {
            return "已添加";
        }
        List<UserResourcePO> userResourcePOs = filteredResourceIds.stream().map(id -> UserResourcePO.builder().userId(userAddVO.getUserId()).resourceId(id).build()).collect(Collectors.toList());
        boolean success = userResourceMapper.batchAdd(userResourcePOs);
        return success ? "授权成功" : "发生异常，请重試";
    }

    /**
     * 查询资源
     *
     * @return
     */
    @GetMapping(value = "/user/resource/{id}")
    public String getResource(@PathVariable("id") Long id, @RequestHeader("Logged-in User") String strLoginUser) {
        //请求解析
        UserVO loginUser = GsonUtil.fromJson(strLoginUser, UserVO.class);
        if (loginUser == null) {
            return "请先登录";
        }

        //参数校验
        validator.validateGetResource(loginUser, id);


        return "hello springboot!";
    }

}
