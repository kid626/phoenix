package com.bruce.phoenix.core.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName excel
 * @Date 2021/2/2 16:33
 * @Author Bruce
 */
@Slf4j
public class EasyExcelUtil {

    /**
     * 最简单的读
     * <p>1. 创建excel对应的实体对象
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器
     * <p>3. 直接读即可
     *
     * @param fileName           文件名
     * @param pojoClass          对应实体类
     * @param pojoReaderListener 对应实体类监听器
     * @param headRowNumber      行头数量，决定了从哪一行开始读
     * @param <T>                实体类
     */
    public static <T> void simpleRead(String fileName, Class<T> pojoClass, ReadListener<T> pojoReaderListener, Integer headRowNumber, Integer sheetNo) {
        if (headRowNumber == null) {
            headRowNumber = 1;
        }
        if (sheetNo == null) {
            sheetNo = 0;
        }
        EasyExcel.read(fileName, pojoClass, pojoReaderListener).sheet(sheetNo).headRowNumber(headRowNumber).doRead();
    }

    /**
     * 最简单的读
     * <p>1. 创建excel对应的实体对象
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器
     * <p>3. 直接读即可
     *
     * @param file               MultipartFile
     * @param pojoClass          对应实体类
     * @param pojoReaderListener 对应实体类监听器
     * @param headRowNumber      行头数量，决定了从哪一行开始读
     * @param <T>                实体类
     * @throws IOException IOException
     */
    public static <T> void simpleRead(MultipartFile file, Class<T> pojoClass, ReadListener<T> pojoReaderListener, Integer headRowNumber, Integer sheetNo) throws IOException {
        if (headRowNumber == null) {
            headRowNumber = 1;
        }
        if (sheetNo == null) {
            sheetNo = 0;
        }
        EasyExcel.read(file.getInputStream(), pojoClass, pojoReaderListener).sheet(sheetNo).headRowNumber(headRowNumber).doRead();
    }

    /**
     * 最简单的写,若要自定义格式，合并单元格请参照 {@link "https://alibaba-easyexcel.github.io/quickstart/write.html#%E8%87%AA%E5%AE%9A%E4%B9%89%E6%A0%B7%E5%BC%8F"}
     * <p>1. 创建excel对应的实体对象
     * <p>2. 直接写即可
     *
     * @param fileName         文件
     * @param templateFileName 模板文件
     * @param pojoClass        对应实体类
     * @param data             数据
     * @param needHead         是否需要导出表头
     * @param sheetNo          表单 no
     * @param sheetName        表单名称
     * @param <T>              实体类
     */
    public static <T> void simpleWrite(String fileName, String templateFileName, Class<T> pojoClass, List<T> data, Integer sheetNo, String sheetName, Boolean needHead) {
        if (sheetNo == null) {
            sheetNo = 0;
        }
        if (StrUtil.isEmpty(sheetName)) {
            sheetName = "Sheet" + sheetNo;
        }
        ExcelWriterBuilder write = EasyExcel.write(fileName, pojoClass);
        if (StrUtil.isNotEmpty(templateFileName)) {
            write.withTemplate(templateFileName);
        }
        if (needHead != null) {
            write.needHead(needHead);
        }
        ExcelWriterSheetBuilder builder = write.sheet(sheetNo, sheetName);
        builder.doWrite(data);
    }

    /**
     * 最简单的写,若要自定义格式，合并单元格请参照 {@link "https://alibaba-easyexcel.github.io/quickstart/write.html#%E8%87%AA%E5%AE%9A%E4%B9%89%E6%A0%B7%E5%BC%8F"}
     * <p>1. 创建excel对应的实体对象
     * <p>2. 直接写即可
     *
     * @param response         HttpServletResponse
     * @param templateFileName 模板文件
     * @param pojoClass        对应实体类
     * @param data             数据
     * @param needHead         是否需要导出表头
     * @param sheetNo          表单 no
     * @param sheetName        表单名称
     * @param <T>              实体类
     */
    public static <T> void simpleWrite(HttpServletResponse response, String templateFileName, Class<T> pojoClass, List<T> data, Integer sheetNo, String sheetName, Boolean needHead) throws IOException {
        if (sheetNo == null) {
            sheetNo = 0;
        }
        if (StrUtil.isEmpty(sheetName)) {
            sheetName = "Sheet" + sheetNo;
        }
        ExcelWriterBuilder write = EasyExcel.write(response.getOutputStream(), pojoClass);
        if (StrUtil.isNotEmpty(templateFileName)) {
            write.withTemplate(templateFileName);
        }
        if (needHead != null) {
            write.needHead(needHead);
        }
        ExcelWriterSheetBuilder builder = write.sheet(sheetNo, sheetName);
        builder.doWrite(data);
    }

    /**
     * 最简单的写,若要自定义格式，合并单元格请参照 {@link "https://alibaba-easyexcel.github.io/quickstart/write.html#%E8%87%AA%E5%AE%9A%E4%B9%89%E6%A0%B7%E5%BC%8F"}
     * <p>1. 创建excel对应的实体对象
     * <p>2. 直接写即可
     *
     * @param bos      输出流
     * @param inStream 输入流
     * @param data     数据
     * @param <T>      实体类
     */
    public static <T> void simpleWrite(ByteArrayOutputStream bos, InputStream inStream, List<T> data) {
        ExcelWriterBuilder write = EasyExcel.write(bos);
        write.withTemplate(inStream);
        ExcelWriterSheetBuilder builder = write.sheet();
        builder.doWrite(data);
    }

    /**
     * 最简单的写,若要自定义格式，合并单元格请参照 {@link "https://alibaba-easyexcel.github.io/quickstart/write.html#%E8%87%AA%E5%AE%9A%E4%B9%89%E6%A0%B7%E5%BC%8F"}
     * <p>1. 创建excel对应的实体对象
     * <p>2. 直接写即可
     *
     * @param bos          输出流
     * @param inStream     输入流
     * @param data         数据
     * @param <T>          实体类
     * @param mergeRegions 合并区域
     */
    public static <T> void simpleWriteWithMerge(ByteArrayOutputStream bos, InputStream inStream, List<T> data, int[][] mergeRegions) {
        ExcelWriterBuilder write = EasyExcel.write(bos);
        write.withTemplate(inStream);
        write.registerWriteHandler(new AbstractMergeStrategy() {
            @Override
            protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
                // 遍历所有合并区域，添加合并配置
                for (int[] region : mergeRegions) {
                    if (region.length != 4) {
                        throw new IllegalArgumentException("合并区域配置错误：格式必须是{起始行, 结束行, 起始列, 结束列}");
                    }
                    int firstRow = region[0];
                    int lastRow = region[1];
                    int firstCol = region[2];
                    int lastCol = region[3];
                    // 校验行名列名合法性
                    if (firstRow < 0 || lastRow < firstRow || firstCol < 0 || lastCol < firstCol) {
                        throw new IllegalArgumentException("合并区域参数非法：起始行/列不能小于0，结束行/列不能小于起始行/列");
                    }
                    sheet.addMergedRegionUnsafe(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
                }
            }

        });
        write.registerWriteHandler(defaultCellStyleStrategy());
        ExcelWriterSheetBuilder builder = write.sheet();
        builder.doWrite(data);
    }

    /**
     * 默认单元格样式策略（可根据需求修改）
     */
    private static HorizontalCellStyleStrategy defaultCellStyleStrategy() {
        // 表头样式
        WriteCellStyle headStyle = new WriteCellStyle();
        // 表头背景色：浅蓝色（可修改IndexedColors枚举）
        headStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 表头字体：加粗、12号
        WriteFont headFont = new WriteFont();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short) 12);
        headStyle.setWriteFont(headFont);
        // 表头对齐：水平居中、垂直居中
        headStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 表头边框：全边框
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);

        // 内容样式
        WriteCellStyle contentStyle = new WriteCellStyle();
        // 内容字体：11号
        WriteFont contentFont = new WriteFont();
        contentFont.setFontHeightInPoints((short) 11);
        contentStyle.setWriteFont(contentFont);
        // 内容对齐：水平居中、垂直居中
        contentStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 内容边框：全边框
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setBorderRight(BorderStyle.THIN);

        // 返回样式策略（表头+内容）
        return new HorizontalCellStyleStrategy(headStyle, contentStyle);
    }

    /**
     * 简单填充，适合 header + 列表 模式
     *
     * @param fileName         生成的文件名
     * @param templateFileName 模板文件名
     * @param map              用于填充 header 里的参数 模板中以 {name} 来替代
     * @param fillData         用于填充 列表，模板中以 {.name} 来替代
     * @param direction        方向 HORIZONTAL | VERTICAL {@link WriteDirectionEnum},默认为 VERTICAL
     * @param <T>              列表实体类
     */
    public static <T> void simpleFill(String fileName, String templateFileName, Map<String, Object> map, List<T> fillData, WriteDirectionEnum direction) {
        if (direction == null) {
            direction = WriteDirectionEnum.VERTICAL;
        }
        FillConfig fillConfig = FillConfig.builder().direction(direction).build();
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        if (CollUtil.isNotEmpty(fillData)) {
            excelWriter.fill(fillData, fillConfig, writeSheet);
        }
        if (CollUtil.isNotEmpty(map)) {
            excelWriter.fill(map, fillConfig, writeSheet);
        }
        excelWriter.finish();
    }

    /**
     * 简单填充，适合 header + 列表 模式
     *
     * @param fileName  生成的文件名
     * @param is        InputStream
     * @param map       用于填充 header 里的参数 模板中以 {name} 来替代
     * @param fillData  用于填充 列表，模板中以 {.name} 来替代
     * @param direction 方向 HORIZONTAL | VERTICAL {@link WriteDirectionEnum},默认为 VERTICAL
     * @param <T>       列表实体类
     */
    public static <T> void simpleFill(String fileName, InputStream is, Map<String, Object> map, List<T> fillData, WriteDirectionEnum direction) {
        if (direction == null) {
            direction = WriteDirectionEnum.VERTICAL;
        }
        FillConfig fillConfig = FillConfig.builder().direction(direction).build();
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(is).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        if (CollUtil.isNotEmpty(fillData)) {
            excelWriter.fill(fillData, fillConfig, writeSheet);
        }
        if (CollUtil.isNotEmpty(map)) {
            excelWriter.fill(map, fillConfig, writeSheet);
        }
        excelWriter.finish();
    }

    /**
     * 简单填充，适合 header + 列表 模式
     *
     * @param response         HttpServletResponse
     * @param templateFileName 模板文件名
     * @param map              用于填充 header 里的参数 模板中以 {name} 来替代
     * @param fillData         用于填充 列表，模板中以 {.name} 来替代
     * @param direction        方向 HORIZONTAL | VERTICAL {@link WriteDirectionEnum},默认为 VERTICAL
     * @param <T>              列表实体类
     */
    public static <T> void simpleFill(HttpServletResponse response, String templateFileName, Map<String, Object> map, List<T> fillData, WriteDirectionEnum direction) throws IOException {
        if (direction == null) {
            direction = WriteDirectionEnum.VERTICAL;
        }
        FillConfig fillConfig = FillConfig.builder().direction(direction).build();
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        if (CollUtil.isNotEmpty(fillData)) {
            excelWriter.fill(fillData, fillConfig, writeSheet);
        }
        if (CollUtil.isNotEmpty(map)) {
            excelWriter.fill(map, fillConfig, writeSheet);
        }
        excelWriter.finish();
    }

    /**
     * 简单填充，适合 header + 列表 模式
     *
     * @param response  HttpServletResponse
     * @param is        InputStream
     * @param map       用于填充 header 里的参数 模板中以 {name} 来替代
     * @param fillData  用于填充 列表，模板中以 {.name} 来替代
     * @param direction 方向 HORIZONTAL | VERTICAL {@link WriteDirectionEnum},默认为 VERTICAL
     * @param <T>       列表实体类
     */
    public static <T> void simpleFill(HttpServletResponse response, InputStream is, Map<String, Object> map, List<T> fillData, WriteDirectionEnum direction) throws IOException {
        if (direction == null) {
            direction = WriteDirectionEnum.VERTICAL;
        }
        FillConfig fillConfig = FillConfig.builder().direction(direction).build();
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(is).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        if (CollUtil.isNotEmpty(fillData)) {
            excelWriter.fill(fillData, fillConfig, writeSheet);
        }
        if (CollUtil.isNotEmpty(map)) {
            excelWriter.fill(map, fillConfig, writeSheet);
        }
        excelWriter.finish();
    }

    /**
     * 复杂填充，适合 header + 列表 + tail 模式
     *
     * @param fileName         生成的文件名
     * @param templateFileName 模板文件名
     * @param map              用于填充 header 里的参数 模板中以 {name} 来替代
     * @param fillData         用于填充 列表，模板中以 {.name} 来替代
     * @param tailData         用于写入 tail 里
     * @param <T>              列表实体类
     * @param <V>              tail 实体类
     */
    public static <T, V> void complexFill(String fileName, String templateFileName, Map<String, Object> map, List<T> fillData, List<V> tailData) {
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        if (CollUtil.isNotEmpty(fillData)) {
            excelWriter.fill(fillData, writeSheet);
        }
        if (CollUtil.isNotEmpty(map)) {
            excelWriter.fill(map, writeSheet);
        }
        excelWriter.write(tailData, writeSheet);
        excelWriter.finish();
    }

    /**
     * 复杂填充，适合 header + 列表 + tail 模式
     *
     * @param response         HttpServletResponse
     * @param templateFileName 模板文件名
     * @param map              用于填充 header 里的参数 模板中以 {name} 来替代
     * @param fillData         用于填充 列表，模板中以 {.name} 来替代
     * @param tailData         用于写入 tail 里
     * @param <T>              列表实体类
     * @param <V>              tail 实体类
     */
    public static <T, V> void complexFill(HttpServletResponse response, String templateFileName, Map<String, Object> map, List<T> fillData, List<V> tailData) throws IOException {
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        if (CollUtil.isNotEmpty(fillData)) {
            excelWriter.fill(fillData, writeSheet);
        }
        if (CollUtil.isNotEmpty(map)) {
            excelWriter.fill(map, writeSheet);
        }
        excelWriter.write(tailData, writeSheet);
        excelWriter.finish();
    }


}
