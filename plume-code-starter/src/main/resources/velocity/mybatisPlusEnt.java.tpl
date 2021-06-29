package ${packageName};

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description: ${comment}
 * @author: ${author}
 * @date:  ${createTime}
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("${tableName}")
public class ${ClassName}ENT implements Serializable {

    #foreach(${fieldModel} in ${fieldModelList})
        /**
         * ${fieldModel.comment}
         */
        #if(${fieldModel.pk})
        @TableId(value = "${fieldModel.columnName}"
            #if(${fieldModel.pkStrategy}==1)
                    , type = IdType.AUTO
            #end
        )
        #end
    private ${fieldModel.type} ${fieldModel.name};

    #end
}
