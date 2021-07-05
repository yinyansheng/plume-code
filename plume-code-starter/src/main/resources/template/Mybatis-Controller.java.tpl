package ${packageName};

import ${basePackageName}.admin.controller.query.${ClassName}Query;
import ${basePackageName}.mapper.entity.${ClassName}ENT;
import ${basePackageName}.service.${ClassName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.Page;

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
    public Page<${ClassName}ENT> page(@RequestBody ${ClassName}Query query) {
      return ${className}Service.page(query);
    }

    @PostMapping(value = "save")
    public Boolean save(@RequestBody ${ClassName}ENT ent) {
        return ${className}Service.save(ent);
    }

    @PostMapping(value = "update")
    public Boolean update(@RequestBody ${ClassName}ENT ent) {
        return ${className}Service.updateById(ent);
    }

    @PostMapping(value = "delete")
    public Boolean delete(@RequestParam("id") Integer id) {
        return ${className}Service.removeById(id);
    }
}
