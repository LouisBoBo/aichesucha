package com.car.scth.mvp.mapapi;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import com.car.scth.mvp.model.bean.BaiduMapAddressBean;

/**
 * 地图服务类
 */
public interface MapService {

    /**
     * 百度地图通过经纬度获取地址信息
     * @param ak
     * @param output json
     * @param coordtype wgs84ll
     * @param pois 1
     * @param mcode 包名
     * @param location 经纬度坐标，纬度在前，经度在后
     * @param radius poi召回半径
     * @return
     */
    @GET("/geocoder/v2/")
    Observable<BaiduMapAddressBean> getBaiduMapAddress(@Query("ak") String ak, @Query("output") String output,
                                                       @Query("coordtype") String coordtype, @Query("pois") int pois,
                                                       @Query("mcode") String mcode, @Query("location") String location,
                                                       @Query("radius") int radius);

    @GET
    Observable<BaiduMapAddressBean> getBaiduMapAddressTwo(@Url String url);

}
