package org.jeecg.modules.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("cg_test")
public class CgTest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    @TableId(type = IdType.AUTO)
    private java.lang.Integer id;
    /**name_code*/
    private java.lang.String nameCode;
    /**age*/
    private java.lang.Integer age;

}
