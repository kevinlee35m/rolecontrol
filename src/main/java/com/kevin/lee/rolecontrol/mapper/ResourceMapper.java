package com.kevin.lee.rolecontrol.mapper;

import com.kevin.lee.rolecontrol.repository.po.ResourcePO;
import com.kevin.lee.rolecontrol.util.FileUtil;
import com.kevin.lee.rolecontrol.util.GsonUtil;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    /**
     * 根据资源ID查询资源信息
     *
     * @param resourceId
     * @return
     */
    public ResourcePO getResource(long resourceId) {
        List<ResourcePO> resourcePOs = getAllResource();
        Map<Long, ResourcePO> resourcePOMap = resourcePOs.stream().collect(Collectors.toMap(ResourcePO::getResourceId, re -> re));
        return resourcePOMap.get(resourceId);
    }

    /**
     * 查询所有资源ID
     *
     * @return
     */
    public List<Long> queryAllResourceId() {
        List<ResourcePO> resourcePOs = getAllResource();
        return resourcePOs.stream().map(resourcePO -> resourcePO.getResourceId()).collect(Collectors.toList());
    }

    private List<ResourcePO> getAllResource() {
        //确保文件存在
        FileUtil.newFile(FileUtil.RESOURCE_FILE_PATH, FileUtil.DEFAULT_RESOURCES);

        //读取文件并转换为对象列表
        List<ResourcePO> objects = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FileUtil.RESOURCE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                objects.add(GsonUtil.fromJson(line, ResourcePO.class));
            }
        } catch (IOException e) {
            System.out.println("read file " + FileUtil.RESOURCE_FILE_PATH + " fail, cause: " + e);
        }

        return objects;
    }
}
