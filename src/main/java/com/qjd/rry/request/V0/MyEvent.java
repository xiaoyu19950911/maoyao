package com.qjd.rry.request.V0;

import lombok.Data;

import java.sql.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-17 10:03
 **/
@Data
public class MyEvent {

    private String id;//事件id

    private Boolean liveMode;//事件是否发生在生产环境。

    private Date createdTime;//时间创建时间

    private Integer pendingWebhooks;//推送未成功的 webhooks 数量。

    private String type;//事件类型

    private String request;//API Request ID。值 "null" 表示该事件不是由 API 请求触发的。

    private PingData data;//具体数据

    private Boolean result;//验签结果

}
