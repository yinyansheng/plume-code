package ${packageName};

import ${entityPackageName};
import ${basePackageName}.repository.entity.${ClassName}ENT;
import ${basePackageName}.admin.controller.query.${ClassName}Query;
import com.github.pagehelper.Page;
/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
public interface ${ClassName}Service  {
    Page<${ClassName}> page(${ClassName}Query query);

    void save(${ClassName}ENT ${className}ENT);

    void updateById(${ClassName}ENT ${className}ENT);

    void removeById(${ClassName}ENT ${className}ENT);
}
