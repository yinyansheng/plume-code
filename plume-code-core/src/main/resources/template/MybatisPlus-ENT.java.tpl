package ${packageName};

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

#foreach(${extraPackageName} in ${typePackageNameList})
${extraPackageName}
#end

import java.io.Serializable;
#if($setting.lombokState)
import lombok.Data;
#end
/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
@TableName("${tableName}")
#if($setting.lombokState)
@Data
#end
public class ${ClassName}${setting.entPostfix} implements Serializable {

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

#if(!$setting.lombokState)
#foreach(${fieldModel} in ${fieldModelList})
    public ${fieldModel.type} get${fieldModel.upperCaseName}() {
        return ${fieldModel.name};
    }

    public void set${fieldModel.upperCaseName}(${fieldModel.type} ${fieldModel.name}) {
        this.${fieldModel.name} = ${fieldModel.name};
    }

#end
#end
}


