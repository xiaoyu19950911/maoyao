package com.qjd.rry.convert;

import com.qjd.rry.entity.Vip;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.response.VipInfoResponse;
import com.qjd.rry.utils.TimeTransUtil;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-02 14:49
 **/
public class VipConvert {

    public static VipInfoResponse VipToVipInfoResponse(Vip vip){
        VipInfoResponse vipInfoResponse=new VipInfoResponse();
        if (CategoryEnums.CARD_YEAR.getCode().equals(vip.getType()))
            vipInfoResponse.setType(vip.getType());
        else
            vipInfoResponse.setType(vip.getType().substring(0,6));
        vipInfoResponse.setEndTime(TimeTransUtil.DateToUnix(vip.getEndTime()));
        return vipInfoResponse;
    }
}
