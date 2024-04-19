package com.kevin.lee.rolecontrol.util;

import com.google.common.collect.Lists;
import com.kevin.lee.rolecontrol.RoleEnum;
import com.kevin.lee.rolecontrol.repository.po.ResourcePO;
import com.kevin.lee.rolecontrol.repository.po.UserPO;
import com.kevin.lee.rolecontrol.repository.po.UserResourcePO;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 4/19/24 11:52 AM
 */
public class FileUtil {

    public static final String USER_FILE_PATH = "file/user.txt";

    public static final String USER_RESOURCE_FILE_PATH = "file/userresource.txt";

    public static final String RESOURCE_FILE_PATH = "file/resource.txt";

    public static final List<String> DEFAULT_USERS = Lists.newArrayList(
            GsonUtil.toJson(UserPO.builder().userId(1).userName("lee").role(RoleEnum.ADMIN.role).build()),
            GsonUtil.toJson(UserPO.builder().userId(2).userName("lee2").role(RoleEnum.USER.role).build()));

    public static final List<String> DEFAULT_RESOURCES = Lists.newArrayList(
            GsonUtil.toJson(ResourcePO.builder().resourceId(1).resource("resource1").build()),
            GsonUtil.toJson(ResourcePO.builder().resourceId(2).resource("resource2").build()),
            GsonUtil.toJson(ResourcePO.builder().resourceId(3).resource("resource3").build()));

    public static final List<String> DEFAULT_USER_RESOURCES = Lists.newArrayList(
            GsonUtil.toJson(UserResourcePO.builder().userId(1).resourceId(1).build())
    );


    public static void initFile() {
        newFile(USER_FILE_PATH, DEFAULT_USERS);

        newFile(USER_RESOURCE_FILE_PATH, DEFAULT_RESOURCES);

        newFile(RESOURCE_FILE_PATH, DEFAULT_USER_RESOURCES);
    }

    public static Path newFile(String strFilePath, List<String> strs) {
        Path filePath = Paths.get(strFilePath);
        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
            } catch (IOException e) {
                System.out.println("create file " + strFilePath + " fail, cause: " + e);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                for (String str : strs) {
                    writer.write(str);
                    writer.newLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
                // 处理文件读写异常
            }
        }
        return filePath;
    }
}
