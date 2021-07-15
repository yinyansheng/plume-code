package ${packageName};

import ${basePackageName}.mapper.entity.${ClassName}ENT;
import ${basePackageName}.admin.controller.query.${ClassName}Query;
import ${basePackageName}.mapper.${ClassName}Mapper;
import ${basePackageName}.service.${ClassName}Service;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
@Service
public class ${ClassName}ServiceImpl implements ${ClassName}Service {

    @Autowired
    private ${ClassName}Mapper ${className}Mapper;

    @Override
    public PageInfo<${ClassName}ENT> page(${ClassName}Query query) {
        PageInfo<${ClassName}> page = PageHelper.startPage(query.getPageIndex(), query.getPageSize())
                                .doSelectPageInfo(()-> ${className}Mapper.page(query));
        return page;
    }

    @Override
    public void save(${ClassName}ENT ${className}ENT) {
        ${className}Mapper.insert(${className}ENT);
    }

    @Override
    public void updateById(${ClassName}ENT ${className}ENT) {
        ${className}Mapper.updateByPrimaryKeySelective(${className}ENT);
    }

    @Override
    public void removeById(Long id) {
        ${className}Mapper.deleteByPrimaryKey(id);
    }
}
