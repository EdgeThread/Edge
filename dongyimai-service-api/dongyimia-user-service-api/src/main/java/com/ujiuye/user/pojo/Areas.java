package com.ujiuye.user.pojo;



import lombok.Data;

import java.io.Serializable;

@Data
public class Areas implements Serializable {
    private int id;
    private long areaId;
    private String area;
    private long cityId;
}
