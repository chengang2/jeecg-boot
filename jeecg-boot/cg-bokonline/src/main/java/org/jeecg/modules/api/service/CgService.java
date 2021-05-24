package org.jeecg.modules.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.api.entity.CgTest;

import java.util.List;
import java.util.Map;

public interface CgService extends IService<CgTest> {

    public boolean deleteTestByAge(int age);

    public List<CgTest> selectTestByName(String nameCode);

    public int insertTest(CgTest cgtest);

    public int UpdateTestById(CgTest cgtest);

    public int deleteTestById(int id);

    public int deleteTestByIds(List<Integer> ids);

    public CgTest selectTestById(int id);

    public List<CgTest> selectTest(Map<String,Object> map);
}
