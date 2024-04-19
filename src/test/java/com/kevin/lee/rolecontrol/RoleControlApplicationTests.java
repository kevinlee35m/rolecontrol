package com.kevin.lee.rolecontrol;


import com.kevin.lee.rolecontrol.constants.Constant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RoleControlApplicationTests {

    @Autowired
    private MockMvc mockMvc;


    /*************************************    /user/resource/{id}    *************************************/

    /**
     * 测试方法：/user/resource/{id}
     * case1：请求资源无效(有效资源1~7)
     *
     * @throws Exception
     */
    @Test
    public void test_getResource_invalidResource() throws Exception {
        mockMvc.perform(get("/user/resource/8").header(Constant.HEADER_PARAM, "{\"userId\":1,\"accountName\":\"lee\",\"role\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("resource is not exist"));
    }

    /**
     * 测试方法：/user/resource/{id}
     * case2：资源不属于自己(userId=1只拥有资源1，不拥有资源2)
     *
     * @throws Exception
     */
    @Test
    public void test_getResource_notSelf() throws Exception {
        mockMvc.perform(get("/user/resource/2").header(Constant.HEADER_PARAM, "{\"userId\":1,\"accountName\":\"lee\",\"role\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("login user has no access"));
    }

    /**
     * 测试方法：/user/resource/{id}
     * case3：资源属于自己(userId=1只拥有资源1)
     *
     * @throws Exception
     */
    @Test
    public void test_getResource_self() throws Exception {
        mockMvc.perform(get("/user/resource/1").header(Constant.HEADER_PARAM, "{\"userId\":1,\"accountName\":\"lee\",\"role\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("login user has access"));
    }





    /*************************************    /admin/addUser    *************************************/

    /**
     * 测试方法：/admin/addUser
     * case1：body参数缺失，如userId缺失，或者资源id缺失
     *
     * @throws Exception
     */
    @Test
    public void test_addUser_paramWrong() throws Exception {
        mockMvc.perform(post("/admin/addUser").header(Constant.HEADER_PARAM, "{\"userId\":1,\"accountName\":\"lee\",\"role\":1}")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"resourceIds\":[1,2,3,4]}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("param is wrong"));
    }

    /**
     * 测试方法：/admin/addUser
     * case2：登录用户无权限授权，userId=2为普通用户，无权授权
     *
     * @throws Exception
     */
    @Test
    public void test_addUser_loginUserHasNoRight2Grant() throws Exception {
        mockMvc.perform(post("/admin/addUser").header(Constant.HEADER_PARAM, "{\"userId\":2,\"accountName\":\"lee\",\"role\":2}")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"resourceIds\":[1,2,3,4]}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("login user has no right to grant"));
    }

    /**
     * 测试方法：/admin/addUser
     * case3：资源授权成功
     *
     * @throws Exception
     */
    @Test
    public void test_addUser_grantSuccess() throws Exception {
        mockMvc.perform(post("/admin/addUser").header(Constant.HEADER_PARAM, "{\"userId\":1,\"accountName\":\"lee\",\"role\":1}")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":2,\"resourceIds\":[2,3,4]}"))
                .andExpect(status().isOk())
                .andExpect(content().string("grant success"));
    }

    /**
     * 测试方法：/admin/addUser
     * case4：资源无效(有效资源仅为1~7)
     *
     * @throws Exception
     */
    @Test
    public void test_addUser_resourceInvalid() throws Exception {
        mockMvc.perform(post("/admin/addUser").header(Constant.HEADER_PARAM, "{\"userId\":1,\"accountName\":\"lee\",\"role\":1}")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":2,\"resourceIds\":[8]}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("resource is invalid"));
    }
}
