package com.moji.servicemonitor.entity;

import lombok.*;

/**
 * 入口实体类
 *
 * @date 2020/8/26
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entrance {
    /**
     * 主键
     */
    private Integer id ;
    /**
     * 入口名称
     */
    private String name;
    /**
     * 开始时间
     */
    private Long startTime;
    /**
     * 结束时间
     */
    private Long endTime;
}
