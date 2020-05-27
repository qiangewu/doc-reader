package com.ls.entity.echarts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Echarts中对照组的信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeriesItem {

    String itemName;

    String color;

    double[] data;

}
