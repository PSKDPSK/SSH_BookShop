## 基于SSH的网上书店商城(Struts,Spring,Hibernate)
### 学校实训时做的项目
### 前台
- 用户注册、登录、注销
- 浏览图书。
    - 可以在首页浏览图书，也可以分类浏览
- 购物车
    - 在购物车中，可以删除购物车中的商品，添加商品至购物车
- 订单
    - 用户想要确认订单，就需要在购物车界面填写收货地址，对于已经填写的用户不必填写
    - 确认订单后，生成订单，可在我的订单中查看订单状态，当订单状态变为已发货时，用户可以删除该订单，否则不可删除
### 后台
- 登录
    - 在登录界面管理员进行登录即可进入后台
- 图书分类
    - 可对类型进行增删改查的操作
- 图书
    - 可对图书进行增删改查的操作，添加图书时需要上传图书图片
- 订单
    - 管理员在未发货订单中，点击查看，即可看到详细的订单信息，即可确认发货。发货后订单状态就会改变
    - 在已发货订单中，可查看状态为已发货的订单
### 使用mysql数据库
#### 一个bug:后台图书类型添加，最多添加十个，可以将图书类型表的主键设成int类型就可解决，我嫌太麻烦，不想改了。
#### 没有做分页查询
#### 功能还有不足，不能添加多个地址，只能添加一个，不能够在线支付。
#### 只是基本实现。
# 因为我菜，也只能糊弄个实训。
