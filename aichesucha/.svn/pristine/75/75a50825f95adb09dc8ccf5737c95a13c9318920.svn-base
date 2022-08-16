package com.car.scth.mvp.model.bean;

import com.google.gson.annotations.SerializedName;
import com.car.scth.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 围栏列表结果bean
 */
public class FenceResultBean extends BaseBean {

    /**
     * items : [{"enable":"e_enable_close|e_enable_open","fence_switch":"e_fence_in|e_fence_out|e_fence_in_out|e_fence_close","name":"287fee56-ee44-4925-aed6-5163cbb94015","ofence":{"circle":{"lat":1589498272,"lon":1589498272,"radius":1589498272},"city":{"name":"7d2f2b16-67dd-49e3-ae74-272fed8c28b8"},"polygon":{"poit":[{"lat":1589498272,"lon":1589498272},{"lat":1589498272,"lon":1589498272}]}},"sfid":"7a7f9737-6dc8-490d-9772-1800f1537fda","type":"e_type_circle|e_type_city|e_type_polygon"},{"enable":"e_enable_close|e_enable_open","fence_switch":"e_fence_in|e_fence_out|e_fence_in_out|e_fence_close","name":"a0eb101f-f311-4826-95fb-50adf5a8fc26","ofence":{"circle":{"lat":1589498272,"lon":1589498272,"radius":1589498272},"city":{"name":"d3f451ba-3b65-45bc-a712-7cfa7c9c4e67"},"polygon":{"poit":[{"lat":1589498272,"lon":1589498272}]}},"sfid":"869c9bb1-a486-4daf-befe-4a6f154d6050","type":"e_type_circle|e_type_city|e_type_polygon"}]
     * errcode : 0
     */

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * enable : e_enable_close|e_enable_open
         * fence_switch : e_fence_in|e_fence_out|e_fence_in_out|e_fence_close
         * name : 287fee56-ee44-4925-aed6-5163cbb94015
         * ofence : {"circle":{"lat":1589498272,"lon":1589498272,"radius":1589498272},"city":{"name":"7d2f2b16-67dd-49e3-ae74-272fed8c28b8"},"polygon":{"poit":[{"lat":1589498272,"lon":1589498272},{"lat":1589498272,"lon":1589498272}]}}
         * sfid : 7a7f9737-6dc8-490d-9772-1800f1537fda
         * type : e_type_circle|e_type_city|e_type_polygon
         */

        private String enable; // 是否启用,修改是可选填， e_enable_close 0-不启用  | e_enable_open 1-启用
        private String fence_switch; // 围栏报警,修改是可选填， e_fence_in 进围栏 | e_fence_out 出围栏 | e_fence_in_out 进出围栏 | e_fence_close 关闭
        private String name; // 围栏名称,修改是可选填
        private OfenceBean ofence; // 围栏信息,修改是可选填，根据具体的围栏类型，数据不同（type为e_type_circle，有半径，经度 ，维度；type为e_type_city有地级市名称 ；e_type_polygon有地址列表',）
        private String sfid; // 围栏id
        private String type; // 围栏类型,修改是可选填， e_type_circle 圆形围栏 | e_type_city 地市级围栏 | e_type_polygon 多边形围栏

        public String getEnable() {
            return enable;
        }

        public void setEnable(String enable) {
            this.enable = enable;
        }

        public String getFence_switch() {
            return fence_switch;
        }

        public void setFence_switch(String fence_switch) {
            this.fence_switch = fence_switch;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public OfenceBean getOfence() {
            return ofence;
        }

        public void setOfence(OfenceBean ofence) {
            this.ofence = ofence;
        }

        public String getSfid() {
            return sfid;
        }

        public void setSfid(String sfid) {
            this.sfid = sfid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static class OfenceBean {
            /**
             * circle : {"lat":1589498272,"lon":1589498272,"radius":1589498272}
             * city : {"name":"7d2f2b16-67dd-49e3-ae74-272fed8c28b8"}
             * polygon : {"poit":[{"lat":1589498272,"lon":1589498272},{"lat":1589498272,"lon":1589498272}]}
             */

            private CircleBean circle; // 当围栏为圆形时设置
            private CityBean city; // 当围栏为地级市形时设置
            private PolygonBean polygon; // 当围栏为多边形时设置

            public CircleBean getCircle() {
                return circle;
            }

            public void setCircle(CircleBean circle) {
                this.circle = circle;
            }

            public CityBean getCity() {
                return city;
            }

            public void setCity(CityBean city) {
                this.city = city;
            }

            public PolygonBean getPolygon() {
                return polygon;
            }

            public void setPolygon(PolygonBean polygon) {
                this.polygon = polygon;
            }

            public static class CircleBean {
                /**
                 * lat : 1589498272
                 * lon : 1589498272
                 * radius : 1589498272
                 */

                private int lat; // 圆心的维度，原值*1000000
                private int lon; // 圆心的经度，原值*1000000
                private int radius; // 半径，原值*1000000
                private String addr;//地址

                public int getLat() {
                    return lat;
                }

                public void setLat(int lat) {
                    this.lat = lat;
                }

                public int getLon() {
                    return lon;
                }

                public void setLon(int lon) {
                    this.lon = lon;
                }

                public int getRadius() {
                    return radius;
                }

                public void setRadius(int radius) {
                    this.radius = radius;
                }
                public String getAddr() {
                    return addr;
                }

                public void setAddr(String addr) {
                    this.addr = addr;
                }

            }

            public static class CityBean {
                /**
                 * name : 7d2f2b16-67dd-49e3-ae74-272fed8c28b8
                 */

                private String name; // 城市名称
                private String district; // 区名称

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDistrict() {
                    return district;
                }

                public void setDistrict(String district) {
                    this.district = district;
                }
            }

            public static class PolygonBean {
                private List<PoitBean> poit;

                public List<PoitBean> getPoit() {
                    return poit;
                }

                public void setPoit(List<PoitBean> poit) {
                    this.poit = poit;
                }

                public static class PoitBean {
                    /**
                     * lat : 1589498272
                     * lon : 1589498272
                     */

                    private int lat; // 维度，原值*1000000
                    private int lon; // 经度，原值*1000000

                    public int getLat() {
                        return lat;
                    }

                    public void setLat(int lat) {
                        this.lat = lat;
                    }

                    public int getLon() {
                        return lon;
                    }

                    public void setLon(int lon) {
                        this.lon = lon;
                    }
                }
            }
        }
    }
}
