package ${packageName};

import ${interfacePackageName}.${ClassName}Service;
#if($lombok)
import lombok.extern.slf4j.Slf4j;
#end
import org.springframework.stereotype.Service;

/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
@Service
#if($lombok)
@Slf4j
#end
public class ${ClassName}ServiceImpl implements ${ClassName}Service {

}
