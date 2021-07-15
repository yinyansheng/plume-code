package ${packageName};

import ${basePackageName}.mapper.entity.${ClassName}ENT;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
public interface ${ClassName}Mapper {
    List<${ClassName}ENT> page(@Param("q") ${ClassName}Query query);

    int deleteByPrimaryKey(#foreach(${primaryKey} in ${primaryKeyList})${primaryKey.type} ${primaryKey.name}#if($foreach.hasNext),#end#end);

    int insert(${ClassName}ENT ${className}ENT);

    int insertSelective(${ClassName}ENT ${className}ENT);

    ${ClassName}ENT selectByPrimaryKey(#foreach(${primaryKey} in ${primaryKeyList})${primaryKey.type} ${primaryKey.name}#if($foreach.hasNext),#end#end);

    int updateByPrimaryKeySelective(${ClassName}ENT ${className}ENT);

    int updateByPrimaryKey(${ClassName}ENT ${className}ENT);
}
