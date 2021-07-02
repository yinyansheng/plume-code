package ${packageName};

import ${basePackageName}.admin.query.${ClassName}Query;
import ${basePackageName}.mapper.entity.${ClassName}ENT;
import ${basePackageName}.repository.${ClassName}Repository;
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
    private ${ClassName}Repository ${className}Repository;

    @PostMapping("page")
    public Page<${ClassName}ENT> page(@RequestBody ${ClassName}Query query) {
      return ${className}Repository.page(query);
    }

    @PostMapping(value = "save")
    public Boolean save(@RequestBody ${ClassName}ENT ent) {
        return ${className}Repository.save(ent);
    }

    @PostMapping(value = "update")
    public Boolean update(@RequestBody ${ClassName}ENT ent) {
        return ${className}Repository.save(ent);
    }

    @PostMapping(value = "delete")
    public Boolean delete(@RequestParam("id") Integer id) {
        return ${className}Repository.delete(id);
    }
}
