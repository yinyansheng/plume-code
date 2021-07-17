package ${packageName};

import ${basePackageName}.repository.entity.${ClassName}${setting.entPostfix};
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
public interface ${ClassName}Repository extends JpaRepository<${ClassName}${setting.entPostfix}, ${pkType}> {

}
