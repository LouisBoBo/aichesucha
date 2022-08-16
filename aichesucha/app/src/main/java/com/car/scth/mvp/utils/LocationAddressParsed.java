package com.car.scth.mvp.utils;


import android.text.TextUtils;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.car.scth.R;
import com.car.scth.app.MyApplication;

/**
 * 高德获取地址 单例模式
 */
public class LocationAddressParsed {
    private static volatile LocationAddressParsed locationParsed;
    // 高德地图通过经纬度获取地址信息
    private RegeocodeQuery query;
    private GeocodeSearch geocoderSearch; // 地理编码，通过经纬度获取地址信息
    private double lat, lon;
    private getAddressListener addressListener;
    // 百度SDK解析地址
    private GeoCoder baiduCoder;

    private LocationAddressParsed() {
        geocoderSearch = new GeocodeSearch(MyApplication.getMyApp());
        geocoderSearch.setOnGeocodeSearchListener(aMaplistener);

        baiduCoder = GeoCoder.newInstance();
        baiduCoder.setOnGetGeoCodeResultListener(baiduListener);
    }

    public static LocationAddressParsed getLocationParsedInstance() {
        if (locationParsed == null) {
            synchronized (LocationAddressParsed.class) {
                if (locationParsed == null) {
                    locationParsed = new LocationAddressParsed();
                }
            }
        }
        return locationParsed;
    }

    public interface getAddressListener {
        //返回地址
        void getAddress(String str);
    }

    public void setAddressListener(getAddressListener listener) {
        this.addressListener = listener;
    }

    public LocationAddressParsed Parsed(double Latitude, double longitude) {
        lat = Latitude;
        lon = longitude;
        if (GpsUtils.isChinaTaiWan(lat, lon)) {//台湾用百度解析
            getAddressForLocation(lat, lon);
        } else {
            LatLonPoint latLonPoint = new LatLonPoint(lat, lon);
            // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            query = new RegeocodeQuery(latLonPoint, 500, GeocodeSearch.AMAP);
            query.setExtensions("all");
            geocoderSearch.getFromLocationAsyn(query);
        }
        return this;
    }

    /**
     * 高德地图-地理编码检索监听器，逆地理编码，通过经纬度获取地址信息
     */
    private GeocodeSearch.OnGeocodeSearchListener aMaplistener = new GeocodeSearch.OnGeocodeSearchListener() {
        @Override
        public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                    && !TextUtils.isEmpty(regeocodeResult.getRegeocodeAddress().getFormatAddress())) {
                String address = regeocodeResult.getRegeocodeAddress().getFormatAddress().replace("(", "");
                address = address.replace(")", "");
                address = address.replace("null", "");
                // 判断是否获取到了方向和距离
                if (regeocodeResult.getRegeocodeAddress().getRoads() != null
                        && regeocodeResult.getRegeocodeAddress().getRoads().size() > 0) {
                    RegeocodeRoad road = regeocodeResult.getRegeocodeAddress().getRoads().get(0);
                    int distance = (int) road.getDistance();
                    address = address + "，" + road.getName() + road.getDirection() + distance
                            + MyApplication.getMyApp().getString(R.string.txt_meter);
                } else {
                    if (regeocodeResult.getRegeocodeAddress().getPois().size() > 0) {
                        PoiItem poiItem = regeocodeResult.getRegeocodeAddress().getPois().get(0);
                        if (!TextUtils.isEmpty(poiItem.getSnippet())) {
                            address = address + "，" + poiItem.getSnippet();
                        } else {
                            address = address + poiItem.getDirection() + poiItem.getDistance() + MyApplication.getMyApp().getString(R.string.txt_meter);
                        }
                    }
                }
                addressListener.getAddress(address);
            } else {
                getAddressForLocation(lat, lon);
            }
        }

        @Override
        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        }
    };

    /**
     * 百度API通过经纬度获取详细地址信息
     *
     * @return
     */
    private void getAddressForLocation(double lat, double lon) {
        baiduCoder.reverseGeoCode(new ReverseGeoCodeOption()
                .location(new com.baidu.mapapi.model.LatLng(lat, lon))
                // 设置是否返回新数据 默认值0不返回，1返回
                .newVersion(1)
                // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                .radius(500));
    }

    private OnGetGeoCoderResultListener baiduListener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
                addressListener.getAddress("");
            } else {
                //详细地址
                String address = reverseGeoCodeResult.getAddress() + reverseGeoCodeResult.getSematicDescription();
                addressListener.getAddress(address);
            }
        }
    };
}
