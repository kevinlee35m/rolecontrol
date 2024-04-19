package com.kevin.lee.rolecontrol.repository.po;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 4/19/24 12:38 PM
 */
@Getter
@Setter
@Builder
public class UserResourcePO {

    private long userId;

    private long resourceId;

}
