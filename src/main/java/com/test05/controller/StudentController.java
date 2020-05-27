package com.test05.controller;

import com.test05.entity.Student;
import com.test05.service.StudentService;
import com.test05.utils.ExcelUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * (Student)表控制层
 *
 * @author makejava
 * @since 2020-05-27 19:03:19
 */
@RestController
@RequestMapping("student")
public class StudentController {
    /**
     * 服务对象
     */
    @Resource
    private StudentService studentService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Student selectOne(Integer id) {
        return this.studentService.queryById(id);
    }

    @RequestMapping(value="/import",method = RequestMethod.POST,produces = { "application/json;charset=UTF-8"})
    public  String imporCourse(@RequestParam MultipartFile excelFile, HttpSession httpSession) throws IOException {
        InputStream in =excelFile.getInputStream();
        String fileOriginalName=excelFile.getOriginalFilename();
        String fileName=excelFile.getName();
        String excelType=fileOriginalName.substring(fileOriginalName.indexOf(".")+1);

        //记录插入多条记录到了数据库；
        int importSuccessNum=0;
        int importAllNum=0;
        boolean importSuccess;

        System.out.println("in:::"+in);
        System.out.println("fileOriginalName:::"+fileOriginalName);
        System.out.println("fileName:::"+fileName);
        System.out.println("excelType:::"+excelType);
        List<Object> forlist= ExcelUtils.ExcelImport(excelFile,Student.class,true,excelType);
        for (Object object: forlist) {
            Student student=(Student) object;
            importAllNum++;
            importSuccess=studentService.getExcel(student);
            if(importSuccess)
                importSuccessNum++;

        }
        httpSession.setAttribute("importAllNum",importAllNum);
        httpSession.setAttribute("importSuccessNum",importSuccessNum);

        if(importSuccessNum>0)
            return "success!"+"应处理"+importAllNum+"条,已成功处理"+importSuccessNum+"条！";
        else{
            return "error!";
        }
    }
}