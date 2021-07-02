package ${packageName};

import ${basePackageName}.repository.entity.${ClassName}ENT;
import ${basePackageName}.admin.controller.query.${ClassName}Query;
import ${basePackageName}.repository.${ClassName}Repository;
import org.springframework.stereotype.Service;
/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
@Service
public class ${ClassName}ServiceImpl implements ${ClassName}Service {

    @Autowired
    private ${ClassName}Repository ${className}Repository;

    Page<${ClassName}ENT> page(${ClassName}Query query) {
        Pageable pageable = PageRequest.of(query.getPageIndex(), query.getPageSize());
        return ${className}Repository.findAll(pageable);
    }
}
