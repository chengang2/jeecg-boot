package org.jeecg.modules.cg.cg_n9e_callback.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.cg.cg_n9e_callback.entity.CgN9eCallback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 告警回调
 * @Author: jeecg-boot
 * @Date:   2021-07-21
 * @Version: V1.0
 */
public interface CgN9eCallbackMapper extends BaseMapper<CgN9eCallback> {

    /**
     *  通过主表外键批量删除客户
     * @param workflowName
     * @return
     */
    @Delete("DELETE FROM CG_N9E_CALLBACK WHERE WORKFLOW_NAME = #{workflowName}")
    public boolean deletetByWorkflowName(String workflowName);
}
