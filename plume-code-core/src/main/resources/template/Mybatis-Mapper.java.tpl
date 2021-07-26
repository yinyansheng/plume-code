package ${packageName};

import ${basePackageName}.mapper.entity.${ClassName}${setting.entPostfix};
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
public interface ${ClassName}Mapper {
    List<${ClassName}${setting.entPostfix}> page(@Param("q") ${ClassName}${setting.queryPostfix} query);

    int deleteByPrimaryKey(#foreach(${primaryKey} in ${primaryKeyList})${primaryKey.type} ${primaryKey.name}#if($foreach.hasNext),#end#end);

    int insert(${ClassName}${setting.entPostfix} ${className}${setting.entPostfix});

    int insertSelective(${ClassName}${setting.entPostfix} ${className}${setting.entPostfix});

    ${ClassName}${setting.entPostfix} selectByPrimaryKey(#foreach(${primaryKey} in ${primaryKeyList})${primaryKey.type} ${primaryKey.name}#if($foreach.hasNext),#end#end);

    int updateByPrimaryKeySelective(${ClassName}${setting.entPostfix} ${className}${setting.entPostfix});

    int updateByPrimaryKey(${ClassName}${setting.entPostfix} ${className}${setting.entPostfix});
}
