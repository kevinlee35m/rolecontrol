功能1：查询登录用户是否可以访问资源
接口：get方法，http://localhost:8080/user/resource/{id} 
参数：从head中解析参数Logged-inUser得到登录用户信息，如：Key为Logged-inUser， Value为{"userId":1,"accountName":"ha","role":"1"}
参数：{id}为资源id，取值范围为[1,2,3,4,5,6,7]，项目启动时会创建资源表(Resource.txt)，初始化资源数据

预期结果
1.当id超出资源范围时，返回失败，具体原因为"resource is not exist"
2.当



功能2：登录用户给指定用户做资源授权
接口：post方法，http://localhost:8080/admin/addUser
参数：从head中解析参数Logged-inUser得到登录用户信息，如：Key为Logged-inUser， Value为{"userId":1,"accountName":"ha","role":"1"}
参数：从body中解析得到被授权用户信息，为raw-json格式，如：{"userId":2,"resourceIds":[1,2,3,4]}
