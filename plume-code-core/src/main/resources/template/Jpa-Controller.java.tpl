package ${packageName};

import ${basePackageName}.admin.controller.query.${ClassName}${setting.queryPostfix};
import ${basePackageName}.repository.entity.${ClassName}${setting.entPostfix};
import ${basePackageName}.repository.${ClassName}Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @Autowired
    private ${ClassName}Repository ${className}Repository;

    @PostMapping("page")
    public Page<${ClassName}${setting.entPostfix}> page(@RequestBody ${ClassName}${setting.queryPostfix} query) {
      return ${className}Service.page(query);
    }

    @PostMapping(value = "save")
    public void save(@RequestBody ${ClassName}${setting.entPostfix} ent) {
        ${className}Repository.save(ent);
    }

    @PostMapping(value = "update")
    public void update(@RequestBody ${ClassName}${setting.entPostfix} ent) {
        ${className}Repository.save(ent);
    }

    @PostMapping(value = "delete")
    public void delete(@RequestBody ${ClassName}${setting.entPostfix} ent) {
        ${className}Repository.delete(id);
    }
}
