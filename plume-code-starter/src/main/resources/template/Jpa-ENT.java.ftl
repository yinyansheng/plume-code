package ${packageName};

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @description: ${comment!''}
 * @author: ${author}
 * @date: ${createTime}
 **/
@Entity
@Table(name = "${tableName}")
<#if (isMultiplePK)>
@IdClass(${ClassName}PK.class)
</#if>
public class ${ClassName}ENT {

<#list fieldModelList as fieldModel>
    /**
    * comment:${fieldModel.comment}
    */
    @Column(name = "${fieldModel.columnName}")
    <#if (!isMultiplePK && fieldModel.pk)>
    @Id
        <#if (fieldModel.pkStrategy==1)>
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        </#if>
    </#if>
    private ${fieldModel.type} ${fieldModel.name};

</#list>
<#list fieldModelList as fieldModel>
    public ${fieldModel.type} get${fieldModel.upperCaseName}() {
    return ${fieldModel.name};
    }

    public void set${fieldModel.upperCaseName}(${fieldModel.type} ${fieldModel.name}) {
    this.${fieldModel.name} = ${fieldModel.name};
    }

</#list>
}
