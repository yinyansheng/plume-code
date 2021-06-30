package ${packageName};

import ${basePackageName}.mapper.entity.${ClassName}ENT;
import ${basePackageName}.mapper.${ClassName}Mapper;
import ${basePackageName}.service.${ClassName}Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
@Service
public class ${ClassName}ServiceImpl extends ServiceImpl<${ClassName}Mapper, ${ClassName}ENT> implements ${ClassName}Service {

}
