package org.jeecg.modules.cg.shop_test.entity;

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
 * @Description: 测试商品类别
 * @Author: jeecg-boot
 * @Date:   2021-06-07
 * @Version: V1.0
 */
@Data
@TableName("cg_shop_good")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="cg_shop_good对象", description="测试商品类别")
public class CgShopGood implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "username")
	@Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @ApiModelProperty(value = "商品名称")
    private java.lang.String name;
	/**商品价格*/
	@Excel(name = "商品价格", width = 15)
    @ApiModelProperty(value = "商品价格")
    private java.math.BigDecimal price;
	/**商品描述*/
	@Excel(name = "商品描述", width = 15)
    @ApiModelProperty(value = "商品描述")
    private java.lang.String content;
	/**商品类别*/
	@Excel(name = "商品类别", width = 15)
    @ApiModelProperty(value = "商品类别")
    private java.lang.String shopTypeId;
	/**商品状态*/
	@Excel(name = "商品状态", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
    @ApiModelProperty(value = "商品状态")
    private java.lang.Integer status;
	/**省市区*/
	@Excel(name = "省市区", width = 15)
    @ApiModelProperty(value = "省市区")
    private java.lang.String ccc;
	/**用户选择*/
	@Excel(name = "用户选择", width = 15, dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
	@Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
    @ApiModelProperty(value = "用户选择")
    private java.lang.String selectName;
	/**是否展示*/
	@Excel(name = "是否展示", width = 15)
    @ApiModelProperty(value = "是否展示")
    private java.lang.String isShow;
	/**图片*/
	@Excel(name = "图片", width = 15)
    @ApiModelProperty(value = "图片")
    private java.lang.String pic;
}
