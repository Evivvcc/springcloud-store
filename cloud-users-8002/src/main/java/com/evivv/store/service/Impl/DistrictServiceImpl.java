package com.evivv.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.evivv.store.entity.District;
import com.evivv.store.mapper.DistrictMapper;
import com.evivv.store.service.IDistrictService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class DistrictServiceImpl extends ServiceImpl<DistrictMapper, District> implements IDistrictService {

    @Override
    public List<District> getByParent(String parent) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("parent", parent);
        List<District> result = listByMap(map);
        for (District district: result) {
            district.setId(null);
            district.setParent(null);
        }
        return result;
    }

    @Override
    public String getNameByCode(String code) {
        return getOne(new QueryWrapper<District>().eq("code", code)).getName();
    }
}
