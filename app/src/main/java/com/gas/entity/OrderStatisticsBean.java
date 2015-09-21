package com.gas.entity;

/**
 * Created by Administrator on 2015/9/11.
 */
public class OrderStatisticsBean {
    private String countOrder;
    private String  count3;
    private String  count2;
    private String   count1;
    private String   total_cost;
    private String total_h_cost;
    private String  total_wx_cost;

    public String getCountOrder() {
        return countOrder;
    }

    public String getCount3() {
        return count3;
    }

    public void setCount3(String count3) {
        this.count3 = count3;
    }

    public String getCount2() {
        return count2;
    }

    public void setCount2(String count2) {
        this.count2 = count2;
    }

    public String getCount1() {
        return count1;
    }

    public void setCount1(String count1) {
        this.count1 = count1;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getTotal_h_cost() {
        return total_h_cost;
    }

    public void setTotal_h_cost(String total_h_cost) {
        this.total_h_cost = total_h_cost;
    }

    public String getTotal_wx_cost() {
        return total_wx_cost;
    }

    public void setTotal_wx_cost(String total_wx_cost) {
        this.total_wx_cost = total_wx_cost;
    }

    public void setCountOrder(String countOrder) {
        this.countOrder = countOrder;
    }
}
