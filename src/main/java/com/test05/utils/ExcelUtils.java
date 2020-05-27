package com.test05.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    public static List<Object> ExcelImport(MultipartFile file, Class<?> beanClass, boolean titleExist, String excelType) {
        List<Object> list = new ArrayList<Object>();
        Workbook wb;
        try {
            InputStream input = file.getInputStream();
            if (excelType.equals("xls")) {
                wb = new HSSFWorkbook(input);
            } else if (excelType.equals("xlsx")) {
                wb = new XSSFWorkbook(input);
            } else {
                System.out.println("error!!");
                return null;
            }
            Sheet sheet = wb.getSheetAt(0);
            int i;
            if (titleExist) {
                i = 2;
            } else {
                i = 0;
            }
            for (i = 0; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Object object = beanClass.newInstance();
                Field[] fields = beanClass.getDeclaredFields();
                int j = 0;
                for (Field field : fields) {
                    String fieldName = field.getName();
                    PropertyDescriptor pd = new PropertyDescriptor(fieldName, beanClass);
                    Method getMethod = pd.getWriteMethod();
                    Cell cell = row.getCell(j++);
                    try {
                        int type = cell.getCellType();
                        if (type == cell.CELL_TYPE_BOOLEAN) {
                            // 返回布尔类型的值
                            boolean value = cell.getBooleanCellValue();
                            getMethod.invoke(object, value);
                            System.out.println(object);
                            System.out.println(value);
                        } else if (type == cell.CELL_TYPE_NUMERIC) {
                            // 返回数值类型的值
                            Double d = cell.getNumericCellValue();
                            int value = d.intValue();
                            getMethod.invoke(object, new Integer(value));
                        } else if (type == cell.CELL_TYPE_STRING) {
                            // 返回字符串类型的值
                            String value = cell.getStringCellValue();
                            getMethod.invoke(object, new String(value));
                        }
                    } catch (Exception e) {
                        System.out.println("");

                    }
                }
                list.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}


