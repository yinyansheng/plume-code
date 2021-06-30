package ${packageName};

import ${backPackageName}.mapper.entity.${ClassName}ENT;
import ${backPackageName}.mapper.${ClassName}Mapper;
import ${backPackageName}.service.${ClassName}Service;
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
