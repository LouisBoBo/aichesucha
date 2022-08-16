package com.car.scth.mvp.model.putbean;

import java.util.List;

/**
 * 围栏修改
 */
public class FenceModifyPutBean {

    /**
     * module : fence
     * func : Modify
     * params : {"item":{"type":"e_type_circle|e_type_city|e_type_polygon","sfid":"91f273d5-cadc-433c-b1d9-cd73ccbe04e9","ofence":{"polygon":{"poit":[{"lon":986202686,"lat":986202686},{"lon":986202686,"lat":986202686},{"lon":986202686,"lat":986202686}]},"city":{"name":"f4573eda-10a0-4766-b770-80190e6d1a4b"},"circle":{"radius":986202686,"lon":986202686,"lat":986202686}},"name":"0bdd6465-a4e2-4226-a665-4b8e632f5f9e","fence_switch":"e_fence_in|e_fence_out|e_fence_in_out|e_fence_close","enable":"e_enable_close|e_enable_open"}}
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
         * item : {"type":"e_type_circle|e_type_city|e_type_polygon","sfid":"91f273d5-cadc-433c-b1d9-cd73ccbe04e9","ofence":{"polygon":{"poit":[{"lon":986202686,"lat":986202686},{"lon":986202686,"lat":986202686},{"lon":986202686,"lat":986202686}]},"city":{"name":"f4573eda-10a0-4766-b770-80190e6d1a4b"},"circle":{"radius":986202686,"lon":986202686,"lat":986202686}},"name":"0bdd6465-a4e2-4226-a665-4b8e632f5f9e","fence_switch":"e_fence_in|e_fence_out|e_fence_in_out|e_fence_close","enable":"e_enable_close|e_enable_open"}
         */

        private ItemBean item;

        public ItemBean getItem() {
            return item;
        }

        public void setItem(ItemBean item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * type : e_type_circle|e_type_city|e_type_polygon
             * sfid : 91f273d5-cadc-433c-b1d9-cd73ccbe04e9
             * ofence : {"polygon":{"poit":[{"lon":986202686,"lat":986202686},{"lon":986202686,"lat":986202686},{"lon":986202686,"lat":986202686}]},"city":{"name":"f4573eda-10a0-4766-b770-80190e6d1a4b"},"circle":{"radius":986202686,"lon":986202686,"lat":986202686}}
             * name : 0bdd6465-a4e2-4226-a665-4b8e632f5f9e
             * fence_switch : e_fence_in|e_fence_out|e_fence_in_out|e_fence_close
             * enable : e_enable_close|e_enable_open
             */

            private String type; // 围栏类型,修改是可选填
            private String sfid; // 围栏id
            private OfenceBean ofence;
            private String name; // 围栏名称,修改是可选填
            private String fence_switch; // 围栏报警,修改是可选填
            private String enable; // 是否启用,修改是可选填

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSfid() {
                return sfid;
            }

            public void setSfid(String sfid) {
                this.sfid = sfid;
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

            public String getEnable() {
                return enable;
            }

            public void setEnable(String enable) {
                this.enable = enable;
            }

            public static class OfenceBean {
                /**
                 * polygon : {"poit":[{"lon":986202686,"lat":986202686},{"lon":986202686,"lat":986202686},{"lon":986202686,"lat":986202686}]}
                 * city : {"name":"f4573eda-10a0-4766-b770-80190e6d1a4b"}
                 * circle : {"radius":986202686,"lon":986202686,"lat":986202686}
                 */

                private PolygonBean polygon;
                private CityBean city;
                private CircleBean circle;

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
                         * lon : 986202686
                         * lat : 986202686
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
                     * name : f4573eda-10a0-4766-b770-80190e6d1a4b
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
                     * radius : 986202686
                     * lon : 986202686
                     * lat : 986202686
                     */

                    private int radius; // 半径，原值*1000000
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
}
