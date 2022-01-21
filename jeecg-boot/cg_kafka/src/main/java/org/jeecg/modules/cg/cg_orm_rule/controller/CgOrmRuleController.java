package org.jeecg.modules.cg.cg_orm_rule.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.cg.cg_orm_rule.entity.CgOrmRule;
import org.jeecg.modules.cg.cg_orm_rule.entity.SdkOrmRule;
import org.jeecg.modules.cg.cg_orm_rule.service.ICgOrmRuleService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.cg.cg_orm_rule.service.ISdkOrmRuleService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 入库规则表
 * @Author: jeecg-boot
 * @Date:   2022-01-20
 * @Version: V1.0
 */
@Api(tags="入库规则表")
@RestController
@RequestMapping("/cg_orm_rule/cgOrmRule")
@Slf4j
public class CgOrmRuleController extends JeecgController<CgOrmRule, ICgOrmRuleService> {
	@Autowired
	private ICgOrmRuleService cgOrmRuleService;
	@Autowired
	private ISdkOrmRuleService sdkOrmRuleService;
	
	/**
	 * 分页列表查询
	 *
	 * @param cgOrmRule
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "入库规则表-分页列表查询")
	@ApiOperation(value="入库规则表-分页列表查询", notes="入库规则表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CgOrmRule cgOrmRule,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CgOrmRule> queryWrapper = QueryGenerator.initQueryWrapper(cgOrmRule, req.getParameterMap());
		Page<CgOrmRule> page = new Page<CgOrmRule>(pageNo, pageSize);
		IPage<CgOrmRule> pageList = cgOrmRuleService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param cgOrmRule
	 * @return
	 */
	@AutoLog(value = "入库规则表-添加")
	@ApiOperation(value="入库规则表-添加", notes="入库规则表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CgOrmRule cgOrmRule) {
		cgOrmRuleService.save(cgOrmRule);
		SdkOrmRule sdkOrmRule = new SdkOrmRule();
		sdkOrmRule.setDatasource("kafka");
		sdkOrmRule.setDatasourceTopic(cgOrmRule.getDatasourceTopic());
		sdkOrmRule.setField(cgOrmRule.getFieldName());
		sdkOrmRule.setExpression(cgOrmRule.getExpression());
		sdkOrmRule.setTableName(cgOrmRule.getTableName());
		sdkOrmRule.setBuss(cgOrmRule.getBuss());
		sdkOrmRuleService.save(sdkOrmRule);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param cgOrmRule
	 * @return
	 */
	@AutoLog(value = "入库规则表-编辑")
	@ApiOperation(value="入库规则表-编辑", notes="入库规则表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CgOrmRule cgOrmRule) {
		CgOrmRule oldOrm = cgOrmRuleService.getById(cgOrmRule.getId());
		cgOrmRuleService.updateById(cgOrmRule);

		QueryWrapper<SdkOrmRule> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(SdkOrmRule::getDatasourceTopic,oldOrm.getDatasourceTopic());
		queryWrapper.lambda().eq(SdkOrmRule::getField,oldOrm.getFieldName());
		queryWrapper.lambda().eq(SdkOrmRule::getExpression,oldOrm.getExpression());
		queryWrapper.lambda().eq(SdkOrmRule::getTableName,oldOrm.getTableName());
		queryWrapper.lambda().eq(SdkOrmRule::getBuss,oldOrm.getBuss());
		SdkOrmRule one = sdkOrmRuleService.getOne(queryWrapper);

		SdkOrmRule sdkOrmRule = new SdkOrmRule();
		sdkOrmRule.setId(one.getId());
		sdkOrmRule.setDatasource("kafka");
		sdkOrmRule.setDatasourceTopic(cgOrmRule.getDatasourceTopic());
		sdkOrmRule.setField(cgOrmRule.getFieldName());
		sdkOrmRule.setExpression(cgOrmRule.getExpression());
		sdkOrmRule.setTableName(cgOrmRule.getTableName());
		sdkOrmRule.setBuss(cgOrmRule.getBuss());

		sdkOrmRuleService.updateById(sdkOrmRule);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "入库规则表-通过id删除")
	@ApiOperation(value="入库规则表-通过id删除", notes="入库规则表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		CgOrmRule oldOrm = cgOrmRuleService.getById(id);
		QueryWrapper<SdkOrmRule> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(SdkOrmRule::getDatasourceTopic,oldOrm.getDatasourceTopic());
		queryWrapper.lambda().eq(SdkOrmRule::getField,oldOrm.getFieldName());
		queryWrapper.lambda().eq(SdkOrmRule::getExpression,oldOrm.getExpression());
		queryWrapper.lambda().eq(SdkOrmRule::getTableName,oldOrm.getTableName());
		queryWrapper.lambda().eq(SdkOrmRule::getBuss,oldOrm.getBuss());
		SdkOrmRule one = sdkOrmRuleService.getOne(queryWrapper);

		cgOrmRuleService.removeById(id);
		sdkOrmRuleService.removeById(one.getId());

		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "入库规则表-批量删除")
	@ApiOperation(value="入库规则表-批量删除", notes="入库规则表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.cgOrmRuleService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "入库规则表-通过id查询")
	@ApiOperation(value="入库规则表-通过id查询", notes="入库规则表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CgOrmRule cgOrmRule = cgOrmRuleService.getById(id);
		if(cgOrmRule==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(cgOrmRule);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param cgOrmRule
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CgOrmRule cgOrmRule) {
        return super.exportXls(request, cgOrmRule, CgOrmRule.class, "入库规则表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CgOrmRule.class);
		//return Result.error("不支持导入，请手动新增");
    }

}
