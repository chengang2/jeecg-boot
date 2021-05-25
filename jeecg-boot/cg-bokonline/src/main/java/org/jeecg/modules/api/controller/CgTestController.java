package org.jeecg.modules.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.api.entity.CgTest;
import org.jeecg.modules.api.service.CgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/test/cg")
@Api(tags="cg-bokonline测试...")
@Slf4j
public class CgTestController extends JeecgController<CgTest, CgService> {

    @Autowired
    private CgService cgService;

    /**
     * hello world
     *
     * @return
     */
    @GetMapping(value = "/hello")
    public Result<String> hello() {
        Result<String> result = new Result<String>();
        result.setResult("Hello World!");
        result.setSuccess(true);
        return result;
    }

    /**
     * 分页列表查询
     *127.0.0.1:8080/jeecg-boot/test/cg/list?pageNo=1&pageSize=10&nameCode=*chengang*
     * @param cgtest
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "分页获取Demo数据列表", notes = "分页获取所有Demo数据列表")
    @GetMapping(value = "/list")
    public Result<?> list(CgTest cgtest, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                          HttpServletRequest req) {
        QueryWrapper<CgTest> queryWrapper = QueryGenerator.initQueryWrapper(cgtest, req.getParameterMap());
        Page<CgTest> page = new Page<CgTest>(pageNo, pageSize);

        IPage<CgTest> pageList = cgService.page(page, queryWrapper);
        log.info("查询当前页：" + pageList.getCurrent());
        log.info("查询当前页数量：" + pageList.getSize());
        log.info("查询结果数量：" + pageList.getRecords().size());
        log.info("数据总数：" + pageList.getTotal());
        return Result.OK(pageList);
    }

    /**
     * 列表查询
     *127.0.0.1:8080/jeecg-boot/test/cg/all?nameCode=*chengang*
     * @param cgtest
     * @param req
     * @return
     */
    @ApiOperation(value = "获取Demo数据列表", notes = "获取所有Demo数据列表")
    @GetMapping(value = "/all")
    public Result<?> all(CgTest cgtest,
                          HttpServletRequest req) {
        QueryWrapper<CgTest> queryWrapper = QueryGenerator.initQueryWrapper(cgtest, req.getParameterMap());
        List<CgTest> list = cgService.list(queryWrapper);
        return Result.OK(list);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    @ApiOperation(value = "通过ID查询DEMO", notes = "通过ID查询DEMO")
    public Result<?> queryById(@ApiParam(name = "id", value = "示例id", required = true) @RequestParam(name = "id", required = true) Integer id) {
        CgTest cgTest = cgService.selectTestById(id);
        return Result.OK(cgTest);
    }

    /**
     * 通过name查询
     *
     * @param nameCode
     * @return
     */
    @GetMapping(value = "/queryByName")
    @ApiOperation(value = "通过NameCode查询DEMO", notes = "通过NameCode查询DEMO")
    public Result<?> queryByName(@ApiParam(name = "nameCode", value = "示例id", required = true) @RequestParam(name = "nameCode", required = true) String nameCode) {
        List<CgTest> cgTests = cgService.selectTestByName(nameCode);
        return Result.OK(cgTests);
    }

    /**
     * 通过map查询
     *
     * @param nameCode
     * @return
     */
    @GetMapping(value = "/queryByMap")
    @ApiOperation(value = "通过Map查询DEMO", notes = "通过Map查询DEMO")
    public Result<?> queryByMap(@ApiParam(name = "nameCode", value = "示例id", required = true) @RequestParam(name = "nameCode", required = true) String nameCode) {
        Map<String,Object> map = new HashMap<>();
        map.put("name_code",nameCode);
        map.put("age",30);
        List<CgTest> cgTests = cgService.selectTest(map);
        return Result.OK(cgTests);
    }

    /**
     * 添加
     *
     * @param cgtest
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加测试DEMO")
    @ApiOperation(value = "添加DEMO", notes = "添加DEMO")
    public Result<?> add(@RequestBody CgTest cgtest) {
        cgService.insertTest(cgtest);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param cgtest
     * @return
     */
    @PutMapping(value = "/edit")
    @ApiOperation(value = "编辑DEMO", notes = "编辑DEMO")
    @AutoLog(value = "编辑DEMO", operateType = CommonConstant.OPERATE_TYPE_3)
    public Result<?> edit(@RequestBody CgTest cgtest) {
        cgService.UpdateTestById(cgtest);
        return Result.OK("更新成功！");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "删除测试DEMO")
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "通过ID删除DEMO", notes = "通过ID删除DEMO")
    public Result<?> delete(@RequestParam(name = "id", required = true) Integer id) {
        cgService.deleteTestById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 通过age删除
     *
     * @param age
     * @return
     */
    @AutoLog(value = "删除测试DEMO")
    @DeleteMapping(value = "/deleteByAge")
    @ApiOperation(value = "通过Age删除DEMO", notes = "通过Age删除DEMO")
    public Result<?> deleteByAge(@RequestParam(name = "age", required = true) Integer age) {
        cgService.deleteTestByAge(age);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除DEMO", notes = "批量删除DEMO")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        List<Integer> idList = new ArrayList<>();
        String[] split = ids.split(",");
        for (String id:split) {
            idList.add(Integer.valueOf(id));
        }
        cgService.deleteTestByIds(idList);
        return Result.OK("批量删除成功！");
    }



}
