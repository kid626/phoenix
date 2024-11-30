package com.bruce.phoenix.common.converter;

import com.bruce.phoenix.common.model.common.BasePageQuery;
import com.bruce.phoenix.common.model.common.PageData;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName mybatis-plus-maven-plugin
 * @Date 2023/11/30 19:53
 * @Author Bruce
 */
public class PageDataConverter {

    public static <T> PageData<T> convertFromPage(Page page, List<T> data) {
        PageData<T> pageData = convertBaseFromPage(page);
        pageData.setData(data);
        return pageData;
    }

    public static <T> PageData<T> convertFromPage(Page<T> page) {
        PageData<T> pageData = convertBaseFromPage(page);
        pageData.setData(page.getResult());
        return pageData;
    }

    /**
     * 手动分页
     */
    public static <T> PageData<T> convertFromPage(Integer page, Integer pageSize, List<T> result) {
        PageData<T> pageData = convertBaseFromPage(page, pageSize, result);
        result = result.stream().skip((long) pageSize * (page - 1)).limit(pageSize).collect(Collectors.toList());
        pageData.setData(result);
        return pageData;
    }

    /**
     * 手动分页
     */
    public static <T> PageData<T> convertFromPage(BasePageQuery query, List<T> result, Long total) {
        PageData<T> pageData = convertBaseFromPage(query, total);
        pageData.setData(result);
        return pageData;
    }

    public static <T> PageData<T> convertFromPageList(Page page, List<T> list) {
        PageData<T> pageData = convertBaseFromPage(page);
        pageData.setList(list);
        return pageData;
    }

    public static <T> PageData<T> convertFromPageList(Page<T> page) {
        PageData<T> pageData = convertBaseFromPage(page);
        pageData.setList(page.getResult());
        return pageData;
    }

    /**
     * 手动分页
     */
    public static <T> PageData<T> convertFromPageList(Integer page, Integer pageSize, List<T> result) {
        PageData<T> pageData = convertBaseFromPage(page, pageSize, result);
        result = result.stream().skip((long) pageSize * (page - 1)).limit(pageSize).collect(Collectors.toList());
        pageData.setList(result);
        return pageData;
    }

    /**
     * 手动分页
     */
    public static <T> PageData<T> convertFromPageList(BasePageQuery query, List<T> result, Long total) {
        PageData<T> pageData = convertBaseFromPage(query, total);
        pageData.setList(result);
        return pageData;
    }

    private static <T> PageData<T> convertBaseFromPage(Integer page, Integer pageSize, List<T> result) {
        PageData<T> pageData = new PageData<>();
        pageData.setPageNum(page);
        pageData.setPageSize(pageSize);
        pageData.setTotal(result.size());
        pageData.setStartRow((long) pageSize * (page - 1));
        pageData.setEndRow((long) pageSize * page);
        pageData.setPages((result.size() + pageSize - 1) / pageSize);
        return pageData;
    }

    private static <T> PageData<T> convertBaseFromPage(Page<T> page) {
        PageData<T> pageData = new PageData<>();
        pageData.setStartRow(page.getStartRow());
        pageData.setEndRow(page.getEndRow());
        pageData.setPageNum(page.getPageNum());
        pageData.setPageSize(page.getPageSize());
        pageData.setPages(page.getPages());
        pageData.setTotal(page.getTotal());
        return pageData;
    }

    private static <T> PageData<T> convertBaseFromPage(BasePageQuery query, Long total) {
        PageData<T> pageData = new PageData();
        pageData.setPageNum(query.getPageNum());
        pageData.setPageSize(query.getPageSize());
        pageData.setTotal(total);
        pageData.setStartRow((long) query.getPageSize() * (long) (query.getPageNum() - 1));
        pageData.setEndRow((long) query.getPageSize() * (long) query.getPageNum());
        pageData.setPages((long) ((total + query.getPageSize() - 1) / query.getPageSize()));
        return pageData;
    }

}
