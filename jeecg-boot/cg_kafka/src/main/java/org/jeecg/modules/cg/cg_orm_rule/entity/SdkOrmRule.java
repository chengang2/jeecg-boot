package org.jeecg.modules.cg.cg_orm_rule.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @author chengang1991
 * @date 2022/1/20
 * @apiNote
 */
@Data
@TableName("sdkdb.loget_orm_rule")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="loget_orm_rule对象", description="入库规则")
public class SdkOrmRule {

    /**主键*/
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "datasource")
    private String datasource;
    @ApiModelProperty(value = "topic名称")
    private String datasourceTopic;
    @ApiModelProperty(value = "字段名")
    private String field;
    @ApiModelProperty(value = "判断表达式")
    private String expression;
    /**写入表名*/
    @ApiModelProperty(value = "写入表名")
    private String tableName;
    /**业务名*/
    @ApiModelProperty(value = "业务名")
    private String buss;

}
