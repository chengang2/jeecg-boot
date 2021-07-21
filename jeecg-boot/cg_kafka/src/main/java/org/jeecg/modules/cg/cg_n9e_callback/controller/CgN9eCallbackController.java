package org.jeecg.modules.cg.cg_n9e_callback.controller;

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
import org.jeecg.modules.cg.cg_n9e_callback.entity.CgN9eCallback;
import org.jeecg.modules.cg.cg_n9e_callback.service.ICgN9eCallbackService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: 告警回调
 * @Author: jeecg-boot
 * @Date:   2021-07-21
 * @Version: V1.0
 */
@Api(tags="告警回调")
@RestController
@RequestMapping("/cg_n9e_callback/cgN9eCallback")
@Slf4j
public class CgN9eCallbackController extends JeecgController<CgN9eCallback, ICgN9eCallbackService> {
	@Autowired
	private ICgN9eCallbackService cgN9eCallbackService;
	
	/**
	 * 分页列表查询
	 *
	 * @param cgN9eCallback
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "告警回调-分页列表查询")
	@ApiOperation(value="告警回调-分页列表查询", notes="告警回调-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CgN9eCallback cgN9eCallback,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CgN9eCallback> queryWrapper = QueryGenerator.initQueryWrapper(cgN9eCallback, req.getParameterMap());
		Page<CgN9eCallback> page = new Page<CgN9eCallback>(pageNo, pageSize);
		IPage<CgN9eCallback> pageList = cgN9eCallbackService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param cgN9eCallback
	 * @return
	 */
	@AutoLog(value = "告警回调-添加")
	@ApiOperation(value="告警回调-添加", notes="告警回调-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CgN9eCallback cgN9eCallback) {
		cgN9eCallbackService.save(cgN9eCallback);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param cgN9eCallback
	 * @return
	 */
	@AutoLog(value = "告警回调-编辑")
	@ApiOperation(value="告警回调-编辑", notes="告警回调-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CgN9eCallback cgN9eCallback) {
		cgN9eCallbackService.updateById(cgN9eCallback);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "告警回调-通过id删除")
	@ApiOperation(value="告警回调-通过id删除", notes="告警回调-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		cgN9eCallbackService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "告警回调-批量删除")
	@ApiOperation(value="告警回调-批量删除", notes="告警回调-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.cgN9eCallbackService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "告警回调-通过id查询")
	@ApiOperation(value="告警回调-通过id查询", notes="告警回调-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CgN9eCallback cgN9eCallback = cgN9eCallbackService.getById(id);
		if(cgN9eCallback==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(cgN9eCallback);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param cgN9eCallback
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CgN9eCallback cgN9eCallback) {
        return super.exportXls(request, cgN9eCallback, CgN9eCallback.class, "告警回调");
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
        return super.importExcel(request, response, CgN9eCallback.class);
    }

}
