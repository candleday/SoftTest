package com.test05.entity;

import java.io.Serializable;

/**
 * (Student)实体类
 *
 * @author makejava
 * @since 2020-05-27 21:26:16
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 639969914098890229L;
    /**
    * 序号
    */
    private Integer id;
    /**
    * 学号
    */
    private Integer sid;
    /**
    * 姓名
    */
    private String sname;
    /**
    * 班级
    */
    private String classes;
    /**
    * 备注
    */
    private String comment;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}