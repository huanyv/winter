package com.book.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Book {

    private Integer id;
    private String bname;
    private String author;
    private String pubcomp;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date pubdate;
    private Integer bcount;
    private BigDecimal price;

}
