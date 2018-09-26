package com.qjd.rry.request.V0;

import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-28 15:02
 **/
@Data
public class EventRequestV0 {
    private Object object;

    @Override
    public String toString() {
        return "EventRequestV0{" +
                "object=" + object +
                '}';
    }
}
