package com.qjd.rry.request.V0;

import com.qjd.rry.entity.User;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-18 11:00
 **/
@Data
public class PurchaseData {

    private User shareUser;//课程/专栏分享者

    private User ownUser;//课程/专栏拥有者

    private User proUser;//代理商

    private User purchaseUser;//购买者

    //private BigDecimal userProportion;//课程分销比例

    private BigDecimal sharePumpCoin = new BigDecimal(0);//分享者抽成金额

    private  BigDecimal proProportion;//代理商抽成比例

    private BigDecimal proPumpCoin = new BigDecimal(0);//代理商抽成金额

    private  BigDecimal platformProportion;//平台抽取比例

    private  BigDecimal platformPumpCoin;//平台抽取金额

    private BigDecimal amount;//此次操作的总金额
}
