package com.kevin.lee.rolecontrol.util;

import com.kevin.lee.rolecontrol.RoleEnum;
import com.kevin.lee.rolecontrol.repository.po.ResourcePO;
import com.kevin.lee.rolecontrol.repository.po.UserPO;
import com.kevin.lee.rolecontrol.repository.po.UserResourcePO;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.google.common.collect.Lists;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 4/19/24 11:52 AM
 */
public class FileUtil {

    public static final String USER_FILE_PATH = "/user.txt";

    public static final String USER_RESOURCE_FILE_PATH = "/userresource.txt";

    public static final String RESOURCE_FILE_PATH = "/resource.txt";

    public static final List<UserPO> DEFAULT_USERS =

    public static final UserPO USER_PO = UserPO.builder().userId(1).userName("lee").role(RoleEnum.ADMIN.role).build();
    public static final UserPO USER_PO2 = UserPO.builder().userId(2).userName("lee2").role(RoleEnum.USER.role).build();
    public static final ResourcePO RESOURCE_PO = ResourcePO.builder().resourceId(1).resource("resource1").build();
    public static final ResourcePO RESOURCE_PO2 = ResourcePO.builder().resourceId(2).resource("resource2").build();
    public static final ResourcePO RESOURCE_PO3 = ResourcePO.builder().resourceId(3).resource("resource3").build();
    public static final UserResourcePO USER_RESOURCE_PO = UserResourcePO.builder().userId(1).resourceId(1).build();


    public static void initFile() {
        newFile(USER_FILE_PATH, );

        newFile(USER_RESOURCE_FILE_PATH);

        newFile(RESOURCE_FILE_PATH);
    }

    public static Path newFile(String sourceFilePath, List<String> strs) {
        Path sourcePath = Paths.get(sourceFilePath);
        if (!Files.exists(sourcePath)) {
            try {
                sourcePath = Files.createFile(sourcePath);

                BufferedWriter writer = Files.newBufferedWriter(sourcePath);
                for (String str : strs) {
                    writer.write(str);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("create file " + sourceFilePath + " fail, cause: " + e);
            }
        }
        return sourcePath;
    }
}
