package com.kevin.lee.rolecontrol.mapper;

import com.kevin.lee.rolecontrol.repository.po.UserResourcePO;
import com.kevin.lee.rolecontrol.util.FileUtil;
import com.kevin.lee.rolecontrol.util.GsonUtil;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 4/19/24 12:41 PM
 */
@Repository
public class UserResourceMapper {

    public boolean batchAdd(List<UserResourcePO> userResourcePOs) {
        return addUserResources(userResourcePOs);
    }

    public List<Long> queryUserResourceIds(long userId) {
        List<UserResourcePO> allRecords = getAllUserResource();
        List<UserResourcePO> userResourcePOs = allRecords.stream().filter(new Predicate<UserResourcePO>() {
            @Override
            public boolean test(UserResourcePO userResourcePO) {
                return userResourcePO.getUserId() == userId;
            }
        }).collect(Collectors.toList());
        return userResourcePOs.stream().map(ur -> ur.getResourceId()).collect(Collectors.toList());
    }

    private List<UserResourcePO> getAllUserResource() {
        //检查文件是否存在
        Path sourcePath = FileUtil.newFile(FileUtil.USER_RESOURCE_FILE_PATH);

        //读取文件并转换为对象列表
        List<UserResourcePO> objects = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(sourcePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                objects.add(GsonUtil.fromJson(line, UserResourcePO.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objects;
    }


    private boolean addUserResources(List<UserResourcePO> list) {
        //检查文件是否存在
        Path sourcePath = FileUtil.newFile(FileUtil.USER_RESOURCE_FILE_PATH);

        //将对象列表写入到目标文件
        try (
                BufferedWriter writer = Files.newBufferedWriter(sourcePath)) {
            for (UserResourcePO obj : list) {
                writer.write(GsonUtil.toJson(obj));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
