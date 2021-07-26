package ${packageName};

#foreach(${extraPackageName} in ${typePackageNameList})
${extraPackageName}
#end

import java.io.Serializable;

/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
public class ${ClassName}${setting.entPostfix} implements Serializable {

#foreach(${fieldModel} in ${fieldModelList})
    /**
     * columnName:${fieldModel.columnName}
     * comment:${fieldModel.comment}
     */
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


