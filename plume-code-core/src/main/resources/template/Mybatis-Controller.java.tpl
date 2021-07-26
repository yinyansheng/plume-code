package ${packageName};

import ${basePackageName}.admin.controller.query.${ClassName}${setting.queryPostfix};
import ${basePackageName}.mapper.entity.${ClassName}${setting.entPostfix};
import ${basePackageName}.service.${ClassName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

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
    public PageInfo<${ClassName}${setting.entPostfix}> page(@RequestBody ${ClassName}${setting.queryPostfix} query) {
      return ${className}Service.page(query);
    }

    @PostMapping(value = "save")
    public void save(@RequestBody ${ClassName}${setting.entPostfix} ent) {
        ${className}Service.save(ent);
    }

    @PostMapping(value = "update")
    public void update(@RequestBody ${ClassName}${setting.entPostfix} ent) {
        ${className}Service.updateById(ent);
    }

    @PostMapping(value = "delete")
    public void delete(@RequestParam("id") Long id) {
        ${className}Service.removeById(id);
    }
}
