package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 获取用户账号下设备分组列表，账号登录第一步，获取当前分组数
 */
public class AccountUserInfoResultBean extends BaseBean {

    /**
     * familyid : c0b6e803-5e82-43b4-8150-2609d3ae0acb
     * familys : [{"sid":"854aff75-62c5-474f-bbe3-b447f9dd4bf6","sname":"6ef9a454-4cf3-40e0-8293-02f71bfe70a7"}]
     * familys_total : 802393757
     * garages : [{"sid":"c33a20b5-4a70-4683-81d5-45e7e5b27be6","sname":"e866d4b0-d85d-467f-90e0-af5f590ea6e3"},{"sid":"deef90e5-4daa-4606-911e-bacb14024d81","sname":"d5dbd5b2-2b9c-4db8-9ae2-91b4e8dde3b3"},{"sid":"9b35bff1-83ae-49a2-8dab-f5c9e3d336fe","sname":"979002a8-f55b-47f4-aba6-7507a9137ad9"}]
     * garages_total : 802393757
     * errcode : 0
     */

    private String familyid; // 当前请求的组织id,为空表示最上一级
    private int familys_total; // 符合条件的车组织总数
    private int garages_total; // 符合条件的车组总数
    private List<FamilysBean> familys; // 车组织信息
    private List<GaragesBean> garages;

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid;
    }

    public int getFamilys_total() {
        return familys_total;
    }

    public void setFamilys_total(int familys_total) {
        this.familys_total = familys_total;
    }

    public int getGarages_total() {
        return garages_total;
    }

    public void setGarages_total(int garages_total) {
        this.garages_total = garages_total;
    }

    public List<FamilysBean> getFamilys() {
        return familys;
    }

    public void setFamilys(List<FamilysBean> familys) {
        this.familys = familys;
    }

    public List<GaragesBean> getGarages() {
        return garages;
    }

    public void setGarages(List<GaragesBean> garages) {
        this.garages = garages;
    }

    public static class FamilysBean {
        /**
         * sid : 854aff75-62c5-474f-bbe3-b447f9dd4bf6
         * sname : 6ef9a454-4cf3-40e0-8293-02f71bfe70a7
         */

        private String sid;
        private String sname; // 名称

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }
    }

    public static class GaragesBean {
        /**
         * sid : c33a20b5-4a70-4683-81d5-45e7e5b27be6
         * sname : e866d4b0-d85d-467f-90e0-af5f590ea6e3
         */

        private String sid;
        private String sname; // 名称

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }
    }
}
