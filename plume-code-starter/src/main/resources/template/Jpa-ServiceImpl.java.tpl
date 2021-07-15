package ${packageName};

import ${basePackageName}.repository.entity.${ClassName}ENT;
import ${basePackageName}.admin.controller.query.${ClassName}Query;
import ${basePackageName}.repository.${ClassName}Repository;
import ${basePackageName}.service.${ClassName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<${ClassName}ENT> page(${ClassName}Query query) {
        //jpa zero-based page index
        Pageable pageable = PageRequest.of(query.getPageIndex() - 1, query.getPageSize());
        return ${className}Repository.findAll(pageable);
    }
}
