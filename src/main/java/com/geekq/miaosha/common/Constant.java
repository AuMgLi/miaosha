package com.geekq.miaosha.common;

public class Constant {
    public static String CLOSE_ORDER_INFO_TASK_LOCK = "CLOSE_ORDER_INFO_KEY";

    public static String COUNT_LOGIN = "count:login";

    public enum orderStatus {
        ORDER_NOT_PAY("新建未支付");

        orderStatus(String name){
            this.name=name;
        }

        private  String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
