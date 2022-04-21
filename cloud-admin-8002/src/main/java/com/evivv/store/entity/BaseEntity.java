package com.evivv.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 作为实体类的基类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    private String createdUser;//'创建人',
    private Date createdTime;//'创建时间',
    private String modifiedUser;// '修改人',
    private Date modifiedTime;//'修改时间',
}
