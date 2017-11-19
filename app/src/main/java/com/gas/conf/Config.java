package com.gas.conf;

/**
 * 系统配置
 * Created by Heart on 2015/7/16.
 */
public class Config {
    public static boolean DEBUG = true;
    public static int PLAT = 1;
    public static String APP_VERSION = "2.0";
    public static String HOST ="http://www.lintianranqi.com/index.php";
    public static String BASE_URL =HOST+"?g=Admin&m=Android";
    public static String localTime =HOST+"/Admin/Android/localtime";
    public static String clientBottle= HOST+"/admin/Android/client_bottle";
    public static String saveClientBottle= HOST+"/admin/Android/client_bottle_action";
    public static String loginUrl = BASE_URL+"&a=login";
    public static String clockInUrl = BASE_URL+ "&a=checking";
    public static String checkInfo =  BASE_URL+ "&a=check_all";
    public static String deliveryHistoryOrder =   BASE_URL+ "&a=order";
    public static String newDeliverOrder = BASE_URL+"&a=new_order";
    public static String onDeliverOrder =BASE_URL+ "&a=on_order";
    public static String getDeliverOrder = BASE_URL+"&a=get_order";
    public static String rejectDeliverOrder= BASE_URL+"&a=reject_order";
    public static String deliverOrderlist= BASE_URL+"&a=order_list";
    public static String finishOrder = BASE_URL+"&a=finish_order";


    public static String  repairOrderHistory = BASE_URL+"&a=repair_order";
    public static String  newRepairOrder = BASE_URL+"&a=new_repair_order";
    public static String  onRepairOrder = BASE_URL+"&a=on_repair_order";
    public static String  getRepairOrder = BASE_URL+"&a=get_repair_order";
    public static String  rejectRepairOrder = BASE_URL+"&a=reject_repair_order";
    public static String  finishRepairOrder = BASE_URL+"&a=finish_repair_order";
    public static String  gasBottleOut = BASE_URL+"&a=gas_bottle_out";
    public static String  gasBottleIn = BASE_URL+"&a=gas_bottle_in";
    public static String  finishRepariOrder = BASE_URL+"&a=finish_repair_order";

    public static String searchBottle = BASE_URL+"&a=select_bottle";
    public static String bottleLog = BASE_URL+"&a=select_bottle_log";
    public static String selectBottle = BASE_URL+"&a=select_bottle";
    public static String bottleFullin = BASE_URL +"&a=gas_bottle_full_in";
    public static String getCar = BASE_URL+"&a=car";
    public static String wrapCar = BASE_URL+"&a=car_wrap";
    public static String unwrapCar= BASE_URL+"&a=car_unwrap";
    public static String siteCar = BASE_URL+"&a=car_site";

    public static String check = BASE_URL +"&a=check";
    public static String checkSearch = BASE_URL +"&a=check_search";

    public static String oilLog = BASE_URL +"&a=oil_log";
    public static String carOil = BASE_URL +"&a=car_oil";

    public static String order_statistics = BASE_URL +"&a=driver_order";
    public static String del_site = BASE_URL +"&a=del_site";
}
/**
 *
 * http://www.weithink.com.cn/ranqi/index.php?g=Admin&m=Android&a=driver_order

 $data = array (
 'driver_id' => '33',                //员工id
 'start_time' => '1439136000',       //开始时间
 'end_time' => '1441814400',         //结束时间
 );
 bg_marking.png

 返回json数组
 $apk_count = array(8) {   ["start_time"] => string(10) "1439136000"     //开始时间
 ["end_time"] => string(10) "1441814400"	  //结束时间
 ["driver_name"] => string(12) "测试人员"      //员工名字
 ["countOrder"] => string(1) "2"		  //订单数
 ["count3"] => int(0)				  //50KG
 ["count2"] => int(0)				  //15KG
 ["count1"] => int(1)				  //5KG
 ["total_cost"] => string(6) "641.00"		  //总金额
 ["total_h_cost"] => string(6) "641.00"	  //货到付款金额
 ["total_wx_cost"] => int(0)     		  //微支付金额 }

 */
