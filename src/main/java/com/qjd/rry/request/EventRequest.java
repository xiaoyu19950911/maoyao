package com.qjd.rry.request;

import com.qjd.rry.request.V0.EventRequestV0;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-28 14:59
 **/
@Data
public class EventRequest {

    private String id;

    private Long created;

    private Boolean livemode;

    private String type;

    private EventRequestV0 data;

    private String object;

    private String request;

    private Integer pendingWebhooks;

    @Override
    public String toString() {
        return "EventRequest{" +
                "id='" + id + '\'' +
                ", created=" + created +
                ", livemode=" + livemode +
                ", type='" + type + '\'' +
                ", data=" + data +
                ", object='" + object + '\'' +
                ", request='" + request + '\'' +
                ", pendingWebhooks=" + pendingWebhooks +
                '}';
    }
}
