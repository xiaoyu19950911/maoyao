
<a name="paths"></a>
## Paths

<a name="getadmissioninfousingget"></a>
### 获取招生简章信息列表
```
GET /art/queryadmissioninfo
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«ArtExamResponse»](#6e19ae3778206bc43f425a197381d852)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* art


<a name="getartteacherinfousingget"></a>
### 获取艺考名师信息列表
```
GET /art/queryartteacherinfo
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«ArtExamResponse»](#6e19ae3778206bc43f425a197381d852)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* art


<a name="createauthenticationtokenusingpost"></a>
### 登陆并获取token
```
POST /auth/gettoken
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Body**|**authenticationRequest**  <br>*required*|authenticationRequest|[JwtAuthenticationRequest](#jwtauthenticationrequest)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|object|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* auth


<a name="deleteauthenticationtokenusingput"></a>
### 退出并更新token
```
PUT /auth/logout
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result](#result)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* auth


<a name="refreshandgetauthenticationtokenusingget"></a>
### 刷新token
```
GET /auth/refresh
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|object|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* auth


<a name="registerusingpost"></a>
### 注册账号
```
POST /auth/register
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Body**|**request**  <br>*required*|request|[用户注册请求参数](#ab08e325c45a0150a4ae9fca3a9d7e96)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result](#result)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* auth


<a name="addbannerlistusingpost"></a>
### 新增banner
```
POST /banner/createbanner
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Query**|**userId**  <br>*required*|userId|< integer (int32) > array(multi)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«TeacherInfo»](#bf0baaf1c0552164c457982fb05a6aea)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* banner


<a name="updatebannerlistusingput"></a>
### 取消banner
```
PUT /banner/modifybanner
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Query**|**userId**  <br>*required*|userId|< integer (int32) > array(multi)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«TeacherInfo»](#bf0baaf1c0552164c457982fb05a6aea)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* banner


<a name="getbannerlistusingget"></a>
### 获取banner列表
```
GET /banner/querybannerlist
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«TeacherInfo»](#bf0baaf1c0552164c457982fb05a6aea)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* banner


<a name="getcategoryinfousingget"></a>
### 获取推广列表
```
GET /category/querycharge
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«CategoryInfoResponse»»](#2f7288fea226c87f5422694ceb6afb39)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* category


<a name="getvipcategorylistusingget"></a>
### 获取可开通vip列表
```
GET /category/queryvipcategorylist
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«VipCategory»»](#c70e8b124faf546fcbc995753bdc1296)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* category


<a name="addcolumnusingpost"></a>
### 新建专栏
```
POST /column/createcolumn
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Body**|**request**  <br>*required*|request|[ColumnInfoRequest](#columninforequest)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result](#result)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* column


<a name="addcoursetocolumnusingput"></a>
### 添加课程至专栏
```
PUT /column/createcoursetocolumn/{columnId}
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string||
|**Path**|**columnId**  <br>*optional*|专栏id|integer (int32)|`1`|
|**Query**|**courseIdList**  <br>*required*|courseIdList|< integer (int32) > array(multi)||


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result](#result)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* column


<a name="removecolumnusingdelete"></a>
### 删除专栏
```
DELETE /column/deletecolumn
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Query**|**columnIdList**  <br>*required*|columnIdList|< integer (int32) > array(multi)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result](#result)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* column


<a name="updatecolumnusingput"></a>
### 编辑专栏
```
PUT /column/modifycolumn
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Body**|**request**  <br>*required*|request|[UpdateColumnInfoRequest](#updatecolumninforequest)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result](#result)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* column


<a name="getpurchasedcourseinfousingget"></a>
### 查询专栏详细信息
```
GET /column/querypurchasedcolumninfo/{columnId}
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string||
|**Path**|**columnId**  <br>*optional*|专栏id|integer (int32)|`1`|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«ColumnDetailedInfo»](#8a5e080566190d83027b07a771365b3a)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* column


<a name="getpurchasedcourseinfolistusingget"></a>
### 已购-查询已购专栏列表
```
GET /column/querypurchasedcolumninfolist
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Query**|**page**  <br>*required*|page|integer (int32)|
|**Query**|**rows**  <br>*required*|rows|integer (int32)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«ColumnInfo»»](#6a96bf7d7891cf88e83a890f9098fc65)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* column


<a name="getusercolumninfolistusingget"></a>
### 获取用户专栏列表
```
GET /column/queryusercolumninfolist/{userId}
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string||
|**Path**|**userId**  <br>*optional*|用户id|integer (int32)|`1`|
|**Query**|**page**  <br>*required*|page|integer (int32)||
|**Query**|**rows**  <br>*required*|rows|integer (int32)||


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«ColumnInfo»»](#6a96bf7d7891cf88e83a890f9098fc65)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* column


<a name="createcourseusingpost"></a>
### 创建课程
```
POST /course/addcourse
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Body**|**request**  <br>*required*|request|[CourseInfoRequest](#courseinforequest)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result](#result)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* course


<a name="updatecourseusingput"></a>
### 编辑课程
```
PUT /course/modifycourse
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Body**|**request**  <br>*required*|request|[UpdateCourseInfoRequest](#updatecourseinforequest)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result](#result)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* course


<a name="getcoursecategoryusingget"></a>
### 获取课程/首页底部模块分类
```
GET /course/querycoursecategory
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string||
|**Query**|**type**  <br>*optional*|1、课程分类；2、首页底部模块分类|ref|`"1"`|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«CourseCategoryResponse»](#1f4d2ea159babc9f8eb682e1044c7a13)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* course


<a name="getcourseinfolistbycolumnusingget"></a>
### 根据专栏id获取课程列表
```
GET /course/querycourseinfolistbycolumn
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Query**|**page**  <br>*optional*|Results page you want to retrieve (0..N)|integer (int32)|
|**Query**|**size**  <br>*optional*|Number of records per page.|integer (int32)|
|**Query**|**sort**  <br>*optional*|pageable|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«CourseInfo»»](#4193b6be26076451ddd1429bdefb30d1)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* course


<a name="getfreecourseinfolistusingget"></a>
### 免费专区-获取免费课程列表
```
GET /course/queryfreecourseinfolist
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Query**|**page**  <br>*required*|page|integer (int32)|
|**Query**|**rows**  <br>*required*|rows|integer (int32)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«CourseInfo»»](#4193b6be26076451ddd1429bdefb30d1)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* course


<a name="gethotcourseinfolistusingget"></a>
### 热门推荐-获取热门课程列表
```
GET /course/queryhotcourseinfolist
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Query**|**page**  <br>*required*|page|integer (int32)|
|**Query**|**rows**  <br>*required*|rows|integer (int32)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«CourseInfo»»](#4193b6be26076451ddd1429bdefb30d1)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* course


<a name="getlivecourseinfolistusingget"></a>
### 艺术直播-获取当前正在直播的课程列表
```
GET /course/querylivecourseinfolist
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Query**|**page**  <br>*optional*|Results page you want to retrieve (0..N)|integer (int32)|
|**Query**|**size**  <br>*optional*|Number of records per page.|integer (int32)|
|**Query**|**sort**  <br>*optional*|pageable|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«CourseInfo»»](#4193b6be26076451ddd1429bdefb30d1)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* course


<a name="getmycourseinfousingget"></a>
### 获取自身已创建课程详细信息
```
GET /course/querymycourseinfo/{courseId}
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string||
|**Path**|**courseId**  <br>*optional*|课程id|integer (int32)|`1`|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«MyCourseInfoResponse»](#ede257762e73e03b7c9a34bad45c08c8)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* course


<a name="getothercourseinfousingget"></a>
### 获取其他人已创建课程详细信息
```
GET /course/queryothercourseinfo/{courseId}
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string||
|**Path**|**courseId**  <br>*optional*|课程id|integer (int32)|`1`|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«OtherCourseResponse»](#1714742e56dbe13610aaf96f5f89c45d)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* course


<a name="getpurchasedcourseinfolistusingget_1"></a>
### 已购-查询已购课程列表
```
GET /course/querypurchasedcourseinfolist
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Query**|**page**  <br>*required*|page|integer (int32)|
|**Query**|**rows**  <br>*required*|rows|integer (int32)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«CourseInfo»»](#4193b6be26076451ddd1429bdefb30d1)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* course


<a name="getspeakcourseinfolistusingget"></a>
### 开讲-获取最近3条可直播的课程列表
```
GET /course/queryspeakcourseinfolist
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«CourseInfo»»](#4193b6be26076451ddd1429bdefb30d1)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* course


<a name="getusercourseinfolistusingget"></a>
### 获取用户创建的课程列表
```
GET /course/queryusercourseinfolist
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Query**|**page**  <br>*optional*|Results page you want to retrieve (0..N)|integer (int32)|
|**Query**|**size**  <br>*optional*|Number of records per page.|integer (int32)|
|**Query**|**sort**  <br>*optional*|pageable|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«CourseInfo»»](#4193b6be26076451ddd1429bdefb30d1)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* course


<a name="deletecourseusingdelete"></a>
### 删除课程
```
DELETE /course/removecourse/{courseId}
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string||
|**Path**|**courseId**  <br>*optional*|课程id|integer (int32)|`1`|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result](#result)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* course


<a name="addliveusingpost"></a>
### 创建直播回调接口
```
POST /live/createlive
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Query**|**courseId**  <br>*required*|课程id|ref|
|**Query**|**id**  <br>*required*|直播id|ref|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result](#result)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* live


<a name="getliveusingget"></a>
### 查询课程最近一条直播id
```
GET /live/querylive
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Query**|**courseId**  <br>*required*|courseId|integer (int32)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result](#result)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* live


<a name="getchargeusingpost"></a>
### 支付（获取支付凭证）
```
POST /pay/getcharge
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Body**|**request**  <br>*required*|request|[ChargeRequest](#chargerequest)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Charge](#charge)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* pay


<a name="findchargeusingget"></a>
### 查询支付凭证
```
GET /pay/querycharge/{chargeId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Path**|**chargeId**  <br>*optional*|支付凭证id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«Charge»](#49388d7900970b46060e9d527b638985)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* pay


<a name="findchargelistusingpost"></a>
### 查询支付凭证列表
```
POST /pay/querychargelist
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Body**|**request**  <br>*required*|request|[ChargeInfoRequest](#chargeinforequest)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«Charge»](#49388d7900970b46060e9d527b638985)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* pay


<a name="resultusingpost"></a>
### result
```
POST /pay/result
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Body**|**event**  <br>*required*|event|[Event](#event)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result](#result)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* pay


<a name="successusingpost"></a>
### success
```
POST /pay/success
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result](#result)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* pay


<a name="createtransferusingpost"></a>
### 提现
```
POST /pay/withdrawals 
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Body**|**request**  <br>*required*|request|[WithdrawalsRequest](#withdrawalsrequest)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«Charge»](#49388d7900970b46060e9d527b638985)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* pay


<a name="getsearchresultusingget"></a>
### 搜索相关信息
```
GET /search/querysearchresult/{searchInfo}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Path**|**searchInfo**  <br>*required*|searchInfo|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«SearchResultResponse»](#aeb5b6ea75443f89921595bb92990a88)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* search


<a name="updatearteinfousingput"></a>
### 设置认证艺术家
```
PUT /user/modifyarteinfo
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Body**|**request**  <br>*required*|request|< [ArteRequest](#arterequest) > array|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«UserInfoResponse»](#fb114b83be62b7f3b2f3c68bf9660027)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* user


<a name="updateuserinfousingput"></a>
### 修改个人信息
```
PUT /user/modifyuserinfo
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Body**|**request**  <br>*required*|request|[UserInfoRequest](#userinforequest)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«UserInfoResponse»](#fb114b83be62b7f3b2f3c68bf9660027)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* user


<a name="getartistlistusingget"></a>
### 艺术家专栏-获取已认证艺术家列表
```
GET /user/queryartistlist
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|
|**Query**|**page**  <br>*required*|page|integer (int32)|
|**Query**|**rows**  <br>*required*|rows|integer (int32)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«TeacherInfo»»](#d4f964280c424c80275b08cfcda42bb7)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* user


<a name="getattentionteacherlistusingget"></a>
### 获取关注讲师列表
```
GET /user/queryattentionteacherlist/{userId}
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string||
|**Path**|**userId**  <br>*optional*|用户id|integer (int32)|`1`|
|**Query**|**page**  <br>*required*|page|integer (int32)||
|**Query**|**rows**  <br>*required*|rows|integer (int32)||


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«TeacherInfo»»](#d4f964280c424c80275b08cfcda42bb7)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* user


<a name="getcategoryteacherlistusingget"></a>
### 根据分类获取讲师列表
```
GET /user/querycategoryteeacherlist/{categoryId}
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string||
|**Path**|**categoryId**  <br>*optional*|分类id|integer (int32)|`1`|
|**Query**|**page**  <br>*required*|page|integer (int32)||
|**Query**|**rows**  <br>*required*|rows|integer (int32)||


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«TeacherInfo»»](#d4f964280c424c80275b08cfcda42bb7)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* user


<a name="getinvitationcodeusingget"></a>
### 生成6位代理商邀请码
```
GET /user/queryinvitationcode
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«InvitationCodeResponse»](#167809a709332d77e037c7fc178f6654)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* user


<a name="getownaccountlistusingget"></a>
### 查询自有账号列表
```
GET /user/queryownaccountlist
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«OwnAccount»»](#4391b12250ec286853e8c79bd54b2d8f)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* user


<a name="getpartartistlistusingget"></a>
### 学艺-获取已认证艺术家列表
```
GET /user/querypartartistlist
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«TeacherInfo»»](#d4f964280c424c80275b08cfcda42bb7)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* user


<a name="gettransactioninfolistusingget"></a>
### 获取收入支出信息列表
```
GET /user/querytransactioninfolist
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string||
|**Query**|**page**  <br>*required*|page|integer (int32)||
|**Query**|**rows**  <br>*required*|rows|integer (int32)||
|**Query**|**type**  <br>*optional*|1、查询收入列表；2、查询支出列表|integer (int64)|`1`|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«List«TransactionInfoResponse»»](#f90a2e2125dfba4bfe229e303cd84e69)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* user


<a name="getuseraccountinfousingget"></a>
### 获取用户账户信息
```
GET /user/queryuseraccountinfo
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«UserAccountInfoResponse»](#7411f1799a717b29719335939221ca10)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* user


<a name="getuserinfousingget"></a>
### 获取当前用户信息
```
GET /user/queryuserinfo
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«UserInfoResponse»](#fb114b83be62b7f3b2f3c68bf9660027)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* user


<a name="getuserliveroomusingget"></a>
### 获取用户直播间信息
```
GET /user/queryuserliveroom/{userId}
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string||
|**Path**|**userId**  <br>*optional*|用户id|integer (int32)|`1`|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«UserLiveRoomResponse»](#74f23cc419163e960fe338ae58a92271)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* user


<a name="getvipcategoryusingget"></a>
### 获取可开通vip类型列表
```
GET /vip/queryvipcategory
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«VipCategoryResponse»](#94f392d840d913506bf03d5864049add)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* vip


<a name="getvipinfousingget"></a>
### 获取当前用户vip信息
```
GET /vip/queryvipinfo
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|令牌|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[Result«VipInfoResponse»](#fae570a3bcc924fe2df6590768714126)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* vip



