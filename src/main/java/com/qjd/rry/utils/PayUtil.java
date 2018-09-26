package com.qjd.rry.utils;

import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Transfer;
import com.pingplusplus.model.User;
import com.qjd.rry.entity.Order;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.request.ChargeRequest;
import com.qjd.rry.request.PayUserRequest;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PayUtil {

    public static Charge createCharge(ChargeRequest request, String clientIP, String appID, Order order) {
        Charge charge = null;
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("order_no", request.getOrderId());
        chargeParams.put("amount", order.getAmount().multiply(new BigDecimal(100)));
        Map<String, String> app = new HashMap<>();
        app.put("id", appID);
        chargeParams.put("app", app);
        chargeParams.put("description",order.getDescription());
        chargeParams.put("channel", request.getChannel());
        Map<String,String> extra=new HashMap<>();
        Map<String,Object> metadata =new HashMap<>();
        metadata.put("user_id",order.getUserId());
        if (request.getChannel().equals("alipay_wap"))
            extra.put("success_url",request.getUrl());
        if("qpay".equals(request.getChannel()))
            extra.put("device",order.getDevice());
        if("wx_pub".equals(request.getChannel()))
            extra.put("open_id",request.getOpenId());
        chargeParams.put("extra",extra);
        chargeParams.put("metadata",metadata);
        chargeParams.put("currency", "cny");//cny:人民币
        chargeParams.put("client_ip", clientIP);
        chargeParams.put("subject", order.getSubject());//订单支付页面显示的商品信息
        chargeParams.put("body", order.getBody());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);//15分钟失效
        long timestamp = calendar.getTimeInMillis();
        chargeParams.put("time_expire", timestamp/1000);//订单失效时间，用 Unix 时间戳表示。时间范围在订单创建后的 1 分钟到 15 天，默认为 1 天，创建时间以 Ping++ 服务器时间为准。 微信对该参数的有效值限制为 2 小时内；银联对该参数的有效值限制为 1 小时内。
        try {
            //发起交易请求
            charge = Charge.create(chargeParams);
        } catch (PingppException e) {
            e.printStackTrace();
            throw new CommunalException(-1,e.getMessage());
        }
        return charge;
    }

    public User createUser(PayUserRequest request) {
        User user = null;
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("id", request.getId());
        chargeParams.put("address", request.getAddress());
        chargeParams.put("avatar", request.getAvatar());
        chargeParams.put("email", request.getEmail());
        chargeParams.put("gender", request.getGender());
        chargeParams.put("mobile", request.getMobile());
        chargeParams.put("name", request.getName());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);//15分钟失效
        long timestamp = calendar.getTimeInMillis();
        //chargeParams.put("time_expire", timestamp);//订单失效时间，用 Unix 时间戳表示。时间范围在订单创建后的 1 分钟到 15 天，默认为 1 天，创建时间以 Ping++ 服务器时间为准。 微信对该参数的有效值限制为 2 小时内；银联对该参数的有效值限制为 1 小时内。
        try {
            user = User.create(chargeParams);
        } catch (PingppException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static Transfer createTransfer(Order order, String appID) throws RateLimitException, APIException, ChannelException, InvalidRequestException, APIConnectionException, AuthenticationException {
        Map<String, Object> transfer = new HashMap<>();
        transfer.put("amount", order.getAmount().multiply(new BigDecimal(100)));
        transfer.put("currency", "cny");
        transfer.put("type",  "b2c");
        Map<String,Object> metadata =new HashMap<>();
        metadata.put("user_id",order.getUserId());
        transfer.put("metadata",metadata);
        transfer.put("order_no", order.getId());
        transfer.put("channel",  "wx_pub");//支付方式
        transfer.put("recipient", order.getOpenId());
        transfer.put("description", order.getDescription());
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", appID);
        transfer.put("app", app);
        return Transfer.create(transfer);
    }

}
