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
#if($fieldModel.pk)
    @TableId(value = "${fieldModel.name}", type = IdType.AUTO)
#end
    private ${fieldModel.type} ${fieldModel.name};

#end
#foreach(${fieldModel} in ${fieldModelList})
    public ${fieldModel.type} get${fieldModel.upperCaseName}() {
        return ${fieldModel.name};
    }

    public void set${fieldModel.upperCaseName}(${fieldModel.type} ${fieldModel.name}) {
        this.${fieldModel.name} = ${fieldModel.name};
    }

#end
}

