package com.kevin.lee.rolecontrol.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 4/19/24 12:24 PM
 */
@Data
public class UserVO implements Serializable {

    private Long userId;

    private String accountName;

    private String role;
}
