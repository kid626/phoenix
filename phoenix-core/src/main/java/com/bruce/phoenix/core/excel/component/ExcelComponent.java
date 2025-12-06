package com.bruce.phoenix.core.excel.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.common.model.constants.DigitalConstant;
import com.bruce.phoenix.core.component.middleware.RedisComponent;
import com.bruce.phoenix.core.excel.listener.ParallelAnalysisEventListener;
import com.bruce.phoenix.core.excel.listener.SimpleAnalysisEventListener;
import com.bruce.phoenix.core.excel.model.BaseImportModel;
import com.bruce.phoenix.core.excel.model.ImportResultModel;
import com.bruce.phoenix.core.util.EasyExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

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
    public static final Integer EXPORT_MAX_NUM = DigitalConstant.W;
    public static final String EXPORT_MAX_MESSAGE = "您好，只支持导出最近" + EXPORT_MAX_NUM + "条数据，请调整查询条件再导出！";

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

    /**
     * 导入数据 结果统一处理
     *
     * @param file          文件
     * @param pojoClass     每一行对应实体类 继承 {@link BaseImportModel}
     * @param listener      监听器,继承 {@link SimpleAnalysisEventListener}把数据加载到 allDataList 里,然后统一处理
     * @param headRowNumber 头部行数
     * @param <T>           泛型,对应 pojoClass
     * @return 导入结果
     * @throws IOException 文件读取异常
     */
    public <T extends BaseImportModel> ImportResultModel<T> importData(MultipartFile file, Class<T> pojoClass, SimpleAnalysisEventListener<T> listener, Integer headRowNumber) throws IOException {
        // step 1 从 excel 中读取
        EasyExcelUtil.simpleRead(file, pojoClass, listener, headRowNumber, 0);
        // step 2 获取出错的数据
        List<T> failureDataList = listener.getFailureDataList();
        // step 3 填充错误数据,并返回
        return set(failureDataList);
    }

    /**
     * 导入数据 边读取,边处理
     *
     * @param file          文件
     * @param pojoClass     每一行对应实体类 继承 {@link BaseImportModel}
     * @param listener      监听器,继承 {@link ParallelAnalysisEventListener}边读取边处理
     * @param headRowNumber 头部行数
     * @param <T>           泛型,对应 pojoClass
     * @return 导入结果
     * @throws IOException 文件读取异常
     */
    public <T extends BaseImportModel> ImportResultModel<T> importData(MultipartFile file, Class<T> pojoClass, ParallelAnalysisEventListener<T> listener, Integer headRowNumber) throws IOException {
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
    public <T extends BaseImportModel> void downloadErrorData(String filename, String filepath, String operationId) {
        // 获取错误数据
        ImportResultModel<T> model = get(operationId);
        // 填充错误数据
        fillTemplate(filename, filepath, model.getErrorDataList());
    }

    /**
     * 导出
     *
     * @param filename 文件名,建议不要带目录
     * @param filepath 模版文件路径
     * @param data     查询出来的数据
     * @param <T>      数据类型
     */
    public <T> void export(String filename, String filepath, List<T> data) {
        export(filename, filepath, data, EXPORT_MAX_NUM, EXPORT_MAX_MESSAGE);
    }

    /**
     * 导出 V2 自定义输出流
     *
     * @param filename 文件名,建议不要带目录
     * @param filepath 模版文件路径
     * @param data     查询出来的数据
     * @param <T>      数据类型
     */
    public <T> void exportV2(String filename, String filepath, List<T> data) {
        export(filename, filepath, data, (bos, inFileName) -> {
            InputStream inputStream = null;
            ServletOutputStream out = null;
            try {
                inputStream = new ByteArrayInputStream(bos.toByteArray());
                out = response.getOutputStream();
                // 设置响应类型
                // response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                // 设置编码格式
                response.setCharacterEncoding("utf-8");
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

                byte buff[] = new byte[1024];
                int length = 0;

                while ((length = inputStream.read(buff)) > 0) {
                    out.write(buff, 0, length);
                }

            } catch (Exception e) {
                log.warn("导出失败:{}", e.getMessage());
            } finally {
                try {
                    inputStream.close();
                    out.close();
                    out.flush();
                } catch (IOException e) {
                    log.warn("导出失败:{}", e.getMessage());
                }
            }
        });
    }

    /**
     * 导出 V2 自定义输出流
     *
     * @param filename     文件名,建议不要带目录
     * @param filepath     模版文件路径
     * @param data         查询出来的数据
     * @param <T>          数据类型
     * @param mergeRegions 合并单元格
     */
    public <T> void exportWithMerge(String filename, String filepath, List<T> data, int[][] mergeRegions) {
        exportWithMerge(filename, filepath, data, (bos, inFileName) -> {
            InputStream inputStream = null;
            ServletOutputStream out = null;
            try {
                inputStream = new ByteArrayInputStream(bos.toByteArray());
                out = response.getOutputStream();
                // 设置响应类型
                // response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                // 设置编码格式
                response.setCharacterEncoding("utf-8");
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

                byte buff[] = new byte[1024];
                int length = 0;

                while ((length = inputStream.read(buff)) > 0) {
                    out.write(buff, 0, length);
                }

            } catch (Exception e) {
                log.warn("导出失败:{}", e.getMessage());
            } finally {
                try {
                    inputStream.close();
                    out.close();
                    out.flush();
                } catch (IOException e) {
                    log.warn("导出失败:{}", e.getMessage());
                }
            }
        }, mergeRegions);
    }

    /**
     * 导出
     *
     * @param filename 文件名,建议不要带目录
     * @param filepath 模版文件路径
     * @param data     查询出来的数据
     * @param consumer 自定义处理数据  写入到 response 或者写入到文件里
     * @param <T>      数据类型
     */
    public <T> void export(String filename, String filepath, List<T> data, BiConsumer<ByteArrayOutputStream, String> consumer) {
        ByteArrayOutputStream outputStream = getByteOutPutStream(filepath, data);
        try {
            consumer.accept(outputStream, filename);
        } catch (Exception e) {
            log.warn("导出文件失败");
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                log.warn("导出失败");
            }
        }
    }

    /**
     * 导出
     *
     * @param filename 文件名,建议不要带目录
     * @param filepath 文件路径
     * @param data     查询出来的数据
     * @param maxNum   允许导出的最大条数
     * @param message  导出超过最大条数返回 message
     * @param <T>      数据类型
     */
    public <T> void export(String filename, String filepath, List<T> data, Integer maxNum, String message) {
        if (data.size() > maxNum) {
            throw new CommonException(message);
        }
        fillTemplate(filename, filepath, data);
    }

    /**
     * 导出
     *
     * @param filename 文件名,建议不要带目录
     * @param filepath 文件路径
     * @param data     查询出来的数据
     * @param <T>      数据类型
     */
    public <T> void exportWithMerge(String filename, String filepath, List<T> data, BiConsumer<ByteArrayOutputStream, String> consumer, int[][] mergeRegions) {
        ByteArrayOutputStream outputStream = getByteOutPutStream(filepath, data, mergeRegions);
        try {
            consumer.accept(outputStream, filename);
        } catch (Exception e) {
            log.warn("导出文件失败");
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                log.warn("导出失败");
            }
        }
    }

    /**
     * 填充数据
     *
     * @param filename 文件名,建议不要带目录
     * @param filepath 文件路径
     */
    private <T> void fillTemplate(String filename, String filepath, List<T> data) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(filepath);
            InputStream is = classPathResource.getInputStream();
            // 设置输出的格式
            response.reset();
            // 设置响应类型
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 设置编码格式
            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            EasyExcelUtil.simpleFill(response, is, null, data, WriteDirectionEnum.VERTICAL);
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
        redisComponent.setExpire(key, JSON.toJSONString(model, SerializerFeature.WriteNullStringAsEmpty), 1L, TimeUnit.DAYS);
        return model;
    }

    private <T extends BaseImportModel> ImportResultModel<T> get(String operationId) {
        String key = MessageFormat.format(EXCEL_IMPORT_CACHE, operationId);
        String result = redisComponent.get(key);
        return JSONUtil.toBean(result, new TypeReference<ImportResultModel<T>>() {}, false);
    }

    /**
     * 导出文件
     */
    private <T> ByteArrayOutputStream getByteOutPutStream(String templateName, List<T> list) {
        ByteArrayOutputStream bos = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource(templateName);
            InputStream inStream = null;
            try {
                inStream = classPathResource.getInputStream();
            } catch (IOException e) {
                log.info("获取excel模板失败: {}", e.getMessage(), e);
            }
            bos = new ByteArrayOutputStream();
            EasyExcelUtil.simpleWrite(bos, inStream, list);
            bos.flush();
        } catch (IOException e) {
            log.info("传输异常: {}", e.getMessage(), e);
        }
        return bos;
    }

    /**
     * 导出文件
     */
    private <T> ByteArrayOutputStream getByteOutPutStream(String templateName, List<T> list, int[][] mergeRegions) {
        ByteArrayOutputStream bos = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource(templateName);
            InputStream inStream = null;
            try {
                inStream = classPathResource.getInputStream();
            } catch (IOException e) {
                log.info("获取excel模板失败: {}", e.getMessage(), e);
            }
            bos = new ByteArrayOutputStream();
            EasyExcelUtil.simpleWriteWithMerge(bos, inStream, list, mergeRegions);
            bos.flush();
        } catch (IOException e) {
            log.info("传输异常: {}", e.getMessage(), e);
        }
        return bos;
    }

}
