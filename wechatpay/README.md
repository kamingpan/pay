# 微信支付工具
该工具仅是完成微信支付的各类接口调用（基于java语言，仅限于后台），不涉及到任何业务，微信支付接口响应都转换为对象后返回，基于对象操作，满足java面向对象的基本思路。
使用该工具前需要了解微信支付的各类流程及接口响应结果，详情可参考[微信支付官方开发文档](https://pay.weixin.qq.com/wiki/doc/api/index.html "点击打开")。

## 目录
[Toc]
* 基础说明
    * jdk支持版本
    * 支持模式和类型
    * 微信支付基本流程
* 迭代版本
* 基本结构
* 使用方法
    * 工具引用
    * 参数配置
    * 方法调用
* 基本问题解决

---

### 基础说明

#### jdk支持版本：jdk8及以上
目前仅支持jdk8及以上的使用，因为当中的加密解密包使用到jdk8提供的官方库包，后续会将涉及到的算法使用单独工具类完成，使其支持jdk8以下的版本，详情请查看`迭代版本`。

#### 支持模式和类型
目前可配置使用`普通商户版`和`服务商户版`两种模式（`银行服务商户版`尚未接触，因此暂不提供），支持以下六种支付类型：
* 刷卡支付（[普通商户版](https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=5_1)/[服务商户版](https://pay.weixin.qq.com/wiki/doc/api/micropay_sl.php?chapter=5_1)/[银行服务商户版]()）
* 公众号支付（[普通商户版](https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_1)/[服务商户版](https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=7_1)/[银行服务商户版]()）
* 扫码支付（[普通商户版](https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_1)/[服务商户版](https://pay.weixin.qq.com/wiki/doc/api/app/app_sl.php?chapter=8_1)/[银行服务商户版](https://pay.weixin.qq.com/wiki/doc/api/native_sl.php?chapter=6_1)）
* APP支付（[普通商户版](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_1)/[服务商户版]()/[银行服务商户版]()）
* H5支付（[普通商户版](https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=15_1)/[服务商户版](https://pay.weixin.qq.com/wiki/doc/api/H5_sl.php?chapter=15_1)/[银行服务商户版]()）
* 小程序支付（[普通商户版](https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_3&index=1)/[服务商户版](https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_sl_api.php?chapter=7_3&index=1)/[银行服务商户版]()）

#### 微信支付基本流程
* 刷卡支付<br/>
  ![刷卡支付业务流程时序图(免密支付)](https://pay.weixin.qq.com/wiki/doc/api/img/chapter5_4_3.png)
  ![刷卡支付业务流程时序图(验密支付)](https://pay.weixin.qq.com/wiki/doc/api/img/chapter5_4_4.png)
* 公众号支付<br/>
  ![公众号支付业务流程时序图](https://pay.weixin.qq.com/wiki/doc/api/img/chapter7_4_1.png)
* 扫码支付<br/>
  ![公众号支付业务流程时序图(模式一)](https://pay.weixin.qq.com/wiki/doc/api/img/chapter6_4_1.png)
  ![公众号支付业务流程时序图(模式二)](https://pay.weixin.qq.com/wiki/doc/api/img/chapter6_5_1.png)
* APP支付<br/>
  ![APP支付业务流程时序图](https://pay.weixin.qq.com/wiki/doc/api/img/chapter8_3_1.png)
* H5支付<br/>
  ![H5支付业务流程时序图](https://pay.weixin.qq.com/wiki/doc/api/img/chapter15_1.png)
* 小程序支付<br/>
  ![小程序支付业务流程时序图](https://pay.weixin.qq.com/wiki/doc/api/img/wxa-7-2.jpg)

---

### 迭代版本
* 1.0.0
    * 2020-01-17
      ```diff
      + 初始版本
      ```

---

### 基本结构
* src - 资源目录
    * lib -  资源包
    * main - 主目录
        * java - java源码
            * constant - 常量包
            * entity - 实体包
            * enumeration - 枚举包
            * exception - 异常包
            * service - 业务包
            * util - 工具包
        * resources - 配置文件目录
            * wechatpay - 微信支付配置文件
    * test - 测试目录
        * java - java单元测试源码
            * service - 业务包单元测试

---

### 使用方法
#### 工具引用
* maven引用（maven官方库上面已上传了该工具的jar包，可以直接往pom.xml文件里面添加jar引用）
    
    ```Maven
      <!-- 引入微信支付工具，`dependencies`节点内添加 -->
      <dependency>
          <groupId>com.kamingpan.pay</groupId>
          <artifactId>wechatpay</artifactId>
          <version>1.0.0</version>
      </dependency>
    ```

* 源码引用
    ```
    直接将源码clone到本地之后，在项目新建一个模块，然后把源码拷贝到新增的模块里面，直接配置使用即可。 
    ```

* 普通不使用maven项目引用
    ```
    1、直接从maven官方库下载jar包，拷贝到项目lib目录下使用；
    2、直接将源码clone到本地之后，通过`mvn package`命令打包成jar包，然后拷贝到项目lib目录下使用。 
    ```

#### 参数配置
  
  如果是使用maven从私服引用的话，需要在`resources`文件夹目录下新建`wechatpay/wechatpay-config.properties`配置文件，然后配置内容如下：
  ```properties
  # 微信支付结果通知回调地址，必须80或者443端口且不能带参数（配置本系统回调地址）
  wechatpay_notify_url = https://www.kamingpan.com/(请修改为准确配置)
  # 微信支付退款结果通知地址，必须80或者443端口且不能带参数（配置本系统回调地址）
  wechatpay_refund_notify_url = https://www.kamingpan.com/(请修改为准确配置)
  # 公众账号ID || 应用ID
  app_id = app_id(请修改为准确配置)
  # 微信支付分配的商户号
  mch_id = mch_id(请修改为准确配置)
  # 微信支付加密密钥（key）
  key = key(请修改为准确配置)
  # 签名加密方式（目前支持HMAC-SHA256和MD5，默认为MD5）
  sign_type = MD5
  # 是否使用服务商和特约（子）商户号组合
  is_sub_merchant = false
  # 特约（子）商户应用ID（使用服务商和特约商户号组合时使用）
  sub_app_id = sub_app_id(请修改为准确配置)
  # 特约（子）商户号（使用服务商和特约商户号组合时使用）
  sub_mch_id = sub_mch_id(请修改为准确配置)
  # 是否使用沙箱（测试）环境
  # 沙箱版请参考 https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=23_1
  use_sandbox = false
  # 设备号
  device_info = WEB
  # 商品简要描述，该字段须严格按照规范传递
  # PC网站-扫码支付         浏览器打开的网站主页title名-商品概述
  # 微信浏览器-公众号支付   商家名称-销售商品类目
  # 门店扫码-公众号支付     店名-销售商品类目
  # 门店扫码-扫码支付       店名-销售商品类目
  # 门店刷卡-刷卡支付       店名-销售商品类目
  # 第三方手机浏览器-H5支付 浏览器打开的移动网页的主页title名-商品概述
  # 第三方APP-APP支付       应用市场上的APP名字-商品概述
  body = 微信支付收费(请修改为准确配置)
  # 系统服务器外网ip（只用于刷卡支付、原生扫码支付，APP、公众号、H5、小程序请使用用户端ip）
  server_ip = server_ip(请修改为准确配置)
  # 交易失效时长，单位：秒
  # 建议：最短失效时间间隔大于1分钟，即60；0表示不设置失效时长
  time_expire = 0
  # SSL通道证书路径（退款请求使用）
  wechatpay_certificate = /usr/local/cer/证书名称.p12(请修改为准确配置)

  # 微信支付接口请求地址
  # 是否使用以下配置地址（如果不使用，则默认使用配置好的正式环境和沙箱环境地址）
  use_config_url = false
  # 统一下单地址
  unified_order_url = https://api.mch.weixin.qq.com/pay/unifiedorder
  # 查询订单地址
  order_query_url = https://api.mch.weixin.qq.com/pay/orderquery
  # 关闭订单地址
  close_order_url = https://api.mch.weixin.qq.com/pay/closeorder
  # 申请退款地址
  refund_url = https://api.mch.weixin.qq.com/secapi/pay/refund
  # 查询退款地址
  refund_query_url = https://api.mch.weixin.qq.com/pay/refundquery
  # 刷卡支付提交地址
  micro_pay_url = https://api.mch.weixin.qq.com/pay/micropay
  # 撤销订单地址（刷卡支付专用）
  reverse_order_url = https://api.mch.weixin.qq.com/secapi/pay/reverse
  # 获取沙箱版密钥地址
  sandbox_sign_key_url = https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey
  # 下载对账单地址
  download_bill_url = https://api.mch.weixin.qq.com/pay/downloadbill
  # 测速上报地址
  report_url = https://api.mch.weixin.qq.com/payitil/report
  ```

#### 方法调用
  ```java
/*********** 关于实参传入这里不赘述，代码中每个方法头有详细的传入字段注释 ***************/
 
// 直接创建基础方法实例使用
WeChatPayBaseService weChatPayBaseService = new WeChatPayBaseService();
// 刷卡支付实例(刷卡支付的下单和撤销订单和别的类型不一样，因此单独在一个类中)
WeChatMicroPayService weChatMicroPayService = new WeChatMicroPayService();
// 沙箱版接口实例
WeChatPaySandboxService weChatPaySandboxService = new WeChatPaySandboxService();
   
// 使用字段(实际使用需用户自己定义)
// 金额（单位：分）
private int amount = 100;
// 商品描述
private String body = "body";
// openid
private String openid = "openid";
// 订单号
private String tradeNo = "tradeNo";
// 退款单号
private String refundNo = "refundNo";
// 授权码（用户展示二维码信息）
private String authCode = "authCode";
// 交易类型
private TradeTypeEnum tradeType = TradeTypeEnum.APP;
 
// 统一下单(除刷卡支付以外)
UnifiedOrderResponse unifiedOrderResponse = weChatPayBaseService.unifiedOrder(tradeNo, amount, tradeType, body, openid);
System.out.println(unifiedOrderResponse); // 统一下单响应结果
 
// 刷卡支付
MicroPayResponse microPayResponse = weChatMicroPayService.microPay(tradeNo, amount, body, authCode);
System.out.println(microPayResponse); // 刷卡支付响应结果
 
// 订单查询
OrderQueryResponse orderQueryResponse = weChatPayBaseService.orderQuery(tradeNo, null);
System.out.println(orderQueryResponse); // 订单查询响应结果
 
// 关闭订单
CloseOrderResponse closeOrderResponse = weChatPayBaseService.closeOrder(tradeNo);
System.out.println(closeOrderResponse); // 关闭订单响应结果
 
// 撤销订单(仅限刷卡支付)
ReverseOrderResponse reverseOrderResponse = weChatMicroPayService.reverse(tradeNo, null);
System.out.println(reverseOrderResponse); // 撤销订单响应结果
 
// 退款申请
RefundResponse refundResponse = weChatPayBaseService.refund(null, tradeNo, refundNo, amount);
System.out.println(refundResponse); // 退款申请响应结果
 
// 退款查询
RefundQueryResponse refundQueryResponse = weChatPayBaseService.refundQuery(null, tradeNo, refundNo, null);
System.out.println(refundQueryResponse); // 退款查询响应结果
 
// 获取沙箱版签名密钥
SignKeyResponse signKeyResponse = weChatPaySandboxService.getSignKey();
System.out.println(signKeyResponse); // 响应结果
   ```

上述的实例化都是通过new关键字直接新建，也可以选择使用spring注入来使用，配置和使用如下：
  ```xml java
<bean id="weChatPayBaseService" class="com.kamingpan.pay.wechatpay.service.base.WeChatPayBaseService"/>
<bean id="weChatMicroPayService" class="com.kamingpan.pay.wechatpay.service.micro.WeChatMicroPayService"/>
<bean id="weChatPaySandboxService" class="com.kamingpan.pay.wechatpay.service.sandbox.WeChatPaySandboxService"/>
   
@Autowired
private WeChatPayBaseService weChatPayBaseService;
@Autowired
private weChatMicroPayService WeChatMicroPayService;
@Autowired
private weChatPaySandboxService WeChatPaySandboxService;
    
/*********** 方法使用与上面一致，因此不赘述 ***************/
  ```

---

### 基本问题解决
* AES 256算法异常
    ```text
    具体异常
    Caused by: java.security.InvalidKeyException: Illegal key size or default parameters
    ```
    因为美国的出口限制，Sun通过权限文件（local_policy.jar、US_export_policy.jar）做了相应限制，因此存在一些算法异常问题。Oracle在其官方网站上提供了无政策限制权限文件（Unlimited Strength Jurisdiction Policy Files），我们只需要将其替换到jdk环境中，就可以解决限制问题。
    ```text
    jar包替换到目录`%JAVA_HOME%/jre/lib/security`下，可自行备份原有jar包。
    ```
    
    jdk各个版本下载目录(项目`根目录/lib`文件夹下也有相应的jar包，可直接使用)
    * [jdk8 - 加密扩展（JCE）无限强度管辖权策略文件](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)
    * [jdk7 - 加密扩展（JCE）无限强度管辖权策略文件](http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html)
    * [jdk6 - 加密扩展（JCE）无限强度管辖权策略文件](http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html)

