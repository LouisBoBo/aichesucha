package com.car.scth.mvp.model.putbean;

public class AddFamilyPutBean {

    /**
     * {
     * "module": "user",
     * "func": "AddNextAccount",
     * "params": {
     * "parent_id": "fb261e6f-2bea-4bac-8ab2-81b2e5ce06df",
     * "info": {
     * "role": "e_role_manager|e_role_user",
     * "pwd": "44a6c351-4188-46fd-8e84-a67e4870a15a",
     * "email": "ce2f3883-982a-445a-a325-d24536490f3c",
     * "account": "ccb20ed2-04d6-4821-893b-d2d479e1bd5f"
     * },
     * "add_org_name": "4c19bf92-9bf4-468a-b053-7f9ef099ad8b"
     * }
     * }
     */

    private String module;
    private String func;
    private AddFamilyPutBean.ParamsBean params;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        private String parent_id;
        private String family_name;
        private ParamsBean.InfoBean info;

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getFamily_name() {
            return family_name;
        }

        public void setFamily_name(String family_name) {
            this.family_name = family_name;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            private String pwd;
            private String email;
            private String account;
            private String nick_name;


            public String getPwd() {
                return pwd;
            }

            public void setPwd(String pwd) {
                this.pwd = pwd;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }
        }
    }

}
