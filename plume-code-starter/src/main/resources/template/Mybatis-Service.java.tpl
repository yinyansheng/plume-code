package ${packageName};

import ${basePackageName}.mapper.entity.${ClassName}${setting.entPostfix};
import ${basePackageName}.admin.controller.query.${ClassName}${setting.queryPostfix};
import com.github.pagehelper.PageInfo;
/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
public interface ${ClassName}Service  {
    PageInfo<${ClassName}${setting.entPostfix}> page(${ClassName}${setting.queryPostfix} query);

    void save(${ClassName}${setting.entPostfix} ${className}${setting.entPostfix});

    void updateById(${ClassName}${setting.entPostfix} ${className}${setting.entPostfix});

    void removeById(Long id);
}
