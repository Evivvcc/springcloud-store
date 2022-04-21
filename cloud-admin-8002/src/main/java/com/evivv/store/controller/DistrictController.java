package com.evivv.store.controller;

import com.evivv.store.entity.District;
import com.evivv.store.service.IDistrictService;
import com.evivv.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin/districts")
public class DistrictController extends BaseController {
    @Autowired
    private IDistrictService districtService;

    @GetMapping("/")
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<>(OK, data);
    }


}
