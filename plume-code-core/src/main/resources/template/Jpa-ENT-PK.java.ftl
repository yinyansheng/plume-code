package ${packageName};

import java.io.Serializable;

/**
* @description: ${comment!''}
* @author: ${author}
* @date: ${createTime}
**/
public class ${ClassName}${setting.entPostfix}PK implements Serializable {

<#list primaryKeyList as pkModel>
    private ${pkModel.type} ${pkModel.name};

</#list>
<#list primaryKeyList as pkModel>
    public ${pkModel.type} get${pkModel.upperCaseName}() {
    return ${pkModel.name};
    }

    public void set${pkModel.upperCaseName}(${pkModel.type} ${pkModel.name}) {
    this.${pkModel.name} = ${pkModel.name};
    }

</#list>
}
