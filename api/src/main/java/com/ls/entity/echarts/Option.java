package com.ls.entity.echarts;

import lombok.Data;

import java.util.List;

@Data
public class Option {
    /**
     * 横坐标刻度
     */
    String[] xRange;

    /**
     * 单位
     */
    String unit;

    /**
     * 对照组信息
     */
    List<SeriesItem> seriesItemList;
}
