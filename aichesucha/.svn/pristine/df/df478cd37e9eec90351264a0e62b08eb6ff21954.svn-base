package com.car.scth.mvp.model.putbean;

import java.util.List;

/**
 * 添加围栏
 */
public class FenceAddPutBean {

    /**
     * module : fence
     * func : Add
     * params : {"type":"e_type_circle|e_type_city|e_type_polygon","simei":"21d54ad4-2ba2-49f1-b73f-12ed4ea3583a","sgid":"2795359b-14ff-4d6a-b301-b3b2b6be7f18","ofence":{"polygon":{"poit":[{"lon":1129372316,"lat":1129372316},{"lon":1129372316,"lat":1129372316}]},"city":{"name":"f6ef0c3c-ef67-41c9-a7de-cac0c20e94fc"},"circle":{"radius":1129372316,"lon":1129372316,"lat":1129372316}},"name":"80fc5fff-4990-4c92-aff3-bd3948b01e96","fence_switch":"e_fence_in|e_fence_out|e_fence_in_out|e_fence_close"}
     */

    private String module;
    private String func;
    private ParamsBean params;

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
        /**
         * type : e_type_circle|e_type_city|e_type_polygon
         * simei : 21d54ad4-2ba2-49f1-b73f-12ed4ea3583a
         * sgid : 2795359b-14ff-4d6a-b301-b3b2b6be7f18
         * ofence : {"polygon":{"poit":[{"lon":1129372316,"lat":1129372316},{"lon":1129372316,"lat":1129372316}]},"city":{"name":"f6ef0c3c-ef67-41c9-a7de-cac0c20e94fc"},"circle":{"radius":1129372316,"lon":1129372316,"lat":1129372316}}
         * name : 80fc5fff-4990-4c92-aff3-bd3948b01e96
         * fence_switch : e_fence_in|e_fence_out|e_fence_in_out|e_fence_close
         */

        private String type; // 围栏类型
        private String sgid; // 设置一个车组里所有的设备围栏，账号登入可选填，设备登入无效
        private OfenceBean ofence; // 围栏的具体信息
        private List<String> simei; // 设备号,如果设备号登入或者设置sgid，可以不用填写
        private String name; // 围栏名称
        private String fence_switch; // 围栏报警类型

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<String> getSimei() {
            return simei;
        }

        public void setSimei(List<String> simei) {
            this.simei = simei;
        }

        public String getSgid() {
            return sgid;
        }

        public void setSgid(String sgid) {
            this.sgid = sgid;
        }

        public OfenceBean getOfence() {
            return ofence;
        }

        public void setOfence(OfenceBean ofence) {
            this.ofence = ofence;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFence_switch() {
            return fence_switch;
        }

        public void setFence_switch(String fence_switch) {
            this.fence_switch = fence_switch;
        }

        public static class OfenceBean {
            /**
             * polygon : {"poit":[{"lon":1129372316,"lat":1129372316},{"lon":1129372316,"lat":1129372316}]}
             * city : {"name":"f6ef0c3c-ef67-41c9-a7de-cac0c20e94fc"}
             * circle : {"radius":1129372316,"lon":1129372316,"lat":1129372316}
             */

            private PolygonBean polygon; // 多边形围栏
            private CityBean city; // 地级市围栏
            private CircleBean circle; // 圆形围栏

            public PolygonBean getPolygon() {
                return polygon;
            }

            public void setPolygon(PolygonBean polygon) {
                this.polygon = polygon;
            }

            public CityBean getCity() {
                return city;
            }

            public void setCity(CityBean city) {
                this.city = city;
            }

            public CircleBean getCircle() {
                return circle;
            }

            public void setCircle(CircleBean circle) {
                this.circle = circle;
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
                     * lon : 1129372316
                     * lat : 1129372316
                     */

                    private long lon; // 经度，原值*1000000
                    private long lat; // 纬度，原值*1000000

                    public long getLon() {
                        return lon;
                    }

                    public void setLon(long lon) {
                        this.lon = lon;
                    }

                    public long getLat() {
                        return lat;
                    }

                    public void setLat(long lat) {
                        this.lat = lat;
                    }
                }
            }

            public static class CityBean {
                /**
                 * name : f6ef0c3c-ef67-41c9-a7de-cac0c20e94fc
                 */

                private String name; // 城市名称
                private String district; // 区名字

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

            public static class CircleBean {
                /**
                 * radius : 1129372316
                 * lon : 1129372316
                 * lat : 1129372316
                 */

                private int radius; // 圆形半径
                private long lon; // 圆心的经度，原值*1000000
                private long lat; // 圆心的纬度，原值*1000000

                public int getRadius() {
                    return radius;
                }

                public void setRadius(int radius) {
                    this.radius = radius;
                }

                public long getLon() {
                    return lon;
                }

                public void setLon(long lon) {
                    this.lon = lon;
                }

                public long getLat() {
                    return lat;
                }

                public void setLat(long lat) {
                    this.lat = lat;
                }
            }
        }
    }
}
