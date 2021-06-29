package ${packageName};

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

#foreach(${extraPackageName} in ${extraPackageNameList})
${extraPackageName}
#end

import java.io.Serializable;

/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
@TableName("${tableName}")
public class ${ClassName}ENT implements Serializable {

    #foreach(${fieldModel} in ${fieldModelList})
/**
     * ${fieldModel.comment}
     */
    private ${fieldModel.type} ${fieldModel.name};

    #end
}

