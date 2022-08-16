package com.car.scth.mvp.model.bean;


import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * TODO<json数据源>
 *
 * @author: Kangyu
 * @date: 2020/5/25 15:36
 */

public class JsonBean implements IPickerViewData {


    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    private String name;
    private List<CityBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCityList() {
        return city;
    }

    public void setCityList(List<CityBean> city) {
        this.city = city;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.name;
    }


    public static class CityBean {

        /**
         * name : 北京市
         * area : [{"area_name":"市辖区","area_code":"110100"},{"area_name":"东城区","area_code":"110101"},{"area_name":"西城区","area_code":"110102"},{"area_name":"朝阳区","area_code":"110105"},{"area_name":"丰台区","area_code":"110106"},{"area_name":"石景山区","area_code":"110107"},{"area_name":"海淀区","area_code":"110108"},{"area_name":"门头沟区","area_code":"110109"},{"area_name":"房山区","area_code":"110111"},{"area_name":"通州区","area_code":"110112"},{"area_name":"顺义区","area_code":"110113"},{"area_name":"昌平区","area_code":"110114"},{"area_name":"大兴区","area_code":"110115"},{"area_name":"怀柔区","area_code":"110116"},{"area_name":"平谷区","area_code":"110117"},{"area_name":"密云区","area_code":"110118"},{"area_name":"延庆区","area_code":"110119"}]
         */

        private String name; // 城市名称
        private List<AreaBean> area;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<AreaBean> getArea() {
            return area;
        }

        public void setArea(List<AreaBean> area) {
            this.area = area;
        }

        public static class AreaBean {
            /**
             * area_name : 市辖区
             * area_code : 110100
             */

            private String area_name; // 区域名称
            private String area_code; // 区域编码

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public String getArea_code() {
                return area_code;
            }

            public void setArea_code(String area_code) {
                this.area_code = area_code;
            }
        }
    }
}
