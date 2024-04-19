package com.kevin.lee.rolecontrol;

import com.kevin.lee.rolecontrol.util.FileUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoleControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoleControlApplication.class, args);

        FileUtil.initFile();
    }

}
