package com.ls.entity.echarts;

import lombok.Data;

import java.util.List;

/**
 * Echarts中对照组的信息
 */
@Data
public class SeriesItem {

    String itemName;

    String color;

    int[] data;

}
