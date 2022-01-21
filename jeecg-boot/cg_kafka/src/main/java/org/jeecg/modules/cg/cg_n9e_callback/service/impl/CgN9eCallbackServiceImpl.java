package org.jeecg.modules.cg.cg_n9e_callback.service.impl;

import org.jeecg.modules.cg.cg_n9e_callback.entity.CgN9eCallback;
import org.jeecg.modules.cg.cg_n9e_callback.mapper.CgN9eCallbackMapper;
import org.jeecg.modules.cg.cg_n9e_callback.service.ICgN9eCallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 告警回调
 * @Author: jeecg-boot
 * @Date:   2021-07-21
 * @Version: V1.0
 */
@Service
public class CgN9eCallbackServiceImpl extends ServiceImpl<CgN9eCallbackMapper, CgN9eCallback> implements ICgN9eCallbackService {

    @Autowired
    private CgN9eCallbackMapper cgN9eCallbackMapper;


    @Override
    public boolean deletetByWorkflowName(String workflowName) {
        return cgN9eCallbackMapper.deletetByWorkflowName(workflowName);
    }
}
