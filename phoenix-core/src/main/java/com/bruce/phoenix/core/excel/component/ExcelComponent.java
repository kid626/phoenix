package com.bruce.phoenix.core.excel.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.fastjson.JSON;
import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.core.component.middleware.RedisComponent;
import com.bruce.phoenix.core.excel.listener.AbstractAnalysisEventListener;
import com.bruce.phoenix.core.excel.model.BaseImportModel;
import com.bruce.phoenix.core.excel.model.ImportResultModel;
import com.bruce.phoenix.core.excel.service.IExcelService;
import com.bruce.phoenix.core.util.EasyExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/4 15:14
 * @Author Bruce
 */
@Component
@Slf4j
public class ExcelComponent {

    @Resource
    private HttpServletResponse response;
    @Resource
    private RedisComponent redisComponent;

    public static final String EXCEL_IMPORT_CACHE = "cache:excel:import:{0}";

    /**
     * 加载模板
     *
     * @param filepath 文件路径
     * @param filename 文件名,建议不要带目录
     */
    public void loadTemplate(String filename, String filepath) {
        InputStream in = null;
        ServletOutputStream out = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource(filepath);
            in = classPathResource.getInputStream();
            out = response.getOutputStream();
            // 设置输出的格式
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 循环取出流中的数据
            byte[] b = new byte[1024];
            int count;
            while ((count = in.read(b)) > 0) {
                out.write(b, 0, count);
            }
        } catch (Exception e) {
            log.warn("生成 excel 模板失败:{}", e.getMessage(), e);
            throw new CommonException("生成 excel 模板失败!");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                // 一般不会到这里
                log.warn("生成 excel 模板失败:{}", e.getMessage(), e);
            }
        }
    }

    public <T extends BaseImportModel> ImportResultModel<T> importData(MultipartFile file, Class<T> pojoClass, AbstractAnalysisEventListener<T> listener, IExcelService<T> iExcelService, Integer headRowNumber) throws IOException {
        // step 1 从 excel 中读取
        EasyExcelUtil.simpleRead(file, pojoClass, listener, headRowNumber, 0);
        // step 2 获取所有数据    TODO 边获取边处理
        List<T> allDataList = listener.getAllDataList();
        // step 3 处理数据
        allDataList = iExcelService.proceed(allDataList);
        // step 4 填充错误数据,并返回
        return set(allDataList);
    }

    public <T extends BaseImportModel> ImportResultModel<T> importData(MultipartFile file, Class<T> pojoClass, AbstractAnalysisEventListener<T> listener, Integer headRowNumber) throws IOException {
        // step 1 从 excel 中读取
        EasyExcelUtil.simpleRead(file, pojoClass, listener, headRowNumber, 0);
        // step 2 获取出错的数据
        List<T> failureDataList = listener.getFailureDataList();
        // step 3 填充错误数据,并返回
        return set(failureDataList);
    }

    /**
     * 填充错误数据
     *
     * @param filepath 文件路径
     * @param filename 文件名,建议不要带目录
     */
    public void downloadErrorData(String filename, String filepath, String operationId) {
        // 获取错误数据
        ImportResultModel<BaseImportModel> model = get(operationId);
        // 填充错误数据
        fillTemplate(filename, filepath, model.getErrorDataList());
    }

    /**
     * 填充错误数据
     *
     * @param filepath 文件路径
     * @param filename 文件名,建议不要带目录
     */
    private <T extends BaseImportModel> void fillTemplate(String filename, String filepath, List<T> data) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(filepath);
            String templatePath = classPathResource.getFile().getPath();
            // 设置输出的格式
            response.reset();
            // 设置响应类型
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 设置编码格式
            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            EasyExcelUtil.simpleFill(response, templatePath, null, data, WriteDirectionEnum.VERTICAL);
        } catch (IOException e) {
            log.warn("填充 excel 模板失败:{}", e.getMessage(), e);
            throw new CommonException("填充 excel 模板失败!");
        }
    }

    private <T extends BaseImportModel> ImportResultModel<T> set(List<T> errorList) {
        String operationId = UUID.fastUUID().toString(true);
        ImportResultModel<T> model = new ImportResultModel<>();
        model.setOperationId(operationId);
        if (CollUtil.isNotEmpty(errorList)) {
            model.setErrorCount((long) errorList.size());
            model.setErrorDataList(errorList);
        } else {
            model.setErrorCount(0L);
            model.setErrorDataList(new ArrayList<>());
        }
        String key = MessageFormat.format(EXCEL_IMPORT_CACHE, operationId);
        redisComponent.setExpire(key, JSON.toJSONString(model), 1L, TimeUnit.DAYS);
        return model;
    }

    private <T extends BaseImportModel> ImportResultModel<T> get(String operationId) {
        String key = MessageFormat.format(EXCEL_IMPORT_CACHE, operationId);
        String result = redisComponent.get(key);
        return JSONUtil.toBean(result, new TypeReference<ImportResultModel<T>>() {}, false);
    }

}
