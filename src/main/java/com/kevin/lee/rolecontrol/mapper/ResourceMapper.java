package com.kevin.lee.rolecontrol.mapper;

import com.kevin.lee.rolecontrol.repository.po.ResourcePO;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 4/19/24 12:41 PM
 */
@Repository
public class ResourceMapper {

    public boolean addResource(ResourcePO resourcePO) {
        List<ResourcePO> list = new ArrayList<>();
        return addResources(list);
    }

    public ResourcePO getResource(long resourceId) {
        List<ResourcePO> resourcePOs = getAllResource();
        Map<Long, ResourcePO> resourcePOMap = resourcePOs.stream().collect(Collectors.toMap(ResourcePO::getResourceId, user -> user));
        return resourcePOMap.get(resourceId);
    }

    private List<ResourcePO> getAllResource() {
        //检查文件是否存在
        Path sourcePath = FileUtil.newFile(FileUtil.RESOURCE_FILE_PATH, FileUtil.DEFAULT_RESOURCES);

        //读取文件并转换为对象列表
        List<ResourcePO> objects = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(sourcePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                objects.add(GsonUtil.fromJson(line, ResourcePO.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objects;
    }


    private boolean addResources(List<ResourcePO> list) {
        //检查文件是否存在
        Path sourcePath = FileUtil.newFile(FileUtil.RESOURCE_FILE_PATH, FileUtil.DEFAULT_RESOURCES);

        //将对象列表写入到目标文件
        try {
            BufferedWriter writer = Files.newBufferedWriter(sourcePath);
            for (ResourcePO obj : list) {
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
