package com.kevin.lee.rolecontrol.repository.po;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 4/19/24 12:38 PM
 */
@Data
@Builder
@NoArgsConstructor
public class UserResourcePO {

    private long userId;

    private long resourceId;

}
