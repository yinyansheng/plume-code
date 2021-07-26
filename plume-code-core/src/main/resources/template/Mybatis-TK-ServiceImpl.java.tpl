package ${packageName};

import ${basePackageName}.mapper.entity.${ClassName}${setting.entPostfix};
import ${basePackageName}.admin.controller.query.${ClassName}${setting.queryPostfix};
import ${basePackageName}.mapper.${ClassName}Mapper;
import ${basePackageName}.service.${ClassName}Service;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;
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
    public PageInfo<${ClassName}${setting.entPostfix}> page(${ClassName}${setting.queryPostfix} query) {
        Example example = new Example(${ClassName}${setting.entPostfix}.class);
        Example.Criteria criteria = example.createCriteria();
        PageInfo<SmartUserENT> page = PageHelper.startPage(query.getPageIndex(), query.getPageSize())
                .doSelectPageInfo(()-> ${className}Mapper.selectByExample(example));
        return page;
    }

    @Override
    public void save(${ClassName}${setting.entPostfix} ${className}${setting.entPostfix}) {
        ${className}Mapper.insert(${className}${setting.entPostfix});
    }

    @Override
    public void updateById(${ClassName}${setting.entPostfix} ${className}${setting.entPostfix}) {
        ${className}Mapper.updateByPrimaryKeySelective(${className}${setting.entPostfix});
    }

    @Override
    public void removeById(Long id) {
        ${className}Mapper.deleteByPrimaryKey(id);
    }
}
