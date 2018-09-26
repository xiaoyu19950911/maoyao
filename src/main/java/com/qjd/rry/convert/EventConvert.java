package com.qjd.rry.convert;

import com.pingplusplus.model.*;
import com.qjd.rry.entity.RryEvent;
import com.qjd.rry.request.EventRequest;
import com.qjd.rry.request.V0.MyEvent;
import com.qjd.rry.request.V0.PingData;

import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-28 15:12
 **/
public class EventConvert {

    public static Event EventRequestToEvent(EventRequest request) {
        Event event = new Event();
        event.setId(request.getId());
        event.setType(request.getType());
        EventData eventData=new EventData();
        Object object= Webhooks.getObject(request.toString());
        switch (request.getType()){
            case "charge.succeeded":
                eventData.setObject((Charge)object);
                break;
        }
        event.setData(eventData);
        event.setCreated(request.getCreated());
        event.setLivemode(request.getLivemode());
        event.setPendingWebhooks(request.getPendingWebhooks());
        event.setRequest(request.getRequest());
        event.setObject(request.getObject());
        return event;
    }

    public static RryEvent EventToRryEvent(MyEvent event){
        RryEvent rryEven=new RryEvent();
        rryEven.setCreated(new Date());
        rryEven.setEventId(event.getId());
        rryEven.setCreateTime(new Date());
        rryEven.setUpdateTime(new Date());
        rryEven.setLiveMode(event.getLiveMode());
        rryEven.setObject("event");
        return rryEven;
    }

    public static MyEvent EventToMyEvent(Event event,Boolean result){
        MyEvent myEvent = new MyEvent();
        myEvent.setId(event.getId());
        myEvent.setCreatedTime(new java.sql.Date(event.getCreated() * 1000));
        myEvent.setLiveMode(event.getLivemode());
        myEvent.setType(event.getType());
        myEvent.setRequest(event.getRequest());
        myEvent.setPendingWebhooks(event.getPendingWebhooks());
        myEvent.setResult(result);
        if (("charge.succeeded").equals(event.getType())) {
            PingData<Charge> pingData = new PingData<>();
            pingData.setObject((Charge) event.getData().getObject());
            myEvent.setData(pingData);
        } else if (("transfer.succeeded").equals(event.getType())) {
            PingData<Transfer> pingData = new PingData<>();
            pingData.setObject((Transfer) event.getData().getObject());
            myEvent.setData(pingData);
        }
        return myEvent;
    }
}
