package com.plume.code.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plume.code.admin.controller.query.SmartUserQuery;
import com.plume.code.mapper.entity.SmartUserENT;
import com.plume.code.service.SmartUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: ${comment}
 * @author: plume-code
 * @date: 2021-07-05 11:13:50
 **/
@RestController
@RequestMapping("/admin/smartUser/")
public class SmartUserController {

    @Autowired
    private SmartUserService smartUserService;

    @PostMapping("page")
    public IPage<SmartUserENT> page(@RequestBody SmartUserQuery query) {
      LambdaQueryWrapper<SmartUserENT> pageWrapper = Wrappers.lambdaQuery();
      //such as pageWrapper.eq(是否作为查询条件，字段，值)
      //pageWrapper.eq(StringUtils.isNotBlank(query.getPageIndex()), SmartUserQuery::getPageIndex, query.getPageIndex());
      //pageWrapper.eq(query.getPageSize() != null, SmartUserQuery::getPageSize, query.getPageSize());
      return smartUserService.page(new Page<>(query.getPageIndex(), query.getPageSize()), pageWrapper);
    }

    @PostMapping(value = "save")
    public Boolean save(@RequestBody SmartUserENT ent) {
        return smartUserService.save(ent);
    }

    @PostMapping(value = "update")
    public Boolean update(@RequestBody SmartUserENT ent) {
        return smartUserService.updateById(ent);
    }

    @PostMapping(value = "delete")
    public Boolean delete(@RequestParam("id") Integer id) {
        return smartUserService.removeById(id);
    }
}
