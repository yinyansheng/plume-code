package ${packageName};

#foreach(${typePackageName} in ${typePackageNameList})
${typePackageName}
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
#if($setting.lombokState)
@Data
#end
public class ${ClassName}${setting.queryPostfix} implements Serializable {
    protected Integer pageIndex = 1;
    protected Integer pageSize = 10;

#foreach(${fieldModel} in ${fieldModelList})
    /**
     * comment:${fieldModel.comment}
     */
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

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
#end
}

