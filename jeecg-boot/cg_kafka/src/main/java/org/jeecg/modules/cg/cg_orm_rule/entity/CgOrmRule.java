package org.jeecg.modules.cg.cg_orm_rule.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 入库规则表
 * @Author: jeecg-boot
 * @Date:   2022-01-20
 * @Version: V1.0
 */
@Data
@TableName("cg_orm_rule")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="cg_orm_rule对象", description="入库规则表")
public class CgOrmRule implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**topic名称*/
	@Excel(name = "topic名称", width = 15)
    @ApiModelProperty(value = "topic名称")
    private String datasourceTopic;
	/**字段名*/
	@Excel(name = "字段名", width = 15)
    @ApiModelProperty(value = "字段名")
    private String fieldName;
	/**判断表达式*/
	@Excel(name = "判断表达式", width = 15)
    @ApiModelProperty(value = "判断表达式")
    private String expression;
	/**写入表名*/
	@Excel(name = "写入表名", width = 15)
    @ApiModelProperty(value = "写入表名")
    private String tableName;
	/**业务名*/
	@Excel(name = "业务名", width = 15)
    @ApiModelProperty(value = "业务名")
    private String buss;
	/**创建者*/
	@Excel(name = "创建者", width = 15)
    @ApiModelProperty(value = "创建者")
    private String createName;
}
