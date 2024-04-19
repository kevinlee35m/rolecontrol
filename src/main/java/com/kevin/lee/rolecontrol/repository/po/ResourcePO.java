package com.kevin.lee.rolecontrol.repository.po;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 4/19/24 12:37 PM
 */
@Data
@Builder
@NoArgsConstructor
public class ResourcePO {

    private long resourceId;

    private String resource;

}
