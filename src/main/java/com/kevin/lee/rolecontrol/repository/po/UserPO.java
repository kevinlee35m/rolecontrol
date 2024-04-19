package com.kevin.lee.rolecontrol.repository.po;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 4/19/24 11:54 AM
 */
@Data
@Builder
@NoArgsConstructor
public class UserPO {

    private long userId;

    private String userName;

    private int role;
}
