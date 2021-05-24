package org.jeecg.modules.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.api.entity.CgTest;

import java.util.List;

public interface CgTestMapper extends BaseMapper<CgTest> {

    /**
     *  通过主表外键批量删除客户
     * @param age
     * @return
     */
    @Delete("DELETE FROM CG_TEST WHERE AGE = #{age}")
    public boolean deleteTestByAge(int age);

    @Select("SELECT * FROM CG_TEST WHERE NAME_CODE like CONCAT('%',#{nameCode},'%')")
    public List<CgTest> selectTestByName(String nameCode);
}
