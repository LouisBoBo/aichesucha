package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

import java.util.List;

public class AccountUserListResultBean extends BaseBean {
    /**
     * {
     *   "error_message": "3954ed70-eb3e-4c39-b9bb-b8b58f3f5fa4",
     *   "info": [
     *     {
     *       "account": "a876887e-4660-4415-91ec-13c5b7e214ea",
     *       "email": "5d6a6539-ccce-49fb-89b5-de5e6acbe31b",
     *       "is_mine": true,
     *       "nick_name": "a3644bb3-2caa-4b4e-b1ea-a8f7e6fde8ba",
     *       "phone": "1f1b61bf-4704-48ee-9010-8ea9f68630f1",
     *       "pwd": "143d43a6-fa4e-45c3-8f29-f9a57998c08e",
     *       "role": "e_role_manager|e_role_user",
     *       "uid": "5834f35c-77f4-473d-9bed-73c4d7bc0d9b"
     *     }
     *   ],
     *   "total": 1975301026,
     *   "errcode": 0
     * }
     */

    private int total;// 当前级用户总数
    private List<AccountUserListResultBean.InfoBean> info;

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


    public static class InfoBean{
        private String account;
        private String email;
        private boolean is_mine;
        private String nick_name;
        private String phone;
        private String pwd;
        private String role;
        private String uid;


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

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
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
