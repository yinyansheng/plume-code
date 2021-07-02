package ${packageName};

import ${basePackageName}.mapper.entity.${ClassName}ENT;
import ${basePackageName}.admin.controller.query.${ClassName}Query;
import ${basePackageName}.mapper.${ClassName}Mapper;
import ${basePackageName}.service.${ClassName}Service;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
@Service
public class ${ClassName}ServiceImpl implements ${ClassName}Service {

    @Autowired
    private ${ClassName}Mapper ${className}Mapper;

    Page<${ClassName}> page(${ClassName}Query query) {
        Page<${ClassName}> page = PageHelper.startPage(query.getPageIndex(), query.getPageSize())
                                .doSelectPage(()-> ${className}Mapper.page(query));
        return page;
    }

    void save(${ClassName}ENT ${className}ENT) {
        ${className}Mapper.insert(${className}ENT);
    }

    void updateById(${ClassName}ENT ${className}ENT) {
        ${className}Mapper.updateByPrimaryKeySelective(${className}ENT);
    }

    void removeById(${ClassName}ENT ${className}ENT) {
        ${className}Mapper.deleteByPrimaryKey(${className}ENT);
    }
}
