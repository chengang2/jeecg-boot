package org.jeecg.modules.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.api.entity.CgTest;
import org.jeecg.modules.api.mapper.CgTestMapper;
import org.jeecg.modules.api.service.CgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CgServiceImpl extends ServiceImpl<CgTestMapper, CgTest> implements CgService {

    @Autowired
    private CgTestMapper cgTestMapper;

    @Override
    public boolean deleteTestByAge(int age) {
        return cgTestMapper.deleteTestByAge(age);
    }

    @Override
    public List<CgTest> selectTestByName(String nameCode) {
        return cgTestMapper.selectTestByName(nameCode);
    }

    @Override
    public CgTest selectTestById(int id) {
       return  cgTestMapper.selectById(id);
    }
    @Override
    public List<CgTest> selectTest(Map<String,Object> map) {
        return cgTestMapper.selectByMap(map);
    }

    @Override
    public List<CgTest> selectByCg(QueryWrapper<CgTest> queryWrapper) {

        return cgTestMapper.selectByNameList(queryWrapper);
    }

    @Override
    public int UpdateTestById(CgTest cgtest) {
        return cgTestMapper.updateById(cgtest);
    }
    @Override
    public int insertTest(CgTest cgtest) {
       return cgTestMapper.insert(cgtest);
    }
    @Override
    public int deleteTestById(int id) {
        return cgTestMapper.deleteById(id);
    }
    @Override
    public int deleteTestByIds(List<Integer> ids){
        return cgTestMapper.deleteBatchIds(ids);
    }



}
