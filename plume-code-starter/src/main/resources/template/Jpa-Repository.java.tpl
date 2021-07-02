package ${packageName};

import ${entityPackageName};
import ${basePackageName}.repository.entity.${ClassName}ENT;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
public interface ${ClassName}Repository extends JpaRepository<${ClassName}ENT, ${pkType}> {

}
