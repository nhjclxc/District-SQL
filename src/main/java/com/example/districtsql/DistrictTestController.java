package com.example.districtsql;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author LuoXianchao
 * @since 2024/04/17 09:48
 */

public class DistrictTestController {
/*
        <dependency>
            <groupId>org.apache.httpcomponents.client5</groupId>
            <artifactId>httpclient5</artifactId>
            <version>5.1.2</version>
        </dependency>
 */

    public static void main(String[] args) throws Exception{
//      高德接口连接：  https://lbs.amap.com/api/webservice/guide/api/district

        // 高德应用的key
        String key = "";

        // 首先查询中华人民共和国下的所有省份 (其中key word是中国，由于是get请求需要编码)
//        String url = "http://restapi.amap.com/v3/config/district?key=" + key + "&keywords=北京&subdistrict=3&extensions=base";
        String url = "http://restapi.amap.com/v3/config/district?key=" + key + "&subdistrict=4&extensions=base"; // keywords不传代表获取全国数据
        //1、创建httpClient
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = client.execute(httpGet);
        //2、获取实体
        HttpEntity entity = response.getEntity();
        // 3.将实体装成字符串
        String string = EntityUtils.toString(entity);

        // 通过objectMapper解析
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(string, Map.class);
        //  获取中华人民共和国
        List<Map<String, Object>> fdistricts = (List<Map<String, Object>>) map.get("districts");
        //  获取所有的省集合，然后遍历获取市区街道
        List<Map<String, Object>> provinceDistrictsList =  (List<Map<String, Object>>) fdistricts.get(0).get("districts");

        List<SysDistrict> districtList = new ArrayList<>();
        getDistrictList(provinceDistrictsList, districtList, 0L);

        System.out.println(districtList.size());

//        int i = sysDistrictMapper.insertBatch(districtList);
//        System.out.println("插入：" + i);
    }

    public static Long initId = 1L;
    static LocalDateTime now = LocalDateTime.now();

    /** 用于广度优先搜索 */
    static Queue<List<SysDistrict>> districtQueue = new ArrayDeque<>();

    public static Integer getLevel(String level){
        /*
            country:国家
            province:省份（直辖市会在province显示）
            city:市（直辖市会在province显示）
            district:区县
            street:街道
        */
        /** 级别 1:省/自治区/直辖市 2:市级 3:县级 4:街道 */
        switch (level){
            case "province": return 1;
            case "city": return 2;
            case "district": return 3;
            case "street": return 4;
        }
        return 0;
    }

    private static String getCode(Object citycode) {
        String code = "";
        try {
            code = (String) citycode;
        }catch (Exception e){}
        return code;
    }
    /**
     * 将高德接口返回的树状结构转化为list便于插入数据库表
     *
     * @param treeStructure 高德返回的树状结构数据
     * @param districtList 最后要插入数据库的list
     * @param parentId 腹父级id
     */
    private static void getDistrictList(List<Map<String, Object>> treeStructure, List<SysDistrict> districtList, Long parentId) {
        for (Map<String, Object> districtMap : treeStructure) {
            SysDistrict district = new SysDistrict()
                    .setId(initId++).setPid(parentId)
                    .setCode((String)districtMap.get("adcode"))
                    .setName((String)districtMap.get("name"))
                    .setStatus(1).setRemark("")
                    .setLevel(getLevel((String)districtMap.get("level")))
                    .setCenter((String)districtMap.get("center"))
                    .setCityCode(getCode(districtMap.get("citycode")))
                    .setCreateTime(now).setUpdateTime(now);
            districtList.add(district);
            List<Map<String, Object>> subTreeStructure = (List<Map<String, Object>>)districtMap.get("districts");
            if (subTreeStructure != null && subTreeStructure.size() > 0){
                getDistrictList(subTreeStructure, districtList, district.getId());
            }
        }

    }

}
