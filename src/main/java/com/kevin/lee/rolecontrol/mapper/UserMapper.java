package com.kevin.lee.rolecontrol.mapper;

import com.kevin.lee.rolecontrol.repository.po.UserPO;
import com.kevin.lee.rolecontrol.util.FileUtil;
import com.kevin.lee.rolecontrol.util.GsonUtil;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 4/19/24 12:41 PM
 */
@Repository
public class UserMapper {

    public UserPO getUser(long userId) {
        List<UserPO> userPOs = getAllUser();
        Map<Long, UserPO> userPOMap = userPOs.stream().collect(Collectors.toMap(UserPO::getUserId, user -> user));
        return userPOMap.get(userId);
    }


    private List<UserPO> getAllUser() {
        //确保文件存在
        Path sourcePath = FileUtil.newFile(FileUtil.USER_FILE_PATH, FileUtil.DEFAULT_USERS);

        //读取文件并转换为对象列表
        List<UserPO> objects = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(sourcePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                objects.add(GsonUtil.fromJson(line, UserPO.class));
            }
        } catch (IOException e) {
            System.out.println("read file " + FileUtil.DEFAULT_USERS + " fail, cause: " + e);
        }

        return objects;
    }
}
