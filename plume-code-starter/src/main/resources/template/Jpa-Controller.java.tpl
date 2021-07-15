package ${packageName};

import ${basePackageName}.admin.controller.query.${ClassName}Query;
import ${basePackageName}.repository.entity.${ClassName}ENT;
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
    public Page<${ClassName}ENT> page(@RequestBody ${ClassName}Query query) {
      return ${className}Service.page(query);
    }

    @PostMapping(value = "save")
    public void save(@RequestBody ${ClassName}ENT ent) {
        ${className}Repository.save(ent);
    }

    @PostMapping(value = "update")
    public void update(@RequestBody ${ClassName}ENT ent) {
        ${className}Repository.save(ent);
    }

    @PostMapping(value = "delete")
    public void delete(@RequestBody ${ClassName}ENT ent) {
        ${className}Repository.delete(id);
    }
}
