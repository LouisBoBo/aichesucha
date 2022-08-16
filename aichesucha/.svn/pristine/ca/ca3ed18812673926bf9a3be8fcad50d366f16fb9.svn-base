package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 用户列表
 */
public class AccountListResultBean extends BaseBean {

    /**
     * info : [{"account":"ac6e8e15-dd72-41ee-9a30-7a35e1d7b5a8","email":"5a8b39ac-7f17-475a-8038-a01f6a756df4","is_mine":true,"nick_name":"6cfcd7d6-412c-475b-99a5-843134bf36ec","phone":"ee49f89a-abfd-44da-b34a-66cdc36ca223","role":"e_role_user|e_role_manager","uid":"07360d62-0503-4ab5-a079-1452fe604435"},{"account":"f8116956-4a99-44dd-9f4b-3825c4090e96","email":"6a931fc4-e915-4023-83d0-fcada59c8b8a","is_mine":true,"nick_name":"52fdfd98-dd0d-4b50-b6bc-b078c439d787","phone":"2dd395be-6d0f-4371-8c1e-717471a2bdba","role":"e_role_user|e_role_manager","uid":"1c6cd31b-0ad7-42b6-96d1-a77adb1d1a86"}]
     * total : 874053456
     */

    private int total; // uid总量
    private List<InfoBean> info;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * account : ac6e8e15-dd72-41ee-9a30-7a35e1d7b5a8
         * email : 5a8b39ac-7f17-475a-8038-a01f6a756df4
         * is_mine : true
         * nick_name : 6cfcd7d6-412c-475b-99a5-843134bf36ec
         * phone : ee49f89a-abfd-44da-b34a-66cdc36ca223
         * role : e_role_user|e_role_manager
         * uid : 07360d62-0503-4ab5-a079-1452fe604435
         */

        private String account; // 账号
        private String email; // 邮箱
        private boolean is_mine; // 是否是当前用户
        private String nick_name; // 昵称
        private String phone; // 手机号
        private String role; // 用户类型,e_role_user 普通用户权限,e_role_manager 管理员权限
        private String uid; // 用户加密后的uid,注册
        private boolean isSelect = false; // 是否选中

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isIs_mine() {
            return is_mine;
        }

        public void setIs_mine(boolean is_mine) {
            this.is_mine = is_mine;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
