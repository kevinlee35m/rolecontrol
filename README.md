功能1：查询登录用户是否可以访问资源
接口：get方法，http://localhost:8080/user/resource/{id} 
参数：从head中解析参数Logged-inUser得到登录用户信息，如：Key为Logged-inUser， Value为{"userId":1,"accountName":"ha","role":"1"}
参数：{id}为资源id，取值范围为[1,2,3,4,5,6,7]


功能2：登录用户给指定用户做资源授权
接口：post方法，http://localhost:8080/admin/addUser
参数：从head中解析参数Logged-inUser得到登录用户信息，如：Key为Logged-inUser， Value为{"userId":1,"accountName":"ha","role":"1"}
参数：从body中解析得到被授权用户信息，为raw-json格式，如：{"userId":2,"resourceIds":[1,2,3,4]}


注：项目启动时会创建资源表(Resource.txt)，用户表(User.txt)，用户资源关系映射表(UserResource.txt)，并初始化表数据。
其中资源表ID取值范围1~7；
用户表ID=1为管理员角色，用户表ID=2为普通用户角色；
用户资源关系映射表初始数据仅为用户ID=1关联资源ID=1



测试用例具体见RoleControlApplicationTests
包含测试case
1.请求无效资源
2.请求资源不属于自己
3.请求资源属于自己

4.登录用户为用户授权，但请求参数异常
5.登录用户无权限授权
6.资源授权成功
7.授权的资源为无效资源
