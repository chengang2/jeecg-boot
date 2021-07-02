package org.jeecg.modules.api.entity;

import org.jeecg.modules.online.cgform.converter.FieldCommentConverter;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component("cGConverter")
public class CGConverter implements FieldCommentConverter {
    //导入时对文本做的转换
    @Override
    public String converterToVal(String txt) {
        return "cg_"+txt;
    }
    //导出时对内容做的转换
    @Override
    public String converterToTxt(String val) {
        return val+"_val";
    }

    @Override
    public Map<String, String> getConfig() {
        return null;
    }
}
