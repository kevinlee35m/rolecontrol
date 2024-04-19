package com.kevin.lee.rolecontrol.vo;

import lombok.Data;

import java.util.List;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 4/19/24 12:03 PM
 */
@Data
public class UserAddVO {

    private Long userId;

    private List<Long> resourceIds;
}
