
<a name="definitions"></a>
## Definitions

<a name="artexamresponse"></a>
### ArtExamResponse

|Name|Description|Schema|
|---|---|---|
|**admissionId**  <br>*optional*|简章或名师id|integer (int32)|
|**curl**  <br>*optional*|内容URL|string|
|**purl**  <br>*optional*|图片URL|string|
|**time**  <br>*optional*|创建时间|string (date-time)|
|**title**  <br>*optional*|标题/名师昵称|string|


<a name="arterequest"></a>
### ArteRequest

|Name|Description|Schema|
|---|---|---|
|**status**  <br>*optional*|用户状态(1、正在使用；0、已停用；2、已认证)|integer (int32)|
|**userId**  <br>*optional*|用户id|integer (int32)|


<a name="categoryinforesponse"></a>
### CategoryInfoResponse

|Name|Description|Schema|
|---|---|---|
|**amount**  <br>*optional*|刷新金额|number (double)|
|**count**  <br>*optional*|刷新次数|integer (int32)|


<a name="charge"></a>
### Charge

|Name|Schema|
|---|---|
|**amount**  <br>*optional*|integer (int32)|
|**amountRefunded**  <br>*optional*|integer (int32)|
|**amountSettle**  <br>*optional*|integer (int32)|
|**app**  <br>*optional*|object|
|**body**  <br>*optional*|string|
|**channel**  <br>*optional*|string|
|**clientIp**  <br>*optional*|string|
|**created**  <br>*optional*|integer (int64)|
|**credential**  <br>*optional*|object|
|**currency**  <br>*optional*|string|
|**description**  <br>*optional*|string|
|**extra**  <br>*optional*|object|
|**failureCode**  <br>*optional*|string|
|**failureMsg**  <br>*optional*|string|
|**id**  <br>*optional*|string|
|**livemode**  <br>*optional*|boolean|
|**metadata**  <br>*optional*|object|
|**object**  <br>*optional*|string|
|**orderNo**  <br>*optional*|string|
|**paid**  <br>*optional*|boolean|
|**refunded**  <br>*optional*|boolean|
|**refunds**  <br>*optional*|[ChargeRefundCollection](#chargerefundcollection)|
|**reversed**  <br>*optional*|boolean|
|**subject**  <br>*optional*|string|
|**timeExpire**  <br>*optional*|integer (int64)|
|**timePaid**  <br>*optional*|integer (int64)|
|**timeSettle**  <br>*optional*|integer (int64)|
|**transactionNo**  <br>*optional*|string|


<a name="chargeinforequest"></a>
### ChargeInfoRequest

|Name|Description|Schema|
|---|---|---|
|**channel**  <br>*required*|支付渠道|enum (alipay, wx, qpay)|
|**created**  <br>*optional*|对象的创建时间|string (date-time)|
|**ending_before**  <br>*optional*|决定了列表的最末项在何处结束|string|
|**limit**  <br>*optional*|限制有多少对象可以被返回|integer (int32)|
|**paid**  <br>*optional*|是否已付款|boolean|
|**refunded**  <br>*optional*|是否存在退款信息，无论退款是否成功|boolean|
|**reversed**  <br>*optional*|是否已撤销|boolean|
|**starting_after**  <br>*optional*|决定了列表的第一项从何处开始|string|


<a name="chargerefundcollection"></a>
### ChargeRefundCollection

|Name|Schema|
|---|---|
|**data**  <br>*optional*|< [Refund](#refund) > array|
|**hasMore**  <br>*optional*|boolean|
|**object**  <br>*optional*|string|
|**url**  <br>*optional*|string|


<a name="chargerequest"></a>
### ChargeRequest

|Name|Description|Schema|
|---|---|---|
|**amount**  <br>*required*|订单总金额|number (double)|
|**body**  <br>*required*|商品描述信息|string|
|**channel**  <br>*required*|支付渠道|enum (alipay, wx, qpay)|
|**description**  <br>*optional*|订单附加说明|string|
|**referenceId**  <br>*optional*|商品分类主键id|integer (int32)|
|**subject**  <br>*required*|商品标题|string|
|**type**  <br>*optional*|(1、购买课程；2、购买专栏；3、购买课程推广；4、VIP)|integer (int32)|
|**userId**  <br>*optional*|用户id|integer (int32)|


<a name="columndetailedinfo"></a>
### ColumnDetailedInfo

|Name|Description|Schema|
|---|---|---|
|**applyCount**  <br>*optional*|专栏订阅人数|integer (int32)|
|**cover**  <br>*optional*|专栏封面url|string|
|**id**  <br>*optional*|专栏id|integer (int32)|
|**intro**  <br>*optional*|专栏介绍|string|
|**name**  <br>*optional*|专栏标题|string|
|**price**  <br>*optional*|专栏价格|number (double)|
|**status**  <br>*optional*|状态（0、未订阅；1、已订阅）|integer (int32)|
|**version**  <br>*optional*|专栏更新期数|integer (int32)|


<a name="columninfo"></a>
### ColumnInfo

|Name|Description|Schema|
|---|---|---|
|**applyCount**  <br>*optional*|专栏订阅人数|integer (int32)|
|**cover**  <br>*optional*|专栏封面url|string|
|**id**  <br>*optional*|专栏id|integer (int32)|
|**name**  <br>*optional*|专栏标题|string|
|**price**  <br>*optional*|专栏价格|number (double)|
|**version**  <br>*optional*|专栏更新期数|integer (int32)|


<a name="columninforequest"></a>
### ColumnInfoRequest

|Name|Description|Schema|
|---|---|---|
|**courseIdList**  <br>*optional*|专栏包含课程列表|< integer (int32) > array|
|**intro**  <br>*optional*|专栏简介|string|
|**price**  <br>*optional*|专栏价格|number (double)|
|**proportion**  <br>*optional*|专栏分销比例|integer (int32)|
|**purl**  <br>*optional*|专栏海报URL|string|
|**title**  <br>*optional*|专栏名称|string|
|**userVip**  <br>*optional*|vip是否可以免费观看(1、可观看；2、不可观看)|integer (int32)|


<a name="coursecategoryresponse"></a>
### CourseCategoryResponse

|Name|Description|Schema|
|---|---|---|
|**id**  <br>*optional*|分类id|integer (int32)|
|**name**  <br>*optional*|分类名称|string|
|**purl**  <br>*optional*|分类图片URL|string|
|**turl**  <br>*optional*|分类内容URL|string|


<a name="courseinfo"></a>
### CourseInfo

|Name|Description|Schema|
|---|---|---|
|**applyCount**  <br>*optional*|课程报名人数|integer (int32)|
|**column**  <br>*optional*|课程所属专栏标题|string|
|**cover**  <br>*optional*|课程封面URL|string|
|**id**  <br>*optional*|课程id|integer (int32)|
|**price**  <br>*optional*|课程价格|number (double)|
|**startTime**  <br>*optional*|课程开始时间|string (date-time)|
|**status**  <br>*optional*|课程状态（0、即将开课；1、往期课程）|integer (int32)|
|**title**  <br>*optional*|课程标题|string|
|**type**  <br>*optional*|课程播放方式(1、直播；2、录播)|integer (int32)|


<a name="courseinforequest"></a>
### CourseInfoRequest

|Name|Description|Schema|
|---|---|---|
|**columnIdList**  <br>*optional*|课程所属专栏列表|< integer (int32) > array|
|**intro**  <br>*required*|课程简介|string|
|**price**  <br>*optional*|课程价格|number (double)|
|**proportion**  <br>*optional*|课程分销比例|integer (int32)|
|**purl**  <br>*optional*||string|
|**startTime**  <br>*required*|直播课程开始时间|string (date-time)|
|**title**  <br>*required*|课程标题|string|
|**type**  <br>*required*|课程播放方式(1、直播；2、录播)|integer (int32)|
|**userVip**  <br>*optional*|vip是否可以免费观看(1、可观看；2、不可观看)|integer (int32)|
|**vurl**  <br>*optional*||string|


<a name="event"></a>
### Event

|Name|Schema|
|---|---|
|**created**  <br>*optional*|integer (int64)|
|**data**  <br>*optional*|[EventData](#eventdata)|
|**id**  <br>*optional*|string|
|**livemode**  <br>*optional*|boolean|
|**object**  <br>*optional*|string|
|**pendingWebhooks**  <br>*optional*|integer (int32)|
|**request**  <br>*optional*|string|
|**type**  <br>*optional*|string|


<a name="eventdata"></a>
### EventData

|Name|Schema|
|---|---|
|**object**  <br>*optional*|[PingppObject](#pingppobject)|


<a name="invitationcoderesponse"></a>
### InvitationCodeResponse

|Name|Description|Schema|
|---|---|---|
|**invitationCode**  <br>*optional*|代理商邀请码|string|


<a name="jwtauthenticationrequest"></a>
### JwtAuthenticationRequest

|Name|Description|Schema|
|---|---|---|
|**password**  <br>*optional*|登陆凭证|string|
|**username**  <br>*required*|登陆标志|string|


<a name="mycourseinforesponse"></a>
### MyCourseInfoResponse

|Name|Description|Schema|
|---|---|---|
|**columnName**  <br>*optional*|课程所属专栏|string|
|**id**  <br>*optional*|课程id|integer (int32)|
|**intro**  <br>*optional*|课程简介|string|
|**popularity**  <br>*optional*|课程报名人数|integer (int32)|
|**price**  <br>*optional*|课程价格|number (double)|
|**purl**  <br>*optional*||string|
|**startTime**  <br>*optional*|课程开始时间|string (date-time)|
|**title**  <br>*optional*|课程标题|string|
|**vurl**  <br>*optional*||string|


<a name="othercourseresponse"></a>
### OtherCourseResponse

|Name|Description|Schema|
|---|---|---|
|**columnName**  <br>*optional*|课程所属专栏|string|
|**id**  <br>*optional*|课程id|integer (int32)|
|**intro**  <br>*optional*|课程简介|string|
|**isPurchased**  <br>*optional*|是否已购买（0、未购买；1、已购买）|integer (int32)|
|**popularity**  <br>*optional*|课程报名人数|integer (int32)|
|**price**  <br>*optional*|课程价格|number (double)|
|**purl**  <br>*optional*||string|
|**startTime**  <br>*optional*|课程开始时间|string (date-time)|
|**status**  <br>*optional*|课程状态（0、未开始；1、直播中；2、已结束）|integer (int32)|
|**title**  <br>*optional*|课程标题|string|
|**vurl**  <br>*optional*||string|


<a name="ownaccount"></a>
### OwnAccount

|Name|Description|Schema|
|---|---|---|
|**columnCount**  <br>*optional*|专栏数量|integer (int32)|
|**courseCount**  <br>*optional*|课程数量|integer (int32)|
|**name**  <br>*optional*|账号名称|string|
|**remark**  <br>*optional*|备注|string|


<a name="refund"></a>
### Refund

|Name|Schema|
|---|---|
|**amount**  <br>*optional*|integer (int32)|
|**charge**  <br>*optional*|string|
|**chargeOrderNo**  <br>*optional*|string|
|**created**  <br>*optional*|integer (int64)|
|**description**  <br>*optional*|string|
|**extra**  <br>*optional*|object|
|**failureCode**  <br>*optional*|string|
|**failureMsg**  <br>*optional*|string|
|**fundingSource**  <br>*optional*|string|
|**id**  <br>*optional*|string|
|**instanceURL**  <br>*optional*|string|
|**metadata**  <br>*optional*|object|
|**object**  <br>*optional*|string|
|**orderNo**  <br>*optional*|string|
|**status**  <br>*optional*|string|
|**succeed**  <br>*optional*|boolean|
|**timeSucceed**  <br>*optional*|integer (int64)|
|**transactionNo**  <br>*optional*|string|


<a name="result"></a>
### Result

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|object|


<a name="6e19ae3778206bc43f425a197381d852"></a>
### Result«ArtExamResponse»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[ArtExamResponse](#artexamresponse)|


<a name="49388d7900970b46060e9d527b638985"></a>
### Result«Charge»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[Charge](#charge)|


<a name="8a5e080566190d83027b07a771365b3a"></a>
### Result«ColumnDetailedInfo»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[ColumnDetailedInfo](#columndetailedinfo)|


<a name="1f4d2ea159babc9f8eb682e1044c7a13"></a>
### Result«CourseCategoryResponse»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[CourseCategoryResponse](#coursecategoryresponse)|


<a name="167809a709332d77e037c7fc178f6654"></a>
### Result«InvitationCodeResponse»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[InvitationCodeResponse](#invitationcoderesponse)|


<a name="2f7288fea226c87f5422694ceb6afb39"></a>
### Result«List«CategoryInfoResponse»»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|< [CategoryInfoResponse](#categoryinforesponse) > array|


<a name="6a96bf7d7891cf88e83a890f9098fc65"></a>
### Result«List«ColumnInfo»»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|< [ColumnInfo](#columninfo) > array|


<a name="4193b6be26076451ddd1429bdefb30d1"></a>
### Result«List«CourseInfo»»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|< [CourseInfo](#courseinfo) > array|


<a name="4391b12250ec286853e8c79bd54b2d8f"></a>
### Result«List«OwnAccount»»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|< [OwnAccount](#ownaccount) > array|


<a name="d4f964280c424c80275b08cfcda42bb7"></a>
### Result«List«TeacherInfo»»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|< [TeacherInfo](#teacherinfo) > array|


<a name="f90a2e2125dfba4bfe229e303cd84e69"></a>
### Result«List«TransactionInfoResponse»»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|< [TransactionInfoResponse](#transactioninforesponse) > array|


<a name="c70e8b124faf546fcbc995753bdc1296"></a>
### Result«List«VipCategory»»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|< [VipCategory](#vipcategory) > array|


<a name="ede257762e73e03b7c9a34bad45c08c8"></a>
### Result«MyCourseInfoResponse»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[MyCourseInfoResponse](#mycourseinforesponse)|


<a name="1714742e56dbe13610aaf96f5f89c45d"></a>
### Result«OtherCourseResponse»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[OtherCourseResponse](#othercourseresponse)|


<a name="aeb5b6ea75443f89921595bb92990a88"></a>
### Result«SearchResultResponse»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[SearchResultResponse](#searchresultresponse)|


<a name="bf0baaf1c0552164c457982fb05a6aea"></a>
### Result«TeacherInfo»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[TeacherInfo](#teacherinfo)|


<a name="7411f1799a717b29719335939221ca10"></a>
### Result«UserAccountInfoResponse»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[UserAccountInfoResponse](#useraccountinforesponse)|


<a name="fb114b83be62b7f3b2f3c68bf9660027"></a>
### Result«UserInfoResponse»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[UserInfoResponse](#userinforesponse)|


<a name="74f23cc419163e960fe338ae58a92271"></a>
### Result«UserLiveRoomResponse»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[UserLiveRoomResponse](#userliveroomresponse)|


<a name="94f392d840d913506bf03d5864049add"></a>
### Result«VipCategoryResponse»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[VipCategoryResponse](#vipcategoryresponse)|


<a name="fae570a3bcc924fe2df6590768714126"></a>
### Result«VipInfoResponse»

|Name|Description|Schema|
|---|---|---|
|**code**  <br>*optional*|返回码|integer (int32)|
|**msg**  <br>*optional*|返回信息|string|
|**result**  <br>*optional*|返回具体内容|[VipInfoResponse](#vipinforesponse)|


<a name="searchresultresponse"></a>
### SearchResultResponse

|Name|Description|Schema|
|---|---|---|
|**columnInfoList**  <br>*optional*|讲师列表|< [ColumnInfo](#columninfo) > array|
|**courseInfoList**  <br>*optional*|讲师列表|< [CourseInfo](#courseinfo) > array|
|**teacherInfoList**  <br>*optional*|讲师列表|< [TeacherInfo](#teacherinfo) > array|


<a name="teacherinfo"></a>
### TeacherInfo

|Name|Description|Schema|
|---|---|---|
|**avatarUrl**  <br>*optional*|讲师头像URL|string|
|**id**  <br>*optional*|讲师id|integer (int32)|
|**intro**  <br>*optional*|机构或用户简介|string|
|**nickname**  <br>*optional*|机构或用户昵称|string|


<a name="transactioninforesponse"></a>
### TransactionInfoResponse

|Name|Description|Schema|
|---|---|---|
|**avatarUrl**  <br>*optional*|用户头像URL|string|
|**id**  <br>*optional*|交易记录id|integer (int32)|
|**nickname**  <br>*optional*|用户昵称|string|
|**price**  <br>*optional*|购买时的价格|number (double)|
|**time**  <br>*optional*|购买时间|string (date-time)|
|**title**  <br>*optional*|购买的课程/专栏标题|string|
|**type**  <br>*optional*|购买类型（1、专栏；2、课程）|integer (int32)|


<a name="updatecolumninforequest"></a>
### UpdateColumnInfoRequest

|Name|Description|Schema|
|---|---|---|
|**columnId**  <br>*optional*|专栏id|integer (int32)|
|**intro**  <br>*optional*|专栏简介|string|
|**price**  <br>*optional*|专栏价格|number (double)|
|**proportion**  <br>*optional*|专栏分销比例|integer (int32)|
|**purl**  <br>*optional*|专栏海报URL|string|
|**title**  <br>*optional*|专栏名称|string|
|**userVip**  <br>*optional*|vip是否可以免费观看(1、可观看；2、不可观看)|integer (int32)|


<a name="updatecourseinforequest"></a>
### UpdateCourseInfoRequest

|Name|Description|Schema|
|---|---|---|
|**columnId**  <br>*optional*|课程所属专栏|integer (int32)|
|**id**  <br>*required*|课程id|integer (int32)|
|**intro**  <br>*required*|课程简介|string|
|**price**  <br>*optional*|课程价格|number (double)|
|**proportion**  <br>*optional*|课程分销比例|string|
|**startTime**  <br>*optional*|直播课程开始时间|string (date-time)|
|**title**  <br>*required*|课程标题|string|
|**useVip**  <br>*optional*|vip是否可以免费观看(1、可观看；2、不可观看)|integer (int32)|
|**vurl**  <br>*optional*||string|


<a name="useraccountinforesponse"></a>
### UserAccountInfoResponse

|Name|Description|Schema|
|---|---|---|
|**hasWithdrawal**  <br>*optional*|已提现金额|number (double)|
|**notWithdrawal**  <br>*optional*|可提现金额|number (double)|
|**url**  <br>*optional*|公众号链接|string|


<a name="userinforequest"></a>
### UserInfoRequest

|Name|Description|Schema|
|---|---|---|
|**intro**  <br>*optional*|课程简介|string|
|**title**  <br>*optional*|课程名称|string|
|**url**  <br>*optional*|课程海报url|string|


<a name="userinforesponse"></a>
### UserInfoResponse

|Name|Description|Schema|
|---|---|---|
|**avatarUrl**  <br>*optional*|用户头像URL|string|
|**code**  <br>*optional*|用户编码|string|
|**intro**  <br>*optional*|用户简介|string|
|**invitationCode**  <br>*optional*|用户邀请码|string|
|**nickName**  <br>*optional*|用户昵称|string|
|**roles**  <br>*optional*|用户角色|< string > array|
|**status**  <br>*optional*|用户状态(1、正在使用；0、已停用；2、已认证)|integer (int32)|


<a name="userliveroomresponse"></a>
### UserLiveRoomResponse

|Name|Description|Schema|
|---|---|---|
|**attention**  <br>*optional*|用户人气|integer (int32)|
|**avatarUrl**  <br>*optional*|用户头像URL|string|
|**code**  <br>*optional*|用户编码|string|
|**intro**  <br>*optional*|用户简介|string|
|**isAttention**  <br>*optional*|是否已关注|integer (int32)|
|**nickName**  <br>*optional*|用户昵称|string|
|**storesUrl**  <br>*optional*|用户个人店铺URL|string|


<a name="vipcategory"></a>
### VipCategory

|Name|Description|Schema|
|---|---|---|
|**amount**  <br>*optional*|金额：元|number (double)|
|**monthCount**  <br>*optional*|当status为0时，为天数；当status为1时，为月份|integer (int32)|
|**status**  <br>*optional*|0：试用vip；1、正式vip|integer (int32)|


<a name="vipcategoryresponse"></a>
### VipCategoryResponse

|Name|Description|Schema|
|---|---|---|
|**categoryId**  <br>*optional*|分类id|integer (int32)|
|**price**  <br>*optional*|vip价格|number (double)|
|**timeLength**  <br>*optional*|vip时长|integer (int32)|
|**type**  <br>*optional*|vip类型(0、非vip；1、普通vip；2、超级vip；3、试用vip)|number (double)|
|**vipIntro**  <br>*optional*|vip权益介绍|string|


<a name="vipinforesponse"></a>
### VipInfoResponse

|Name|Description|Schema|
|---|---|---|
|**endTime**  <br>*optional*|vip到期时间|string (date-time)|
|**type**  <br>*optional*|vip类型(0、非vip；1、普通vip；2、超级vip；3、试用vip)|integer (int32)|


<a name="withdrawalsrequest"></a>
### WithdrawalsRequest

|Name|Description|Schema|
|---|---|---|
|**amount**  <br>*optional*|提现金额|integer (int32)|
|**description**  <br>*optional*|备注信息|string|


<a name="ab08e325c45a0150a4ae9fca3a9d7e96"></a>
### 用户注册请求参数

|Name|Description|Schema|
|---|---|---|
|**identityType**  <br>*optional*|登陆类型（1、微信；2、QQ；3、账号密码）|integer (int32)|
|**invitationCode**  <br>*optional*|用户登陆邀请码|string|
|**password**  <br>*required*|用户登陆凭证|string|
|**roleList**  <br>*required*|用户角色列表|< string > array|
|**username**  <br>*required*|用户登陆标志|string|



