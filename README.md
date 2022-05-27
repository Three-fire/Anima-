#  Anima外卖项目  --含开发经历及详细流程

成果图大致如下：
后台管理界面：![截屏2022-05-27 23 05 31](https://user-images.githubusercontent.com/56632599/170726765-ce76f776-0c22-43e9-86d0-7f646da31ac0.png)

![截屏2022-05-27 23 06 19](https://user-images.githubusercontent.com/56632599/170726917-39a02833-3a37-4de6-9900-57890a31ed8b.png)

前台界面：![截屏2022-05-27 23 07 20](https://user-images.githubusercontent.com/56632599/170727098-46428f8e-f904-4a00-9f14-9fff32d82033.png)




## day01：

​		day01日记 ---搭了个框架，火速下线睡觉（玩手机）88



​	**1、设计搭建数据库表**

​				（1）Navicat pre+

​				（2）MySql

​				（3）建表
![截屏2022-05-27 23 02 22](https://user-images.githubusercontent.com/56632599/170726133-52b63439-290b-4a5a-bea4-8e8e7eacfd21.png)




​	**2、初始化SpringBoot项目**

​				（1）Spring initilizer ==> 选择Spring Web和 MySql Driver的依赖

​				（2）pom.xml中配置相关依赖， 如==mybatis-plus==、==lombok==、==fastjson==、==druid==



​	**3、加载静态页面**

​				将前端网页放置在SpringBoot管理的`../resources/statics`文件下





## day02:

​		day02日记 ---万事开头难，先搞个登陆界面吧，发现了新发展 ==> IOS开发，了解其路线并收集了学习资料



​	**1、后端页面的登陆功能的需求分析**

​				（1）登陆页面`login.html`

​				（2）登陆账号 ==> 对应数据库里`employee表`， 发送`json`数据至后台

​				（3）查看登陆请求信息



​	**2、登陆功能开发**

​				（1）创建`实体类Employee` 与数据库里的`employee表`进行 **映射**

​				（2）相应的`Controller`、`Service`、`Mapper`

​				（3）统一返回结果规范 ==>新建返回`结果类R`

​				（4）在`Controller`里写登陆方法（*不合理，优化后写在业务层*) ==> 处理逻辑：  

​																		1⃣️将页面提交的password进行md5加密

​																		2⃣️根据提交的username查询数据库

​																		3⃣️密码比对

​																		4⃣️查看员工状态status

​																		5⃣️登陆成功 ==> 将id存入Session



​	**3、Debug**

​				（1）在处理方法上打*断点*

​				（2）在`../resource/js/request.js` 配置了 *超时时间*， 不然debug时间一长，页面就抛 **timeout**错误了

​				（3）注意js相关配置修改了，但浏览器会**自动缓存**（还是之前的配置）

​				（4）**浏览器调试：** `Application` ->` Storage` -> `Local Storage`





## day03:

​		day03日记 ---Anima外卖项目暂时宕机一天，学习Swift基本语言（nt语言，真的花里胡哨），我的评价是不如java大法





## day04:

​		day04日记 ---笔记本屏幕下方docker处出现了故障，很影响使用，屏幕本就小！很糟糕，不得不买个显示器了！



​	**1、退出功能的需求分析**

​				（1）登陆成功后，跳转至后台系统首页面(`backend/index.html`)，右上角并显示当前 **登陆用户的姓名`name`**

​				（2）点击右上角退出按钮（ `logout()`方法 ）即退出后台系统，退出后跳转至登陆页面`login.html`



​	**2、退出功能开发**

​				（1）点击退出按钮，发送 **url请求 ： `/employee/logout`， method： `post` **

​				（2）写相应的 `Controller`  ==> 处理逻辑：

​												1⃣️清除`Session`中的用户id

​												2⃣️返回结果 `R`



​	**3、分析后台系统页面构成和效果**

​				（1）左边导航栏菜单的切换，切换后 右半边区域（`iframe`）的转变



## day05:

​		day05日记 ---周末连着下雨，吃个饭裤子湿一半，垃圾天气写出了垃圾代码



​	**1、完善登陆功能**

​				（1）问题分析： 用户不登陆直接输入 `backend/index.html`也可以访问后台首页 ==>  不合理，必须先登陆，跳转至登陆页面！



​				（2）完善步骤： 

​							1⃣️创建自定义`过滤器LoginCheckFilter`

​							2⃣️在启动类上加入注解`@SerletComponentScan`使过滤器生效

​							3⃣️过滤器的**处理逻辑**



​				（3）过滤器的处理逻辑：

​							1⃣️获取本次请求的`URL`

​							2⃣️判断本次请求是否需要处理， 不需要则直接放行

​							3⃣️判断登陆状态，若**已登陆**，则直接放行

​							4⃣️若未登陆则返回未登陆结果，通过输出流方式向客户端页面响应数据 ==> 前端代码都引入了`request.js`文件，里面有前端的

​																					   `响应拦截器`

- [x] TODO：有个坑，浏览器`自动存储了Session`,导致不能直接拦截错误的requestUrl，已经修补	

  

​	**2、员工管理模块的需求分析**

​				（1）添加员工：   点击 `添加员工按钮`跳转到新增页面`add.html`， 录入的员工数据信息插入到 `employee表`， 最后点保存

​				（2）员工查询分页查询： 员工多在一个页面来显示较乱，以`分页`的方式来展示列表数据

​				（3）启用/禁用`status` 员工账号：  员工管理列表页面可以对某个员工账号进行启用/禁用操作，只有**admin**用户拥有此权限

​				（4）编辑员工信息：  点击 `编辑按钮`跳转到编辑页面， 编辑页面回显员工信息并进行修改，最后点保存



- [x] `employee表`中的 `username`做了`Unique唯一索引`，一旦账号重复（出现*Exception*），则提示 添加员工失败

  

​	**3、新增员工代码开发**

​				（1）页面发送`ajax请求`，将新增员工页面中输入的数据信息以`json`形式提交到服务端

​				（2）服务端`Controller`接收页面提交的数据并调用`Service`将数据进行保存

​				（3）`Service`调用`Mapper`操作数据库，**保存数据**



​	**4、员工信息分页查询代码开发**

​				（1）页面发送`ajax请求`，将分页查询参数（*page、pageSize、name*）提交到服务端

​				（2）服务端`Controller`接收页面提交的数据并调用`Service`查询数据

​				（3）`Service`调用`Mapper`操作数据库，**查询分页数据**

​				（4）`Controller`将查询到的分页数据 响应给页面

​				（5）页面接收到分页数据 并通过`ElementUI的Table`组件展示到页面上

​	

​	**5、启用/禁用员工账号代码开发**

​				（1）页面发送`ajax请求`，将参数（*id、status*）提交到服务端

​				（2）服务端`Controller`接收页面提交的数据并调用`Service`查询数据

​				（3）`Service`调用`Mapper`操作数据库



- [x] Bug：		分页查询时 **服务端**响应给页面的数据中`id`的值类型为`long`， 为19位数字，然而页面`js`处理`long`型数字 只能精确到前16位  
- [x] 解决方案：    1⃣️**对象转换器**`jacksonObjectMapper`， 基于*jackson*进行 *java*对象 到*json*数据 的转换

​				 2⃣️在`WebMvcConfig`配置类中**扩展**SpringMVC的 **消息转换器**，在此**消息转换器**中使用提供的对象转换器进行 *java对象*到*json数据*的转换



​	**6、编辑员工信息代码开发**

​				（1）点击 `编辑按钮`时，跳转到`add.html`, 并在url中携带 参数（员工*id*）

​				（2）在`add.html`页面获取url中的参数（员工*id*）

​				（3）发送`ajax请求`，请求服务端，同时提交员工*id*参数

​				（4）服务端接收请求，根据员工*id*查询员工信息，将 员工信息以`json`形式响应给页面

​				（5）页面接收服务端响应的`json数据`，通过`VUE`的**数据绑定** 进行员工信息**回显**

​				（6）点击 `保存按钮`， 发送`ajax请求`，将页面的员工信息以`json`方式提交给服务端

​				（7）服务端接收员工信息， 并进行**处理**，完成后给 *页面响应*

​				（8）页面接收到服务端**响应信息**后进行 相应**处理**



- [x] 编辑页面的`保存按钮` 与启用/禁用的`保存按钮`功能相同





## Day06-7：

​		day06日记 ---显示器到了，人却落枕了，头要掉了一样，动一下疼一下，就写一点点吧（写完并测通了公共字段自动填充代码）

​		day07日记 ---落枕大大缓解，右后背却疼了起来，估摸着是因为落枕坐姿不当吧 = =

​	**1、分类管理模块需求分析**

​				（1）公共字段（创建时间/人、修改时间/人之类）自动填充： **重复性的工作**，统一处理简化开发 ==> `MybatisPlus`提供的自动填充功能

​				（2）新增分类：分类包括**菜品分类和套餐分类**，后台系统添加菜品时需要选择一个菜品分类... 在 **移动端**也按菜品分类和套餐分类来展示对应产品

​				（3）分类信息分页查询：以分页形式展现 菜品分类

​				（4）删除分类： 对某个分类进行删除操作，但需要注意当分类 关联了菜品或者套餐时，此分类不允许删除

​				（5）修改分类：分类管理页面点击 `修改按钮`，弹出修改窗口， 修改窗口回显信息，最后点击确认 完成修改操作



​	**2、公共字段自动填充代码实现**

​				（1）在实体类的属性 上加入`@TableField`，指定自动填充的**策略(INSERT、UPDATE)**

​				（2）按框架要求编写 `元数据对象处理器`，在此类中*统一*为 **公共字段赋值**， 此类需实现`MetaObjectHandler接口`



- [x] 有个难点在`MyMetaObjectHandler类`中无法动态获得登陆中用户id ==> 此类不能获得`HttpSession对象` ==> 解决方法：使用**JDK**中提供的`ThreadLocal类`
- [x] NOTES： 客户端发送的每一次`http请求`，对应在服务端都会分配一个新的`线程`来处理（**LoginCheckFilter的doFilter方法、EmployeeController的update方法、MyMetaObjectHandler的update方法都属于同一个线程**）   ==>  获取线程号*Thread.currentThread().getId()*

==解决方案细说==

>`ThreadLocal`并不是*Thread*，而是*Thread*的 **局部变量**。 使用`ThreadLocal`维护变量时，`ThreadLocal`为每个使用该变量的线程提供**独立的变量副本**，所以每一个线程都可以独立地改变自己的副本。`ThreadLocal`为每个线程提供**单独的存储空间**，具有 **线程隔离 **的效果，线程内才能获其值，线程外不能访问。
>
>`ThreadLocal`常用方法：
>
>- public void set(T value)           // 设置当前线程的线程局部变量的值
>- public T get()                    // 返回当前线程所对应的线程局部变量的值

​				（3）功能完善：	

​							1⃣️编写`BaseContext工具类`，基于`ThreadLocal`封装的工具类

​							2⃣️在`LoginCheckFilter`的`doFilter`方法中调用`BaseContext`来设置当前登陆用户的*id*

​							3⃣️在`MyMetaObjectHandler`的方法中调用`BaseContext`获取登陆用户的*id*



​	**3、新增分类代码开发**

​				（1）页面(`backend/page/category/list.html`)发送ajax请求，将 新增分类窗口输入的数据以`json`形式提交到服务端

​				（2）服务端`Controller`接收页面提交的数据并调用`Service`将数据进行保存

​				（3）`Service`调用`Mapper`操作数据库，保存数据



​	**4、分类信息分页查询代码开发**

​				（1）页面发送`ajax`请求，将分页查询参数（`page、pageSize`）提交到服务端

​				（2）服务端`Controller`接收页面提交的数据并调用`Service`查询数据

​				（3）`Service`调用`Mapper`操作数据库，查询分页数据

​				（4）`Controller`将查询到的分页数据 响应给页面

​				（5）页面接收到分页数据并通过`ElementUI`的Table组件展示到页面上



​	**5、删除分类代码开发**

​				（1）页面发送`ajax`请求，将参数（*id*）提交到服务端

​				（2）服务端`Controller`接收页面提交的数据并调用`Service`删除数据

​				（3）`Service`调用`Mapper`操作数据库

​				 

- [x] 有个坑：直接删id的话，没有判断此分类是否关联了菜品，属于无脑删除，火速完善！

- [x] 完善功能：

  	- 关联实体类`Dish`和`Setmeal`
  	
  	- 在`CategoryServiceImpl`中重写`remove()`方法, 加以查询判断
  	
  	- 配置`GlobalExceptionHandler`, 若有关联，则抛出异常      



​	**6、修改分类代码开发**

​				（1）修改窗口 回显分类信息  ==> 因为没和后台交互，故直接 **前端页面效果**

​				（2）重复跟 `新增分类功能` 雷同的代码



## Day08-10:

​		day08日记 ---最近忙于考试，还热的要命，今天抽一个钟把 菜品管理模块的 文件（图片之类）上传下载功能写上

​		day09日记 ---复习离散+Swift，请假一天

​		day10日记 ---花费了3个钟终于搞定，主要是两张表同时的增删改查，新学到了 对象拷贝方法



​	**1、文件上传下载功能代码开发**

​				（1）upload: 

​							1⃣️`../resources/backend/page/demo/upload.html`

​							2⃣️`file.transferTo(new File(*****))`方法将临时文件转存到指定（本地）位置

​							3⃣️`upload()` return 回 图片的fileName, 为下一个新增菜品功能做准备

​				（2）download:

​							1⃣️暂时此功能只是用作 上传完菜品图片后 回显在页面上

​							2⃣️`FileInputStream`读取文件内容，`response.getOutputStream()`写回浏览器并展示出来

​							3⃣️记得关闭IO资源



- [x] `Spring框架`在**spring-web包**对文件上传进行了封装，只需要在`Controller`的方法中声明一个`MultipartFile类型的参数`即可接收上传的文件



​	**2、新增菜品**

​				（1）需求分析：

​							后台系统管理菜品信息，通过新增功能来添加一个新的菜品(`dish`)，需选择所属菜品分类且要上传菜品照片，还要添加口味(`dish_flavor`)

​							在移动端按菜品分类来展示菜品

​				（2）代码实现：

						- 页面(`backend/page/food/add.html`)发送`ajax`请求，请求服务端获取 `菜品分类数据` 并展示到下拉框中
						- 页面发送请求 `进行图片上传`，请求服务端 将图片保存到服务端
						- 页面发送请求 `进行图片下载`，将上传的图片进行`回显`
						- 点击保存按钮，发送`ajax`请求，将菜品相关数据以`json`形式提交到服务端			

​			

- [x] 保存的`json`数据中既有`Dish`又有`DishFlavor`，现有的类无法接收，则创建新类`DishDto`(Data Transfer Object)用于封装页面提交的数据

- [x] 提一嘴： 要在DishDto参数前写`@RequestBody` （前端回传的`json`数据需要`@RequestBody` **反序列化**到我们定义的实体对象中）



​	**3、菜品信息分页查询**

​				（1）需求分析：

​							菜品数据多的时候，一个页面展示显得太乱，不便于查看，所以以分页的方式展示列表数据

​				（2）代码实现：

- 页面（`backend/page/food/list.html`）发送`ajax`请求，将分页查询参数（`page、pageSize、name`）提交到服务端，获取分页数据
- 页面发送请求，请求服务端进行图片下载，用于页面图片展示



- [x] 提交到页面的`categoryId`为数字串，菜品分类（需要`categoryName`）无法展示出来
- [x] 先创个`DishDto构造器对象`，对象拷贝。。。。写得很麻烦，之后改进



​	**4、修改菜品**

​				（1）需求分析:  菜品管理列表页面点击修改，跳转至修改页面，在修改页面回显菜品相关信息并进行修改，最后点击确认完成操作

​				（2）代码实现:  `为了省事，将修改菜品页面也放进了add.html`



- 发送`ajax`请求，请求服务端获取`分类数据`，用于 菜品分类下拉框中数据展示
- 发送`ajax`请求，请求服务端，根据*id*查询当前菜品信息，用于菜品信息回显
- 发送`Get`请求，请求服务端进行 图片下载，用于 页图片回显
- 点击保存按钮，发送`ajax`请求，将修改后的菜品相关数据以json形式提交到服务端



​	**5、更新菜品售卖状态**

​				（1）需求分析：  菜品管理列表点击停售/启售，更新*ids*相应菜品的`status`（含批量停售/启售功能）

![截屏2022-05-21 下午11.24.20](/Users/s/Library/Application%20Support/typora-user-images/%E6%88%AA%E5%B1%8F2022-05-21%20%E4%B8%8B%E5%8D%8811.24.20.png)

​				（2）代码实现：  

- 发送`ajax`请求，根据 *ids*停售/启售对应菜品



​	**6、删除菜品**

​				（1）需求分析：  在菜品管理列表页面点击删除按钮，即可删除对应的套餐信息，也可以通过复选框删除多个套餐，注意状态为售卖中的菜品暂时不能删除

![截屏2022-05-21 下午11.28.36](/Users/s/Library/Application%20Support/typora-user-images/%E6%88%AA%E5%B1%8F2022-05-21%20%E4%B8%8B%E5%8D%8811.28.36.png)

​				

​				（2）代码实现：

- 页面发送`ajax`请求，根据 *ids*删除对应套餐(批量删除就提交多个套餐*ids*)





## Day11:

​		day11日记 ---肠胃不舒服，整个脑袋都是嗡嗡的，将着写先，之后code review



​	**1、新增套餐**

​				（1）需求分析:  通过新增套餐功能来添加一个新的套餐，套餐中选择当前套餐所属的套餐分类和包含的菜品，需要上传套餐对应的图片，在移动端显示

​				（2）代码实现:

- 页面（`backend/page/combo/add.html`）发送`ajax`请求，请求服务端获取 **套餐分类数据**并展示到下拉框中
- 页面发送`ajax`请求， 请求服务端获取 **菜品分类数据**并展示到添加菜品窗口中
- 页面发送`ajax`请求，请求服务端根据 **菜品分类查询**对应的 **菜品数据**并展示添加菜品窗口中
- 页面发送请求进行 **图片上传**，请求服务端将图片保存到服务器
- 页面发送请求进行 **图片下载**，将上传的图片进行回显
- 点击保存按钮，发送ajax请求，将套餐相关数据以 **json**形式提交到服务端	



​	**2、套餐信息分页查询**

​				（1）需求分析：  套餐太多的时候，在一个页面全部展示出来显得较乱，不便于查看，需要以分页的方式来展示列表数据

​				（2）代码实现：

- 页面（`backend/page/combo/list.html`）发送`ajax`请求，将分页查询参数（`page、pageSize、name`）提交到服务端，获取分页数据
- 页面发送请求，请求服务端进行图片下载，用于页面图片展示



​	**3、删除套餐**

​				（1）需求分析：  在套餐管理列表页面点击删除按钮，即可删除对应的套餐信息，也可以通过复选框删除多个套餐，注意状态为售卖中的套餐暂时不能删除								==单个删除==:

![截屏2022-05-21 下午8.49.08](/Users/s/Library/Application%20Support/typora-user-images/%E6%88%AA%E5%B1%8F2022-05-21%20%E4%B8%8B%E5%8D%888.49.08.png)

​								==批量删除==:

​                                       ![截屏2022-05-21 下午8.50.48](/Users/s/Library/Application%20Support/typora-user-images/%E6%88%AA%E5%B1%8F2022-05-21%20%E4%B8%8B%E5%8D%888.50.48.png)	

​				（2）代码实现：

- 页面发送`ajax`请求，根据 *ids*删除对应套餐(批量删除就提交多个套餐*ids*)





## Day12:

​		day12日记 ---调试bug半天，很搞心态也很涨经验



​	**1、短信发送**

​				（1）阿里云短信发送服务

>登陆注册阿里云账号，选择相应的短信服务，内有详细教程，含相应的SDK，我将其封装至`../utils/SMSUtils`

​				

​	**2、手机验证码登陆**

​				（1）需求分析：  为方便用户登陆，移动端通常会提供通过手机验证码登陆的功能

​				（2）代码开发：

- 在登陆页面（`front/page/login.html`）输入 手机号, 点击「获取验证码」按钮，页面发送`ajax`亲故，在服务端调用短信服务API给指定手机号发送短信验证码
- 在登陆页面输入验证码， 点击【登陆】，发送`ajax`请求，在服务端处理登陆请求



- [x] 修改`LoginCheckFilter`，在进行手机 验证码登录时，发送的请求需要在此过滤器处理时直接放行

- [x] 血的教训，`Maven`中`clean`完后，一定要`Run Maven Build`，而不是直接run项目！！！！！！！！

​	

​	

## Day13:

​		day13日记 ---心平气和，平心静气，摒弃世俗摒弃世俗摒弃世俗！



​	**1、用户地址簿**

​				（1）需求分析： 移动端用户的地址信息，用户登陆成功后可以维护自己的地址信息（可多个地址信息，但只能有一个默认地址）

​				（2）代码开发： 

- 新增地址
- 设置默认地址
- 根据id查询地址
- 查询默认地址
- 查询指定用户的全部地址

​	

​	**2、菜品展示**

​				（1）需求分析： 用户登陆成功后跳转到系统首页，在首页需要根据分类来展示菜品和套餐。 菜品若设置了口味信息，需要展示==选择规格==按钮，否则显示==+==

​				（2）代码开发：

- 页面（`/front/index.html`）发送`ajax`请求，获取分类数据（ 菜品分类 和套餐分类）
- 页面发送`ajax`请求，获取第一个分类下的 *菜品或套餐*



- [x] 首页加载完后，还发送了一次`ajax`请求用于 加载购物车数据，此处将该请求的地址暂时修改，从静态`json`文件获取数据，后续开发购物车功能时再改回来



## Day14:

​	day14日记 ---Swift真有趣，java真生硬哈哈哈哈



​	**1、套餐展示**

​				（1）需求分析： 点击套餐分类，展示出套餐信息

​				（2）代码开发：

- 页面（`/setmeal/list?categoryId=?&status=?`），发送 **键值对**请求，获取套餐数据



​	**2、购物车**

​				（1）需求分析： 移动端用户可以将菜品或套餐添加到购物车，在购物车中可以修改菜品和套餐的数量，也可以清空购物车

​				（2）代码开发： 

- 点击==加入购物车==或者==+==按钮，页面发送`ajax`请求，请求服务端，将菜品或者套餐 添加到购物车
- 点击购物车图标，页面发送`ajax`请求，请求服务端 查询购物车中的菜品和套餐
- 点击清空购物车按钮，页面发送`ajax`请求，请求服务端执行 清空购物车操作





## Day15:

​	day15日记 ---看SwiftUI看得头晕，返回来写了下此项目，瞬间感觉头脑清醒



​	**1、用户下单**

​				（1）需求分析： 移动端用户将菜品或套餐加入购物车后，可点击购物车中的==去结算==按钮，页面跳转到订单确认页面，点击==去支付==按钮则完成下单操作

​				（2）代码开发：

- 在购物车中点击==去结算==按钮，页面跳转到订单确认页面
- 在订单确认页面，发送`ajax`请求，请求服务端获取当前登陆用户的默认地址
- 在订单确认页面，发送`ajax`请求，请求服务端获取当前登陆用户的购物车数据
- 在订单确认页面点击==去支付==按钮，发送`ajax`请求，请求服务端完成下单操作



- [x] 只需要搞定==去支付==功能就好了，前几个功能之前都写好了，直接能用



​	**2、查看订单**

​				（1）需求分析：	  

​				（2）代码开发：# Anima-[Anima外卖项目.md](https://github.com/Three-fire/Anima-/files/8787543/Anima.md)
