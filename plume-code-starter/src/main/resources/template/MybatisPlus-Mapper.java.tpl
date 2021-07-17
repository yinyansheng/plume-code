package ${packageName};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${basePackageName}.mapper.entity.${ClassName}${setting.entPostfix};
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
@Mapper
public interface ${ClassName}Mapper extends BaseMapper<${ClassName}${setting.entPostfix}> {

}
