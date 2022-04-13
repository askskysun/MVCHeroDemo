package com.hero.mvcdemo.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import java.util.List;

/**
 * 获取定位
 */

public class LocationUtils {
    private static final String TAG = LocationUtils.class.getSimpleName();

    public static String getLocation(Context context) {
        try {
            //获取定位服务
            LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            //获取当前可用的位置控制器
            List<String> list = locationManager.getProviders(true);
            String providerNet = null;
            String providerGPS = null;
            if (list.contains(LocationManager.NETWORK_PROVIDER)) {
                //是否为网络位置控制器
                providerNet = LocationManager.NETWORK_PROVIDER;
            }
            if (list.contains(LocationManager.GPS_PROVIDER)) {
                //是否为GPS位置控制器
                providerGPS = LocationManager.GPS_PROVIDER;
            }
            if (providerGPS != null) {
                @SuppressLint("MissingPermission") Location locationGPS = locationManager.getLastKnownLocation(providerGPS);
                if (locationGPS != null) {
                    //获取当前位置，这里只用到了经纬度
                    return StringUtils.explicitUseStringBuider("纬度为：", locationGPS.getLatitude() + "", ",经度为：", locationGPS.getLongitude() + "");
                }
            }

            if (providerNet != null) {
                @SuppressLint("MissingPermission") Location locationNet = locationManager.getLastKnownLocation(providerNet);
                if (locationNet != null) {
                    //获取当前位置，这里只用到了经纬度
                    return StringUtils.explicitUseStringBuider("纬度为：", locationNet.getLatitude() + "", ",经度为：", locationNet.getLongitude() + "");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 更新location
     *
     * @param location
     * @return cityName
     */
    public static String updateWithNewLocation(Context context,Location location) {
        String mcityName = "";
        double lat = 0;
        double lng = 0;
        List<Address> addList = null;
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
        } else {
//            System.out.println("无法获取地理信息");
        }
        try {
            Geocoder geocoder = new Geocoder(context);
            addList = geocoder.getFromLocation(lat, lng, 1);    //解析经纬度
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (addList != null && addList.size() > 0) {
            for (int i = 0; i < addList.size(); i++) {
                Address add = addList.get(i);
                mcityName += add.getLocality();
            }
        }
        if (mcityName.length() != 0) {
            return mcityName.substring(0, (mcityName.length() - 1));
        } else {
            return mcityName;
        }
    }

    public static String getCityLocation(Context context) {
        String mcityName = "";
        double lat = 0;
        double lng = 0;
        List<Address> addList = null;
        Location location = null;
        try {
            //获取定位服务
            LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            //获取当前可用的位置控制器
            List<String> list = locationManager.getProviders(true);
            String providerNet = null;
            String providerGPS = null;
            if (list.contains(LocationManager.NETWORK_PROVIDER)) {
                //是否为网络位置控制器
                providerNet = LocationManager.NETWORK_PROVIDER;
            }
            if (list.contains(LocationManager.GPS_PROVIDER)) {
                //是否为GPS位置控制器
                providerGPS = LocationManager.GPS_PROVIDER;
            }

            if (providerNet != null) {
                @SuppressLint("MissingPermission") Location locationNet = locationManager.getLastKnownLocation(providerNet);
                if (locationNet != null) {
                    location = locationNet;
                }
            }
            if (providerGPS != null) {
                @SuppressLint("MissingPermission") Location locationGPS = locationManager.getLastKnownLocation(providerGPS);
                if (locationGPS != null) {
                    location = locationGPS;
                }
            }

            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
            } else {
//            System.out.println("无法获取地理信息");
            }
            try {
                Geocoder geocoder = new Geocoder(context);
                addList = geocoder.getFromLocation(lat, lng, 1);    //解析经纬度
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (addList != null && addList.size() > 0) {
                for (int i = 0; i < addList.size(); i++) {
                    Address add = addList.get(i);
                    mcityName += add.getLocality();
                }
            }
            if (mcityName.length() != 0) {
                return mcityName.substring(0, (mcityName.length() - 1));
            } else {
                return mcityName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取城市和经纬度
     * @return
     */
    public static String getCityAndLocation(Context context) {
        String mcityName = "";
        double lat = 0;
        double lng = 0;
        List<Address> addList = null;
        Location location = null;
        try {
            //获取定位服务
            LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            //获取当前可用的位置控制器
            List<String> list = locationManager.getProviders(true);
            String providerNet = null;
            String providerGPS = null;
            if (list.contains(LocationManager.NETWORK_PROVIDER)) {
                //是否为网络位置控制器
                providerNet = LocationManager.NETWORK_PROVIDER;
            }
            if (list.contains(LocationManager.GPS_PROVIDER)) {
                //是否为GPS位置控制器
                providerGPS = LocationManager.GPS_PROVIDER;
            }

            if (providerNet != null) {
                @SuppressLint("MissingPermission") Location locationNet = locationManager.getLastKnownLocation(providerNet);
                if (locationNet != null) {
                    location = locationNet;
                }
            }
            if (providerGPS != null) {
                @SuppressLint("MissingPermission") Location locationGPS = locationManager.getLastKnownLocation(providerGPS);
                if (locationGPS != null) {
                    location = locationGPS;
                }
            }

            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
            } else {
//            System.out.println("无法获取地理信息");
            }
            try {
                Geocoder geocoder = new Geocoder(context);
                addList = geocoder.getFromLocation(lat, lng, 1);    //解析经纬度
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (addList != null && addList.size() > 0) {
                for (int i = 0; i < addList.size(); i++) {
                    Address add = addList.get(i);
                    mcityName += add.getLocality();
                }
            }
            if (mcityName.length() != 0) {
                return StringUtils.explicitUseStringBuider("城市： ",mcityName.substring(0, (mcityName.length() - 1)) ," 纬度为：", location.getLatitude() + "", ",经度为：", location.getLongitude() + "");
            } else {
                return StringUtils.explicitUseStringBuider("城市： ",mcityName ," 纬度为：", location.getLatitude() + "", ",经度为：", location.getLongitude() + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
