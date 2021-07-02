package ${packageName};

import ${entityPackageName};
import ${basePackageName}.repository.entity.${ClassName}ENT;
import ${basePackageName}.admin.controller.query.${ClassName}Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
public interface ${ClassName}Service  {
    Page<${ClassNameENT}> page(${ClassName}Query query);
}
