package ${packageName};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${basePackageName}.admin.controller.query.${ClassName}${setting.queryPostfix};
import ${basePackageName}.mapper.entity.${ClassName}${setting.entPostfix};
import ${basePackageName}.service.${ClassName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
@RestController
@RequestMapping("/admin/${className}/")
public class ${ClassName}Controller {

    @Autowired
    private ${ClassName}Service ${className}Service;

    @PostMapping("page")
    public IPage<${ClassName}${setting.entPostfix}> page(@RequestBody ${ClassName}${setting.queryPostfix} query) {
      LambdaQueryWrapper<${ClassName}${setting.entPostfix}> pageWrapper = Wrappers.lambdaQuery();
      //such as pageWrapper.eq(是否作为查询条件，字段，值)
      //pageWrapper.eq(StringUtils.isNotBlank(query.getPageIndex()), ${ClassName}${setting.queryPostfix}::getPageIndex, query.getPageIndex());
      //pageWrapper.eq(query.getPageSize() != null, ${ClassName}${setting.queryPostfix}::getPageSize, query.getPageSize());
      return ${className}Service.page(new Page<>(query.getPageIndex(), query.getPageSize()), pageWrapper);
    }

    @PostMapping(value = "save")
    public Boolean save(@RequestBody ${ClassName}${setting.entPostfix} ent) {
        return ${className}Service.save(ent);
    }

    @PostMapping(value = "update")
    public Boolean update(@RequestBody ${ClassName}${setting.entPostfix} ent) {
        return ${className}Service.updateById(ent);
    }

    @PostMapping(value = "delete")
    public Boolean delete(@RequestParam("id") Integer id) {
        return ${className}Service.removeById(id);
    }
}
