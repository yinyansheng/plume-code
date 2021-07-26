package ${packageName};

import ${basePackageName}.repository.entity.${ClassName}${setting.entPostfix};
import ${basePackageName}.admin.controller.query.${ClassName}${setting.queryPostfix};
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
public interface ${ClassName}Service  {
    Page<${ClassName}${setting.entPostfix}> page(${ClassName}${setting.queryPostfix} query);
}
