package com.bruce.phoenix.common.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file-manager-system
 * @Date 2023/4/7 15:12
 * @Author fzh
 */
@Data
public class PageData<T> implements Serializable {

    @ApiModelProperty("页数")
    private long pageNum;
    @ApiModelProperty("每页条数")
    private long pageSize;
    @ApiModelProperty("开始")
    private long startRow;
    @ApiModelProperty("结束")
    private long endRow;
    @ApiModelProperty("总页数")
    private long pages;
    @ApiModelProperty("总条数")
    private long total;
    @ApiModelProperty("封装的数据")
    private List<T> data;
    @ApiModelProperty("封装的数据(兼容两种)")
    private List<T> list;


}
