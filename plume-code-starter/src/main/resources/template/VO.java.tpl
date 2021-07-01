package ${packageName};

#foreach(${typePackageName} in ${typePackageNameList})
${typePackageName}
#end

import java.io.Serializable;

/**
 * @description: ${comment}
 * @author: ${author}
 * @date: ${createTime}
 **/
public class ${ClassName}VO implements Serializable {

#foreach(${fieldModel} in ${fieldModelList})
    /**
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

