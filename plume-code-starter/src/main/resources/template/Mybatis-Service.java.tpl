package ${packageName};

import ${basePackageName}.mapper.entity.${ClassName}ENT;
import ${basePackageName}.admin.controller.query.${ClassName}Query;
import com.github.pagehelper.PageInfo;
/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
public interface ${ClassName}Service  {
    PageInfo<${ClassName}ENT> page(${ClassName}Query query);

    void save(${ClassName}ENT ${className}ENT);

    void updateById(${ClassName}ENT ${className}ENT);

    void removeById(Long id);
}
