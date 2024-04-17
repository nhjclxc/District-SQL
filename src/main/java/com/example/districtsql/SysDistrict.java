package com.example.districtsql;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class SysDistrict {

/*

DROP TABLE IF EXISTS `sys_district`;
CREATE TABLE `sys_district`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '地区id',
    `pid`         bigint       DEFAULT NULL COMMENT '父级地区id',
    `code`        varchar(255) DEFAULT NULL COMMENT '区划编码',
    `name`        varchar(255) DEFAULT NULL COMMENT '区划名称',
    `level`       tinyint(1)   DEFAULT NULL COMMENT '级别 1:省/自治区/直辖市 2:市级 3:县级 4:街道',
    `center`      varchar(100) DEFAULT NULL COMMENT '经纬度',
    `city_code`   varchar(100) DEFAULT NULL COMMENT '城市编码（无用）',
    `status`      tinyint(1)   DEFAULT NULL COMMENT '状态 0 删除 -1 再用',
    `remark`      varchar(255) DEFAULT NULL COMMENT '备注',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='区域表';

 */

    private List<SysDistrict> children;
    /** 项目id */
    private Long id;
    /** 父级地区id */
    private Long pid;
    /** 区划编码 */
    private String code;
    /** 区划名称 */
    private String name;
    /** 备注 */
    private String remark;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 更新时间 */
    private LocalDateTime updateTime;
    /** 状态 0 删除 -1 再用 */
    private Integer status;
    /** 级别 1:省/自治区/直辖市 2:市级 3:县级 4:街道 */
    private Integer level;
    /** 经纬度 */
    private String center;
    /** 城市编码 */
    private String cityCode;


}

