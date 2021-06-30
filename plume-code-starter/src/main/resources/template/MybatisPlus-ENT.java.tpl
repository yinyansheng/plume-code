package ${packageName};

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

#foreach(${extraPackageName} in ${extraPackageNameList})
${extraPackageName}
#end

import java.io.Serializable;
import com.baomidou.mybatisplus.extension.service.IService;
import ${entityPackageName};

/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
@TableName("${tableName}")
public class ${ClassName}ENT implements Serializable {

#foreach(${fieldModel} in ${fieldModelList})
    /**
     * columnName:${fieldModel.columnName}
     * comment:${fieldModel.comment}
     */
#if($fieldModel.pk)
#if($fieldModel.multiplePk)
    @MppMultiId
#end
#if($fieldModel.pkStrategy==1)
    @TableId(value = "${fieldModel.name}", type = IdType.AUTO)
#else
    @TableId(value = "${fieldModel.name}")
#end
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


