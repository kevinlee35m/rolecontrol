package com.kevin.lee.rolecontrol.enums;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 4/19/24 12:46 PM
 */
public enum RoleEnum {

    ADMIN(1, "管理者"),

    COMMON(2, "普通用户");


    RoleEnum(int role, String desc) {
        this.role = role;
        this.desc = desc;
    }

    public int role;

    public String desc;

    public static boolean isValid(int role){
        for (RoleEnum roleEnum : values()){
            if (roleEnum.role == role){
                return true;
            }
        }

        return false;
    }
}
