package org.jeecg.modules.cg.cg_n9e_callback.entity;

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
 * @Description: 告警回调
 * @Author: jeecg-boot
 * @Date:   2021-07-21
 * @Version: V1.0
 */
@Data
@TableName("cg_n9e_callback")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="cg_n9e_callback对象", description="告警回调")
public class CgN9eCallback implements Serializable {
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
	/**流程名*/
	@Excel(name = "流程名", width = 15)
    @ApiModelProperty(value = "流程名")
    private String workflowName;
	/**主机名*/
	@Excel(name = "主机名", width = 15, dictTable = "cg_n9e_node", dicText = "endpoint", dicCode = "endpoint")
	@Dict(dictTable = "cg_n9e_node", dicText = "endpoint", dicCode = "endpoint")
    @ApiModelProperty(value = "主机名")
    private String endpoint;
	/**告警指标*/
	@Excel(name = "告警指标", width = 15)
    @ApiModelProperty(value = "告警指标")
    private String metric;
	/**告警标签*/
	@Excel(name = "告警标签", width = 15)
    @ApiModelProperty(value = "告警标签")
    private String tags;
	/**回调脚本*/
	@Excel(name = "回调脚本", width = 15)
    @ApiModelProperty(value = "回调脚本")
    private String runShell;
	/**执行回调用户*/
	@Excel(name = "执行回调用户", width = 15)
    @ApiModelProperty(value = "执行回调用户")
    private String runUser;
	/**创建者*/
	@Excel(name = "创建者", width = 15)
    @ApiModelProperty(value = "创建者")
    private String createName;
}
