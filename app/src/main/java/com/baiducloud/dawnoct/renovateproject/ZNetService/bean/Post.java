package com.baiducloud.dawnoct.renovateproject.ZNetService.bean;

import java.io.Serializable;

/**
 * Created by DawnOct on 2017/7/7.
 */

/**
 * village : 新城阳光
 * district : 北京-通州
 * created_time : 2017-07-04T08:49:00Z
 * predict : 5天
 * fact : 5天
 * state : 进行中
 * service : {"name":"较完好墙面刷新","price":25,"describe":"适用于无墙面问题的情况"}
 * worker : {"name":"张三","tele":"110","num":10,"praise":"4.8"}
 * scheme : {"room":"主卧","damage_des":"墙体裂缝","measures":"加固\\刷新"}
 */
public class Post implements Serializable {
    private String pk;
    private String post_imag;
    private String village;
    private String district;
    private String created_time;
    private String predict;
    private String fact;
    private String state;
    private ServiceBean service;
    private WorkerBean worker;
    private SchemeBean scheme;

    public String getPost_imag() {
        return post_imag;
    }

    public void setPost_imag(String post_imag) {
        this.post_imag = post_imag;
    }


    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }


    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getPredict() {
        return predict;
    }

    public void setPredict(String predict) {
        this.predict = predict;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ServiceBean getService() {
        return service;
    }

    public void setService(ServiceBean service) {
        this.service = service;
    }

    public WorkerBean getWorker() {
        return worker;
    }

    public void setWorker(WorkerBean worker) {
        this.worker = worker;
    }

    public SchemeBean getScheme() {
        return scheme;
    }

    public void setScheme(SchemeBean scheme) {
        this.scheme = scheme;
    }


    public static class ServiceBean implements Serializable {
        /**
         * name : 较完好墙面刷新
         * price : 25
         * describe : 适用于无墙面问题的情况
         */

        private String name;
        private int price;
        private String describe;

        public ServiceBean(String name, int price, String describe) {
            this.name = name;
            this.price = price;
            this.describe = describe;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
    }

    public static class WorkerBean implements Serializable {
        /**
         * name : 张三
         * tele : 110
         * num : 10
         * praise : 4.8
         */
        private String pk;

        public String getPk() {
            return pk;
        }

        public void setPk(String pk) {
            this.pk = pk;
        }

        private String name;
        private String tele;
        private int num;
        private String praise;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTele() {
            return tele;
        }

        public void setTele(String tele) {
            this.tele = tele;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getPraise() {
            return praise;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }
    }

    public static class SchemeBean implements Serializable {
        /**
         * room : 主卧
         * damage_des : 墙体裂缝
         * measures : 加固\刷新
         */

        private String room;
        private String damage_des;
        private String measures;

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getDamage_des() {
            return damage_des;
        }

        public void setDamage_des(String damage_des) {
            this.damage_des = damage_des;
        }

        public String getMeasures() {
            return measures;
        }

        public void setMeasures(String measures) {
            this.measures = measures;
        }
    }
}
