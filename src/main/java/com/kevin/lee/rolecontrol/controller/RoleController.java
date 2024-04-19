package com.kevin.lee.rolecontrol.controller;

import com.kevin.lee.rolecontrol.constants.Constant;
import com.kevin.lee.rolecontrol.repository.mapper.ResourceMapper;
import com.kevin.lee.rolecontrol.repository.mapper.UserResourceMapper;
import com.kevin.lee.rolecontrol.repository.po.UserResourcePO;
import com.kevin.lee.rolecontrol.util.GsonUtil;
import com.kevin.lee.rolecontrol.validator.RoleControlValidator;
import com.kevin.lee.rolecontrol.vo.UserAddVO;
import com.kevin.lee.rolecontrol.vo.UserVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addUser(@RequestBody UserAddVO userAddVO, @RequestHeader(Constant.HEADER_PARAM) String strLoginUser) {
        //请求解析
        UserVO loginUser = GsonUtil.fromJson(strLoginUser, UserVO.class);
        if (loginUser == null) {
            return new ResponseEntity<>("please login first", HttpStatus.BAD_REQUEST);
        }

        //参数校验
        String validateMsg = validator.validateAddUser(loginUser, userAddVO);
        if (!StringUtils.isEmpty(validateMsg)) {
            return new ResponseEntity<>(validateMsg, HttpStatus.BAD_REQUEST);
        }

        //数据插入
        //1.过滤用户已有资源
        List<Long> existUserResourceIds = userResourceMapper.queryUserResourceIds(userAddVO.getUserId());
        List<Long> fResourceIds = userAddVO.getResourceIds().stream().filter(new Predicate<Long>() {
            @Override
            public boolean test(Long aLong) {
                return !existUserResourceIds.contains(aLong);
            }
        }).collect(Collectors.toList());
        if (fResourceIds.size() == 0) {
            return new ResponseEntity<>("grant success", HttpStatus.OK);
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
            return new ResponseEntity<>("resource is invalid", HttpStatus.BAD_REQUEST);
        }

        List<UserResourcePO> userResourcePOs = filteredResourceIds.stream().map(id -> UserResourcePO.builder().userId(userAddVO.getUserId()).resourceId(id).build()).collect(Collectors.toList());
        boolean success = userResourceMapper.batchAdd(userResourcePOs);
        return success ? new ResponseEntity<>("grant success", HttpStatus.OK)
                : new ResponseEntity<>("occur error, please try again", HttpStatus.BAD_REQUEST);
    }

    /**
     * 查询资源
     *
     * @return
     */
    @GetMapping(value = "/user/resource/{id}")
    public ResponseEntity<String> getResource(@PathVariable("id") Long id, @RequestHeader(Constant.HEADER_PARAM) String strLoginUser) {
        //请求解析
        UserVO loginUser = GsonUtil.fromJson(strLoginUser, UserVO.class);
        if (loginUser == null) {
            return new ResponseEntity<>("please login first", HttpStatus.BAD_REQUEST);
        }

        //参数校验
        String validateMsg = validator.validateGetResource(loginUser, id);
        if (!StringUtils.isEmpty(validateMsg)) {
            return new ResponseEntity<>(validateMsg, HttpStatus.BAD_REQUEST);
        }

        //查询登录用户是否有访问权限
        List<Long> resourceIds = userResourceMapper.queryUserResourceIds(loginUser.getUserId());
        return resourceIds.contains(id) ? new ResponseEntity<>("login user has access", HttpStatus.OK)
                : new ResponseEntity<>("login user has no access", HttpStatus.BAD_REQUEST);
    }

}
