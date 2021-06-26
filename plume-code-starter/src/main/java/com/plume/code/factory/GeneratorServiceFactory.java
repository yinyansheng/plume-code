package com.plume.code.factory;

import com.plume.code.common.context.GeneratorContext;
import com.plume.code.model.ConnectionModel;
import com.plume.code.model.SettingModel;
import com.plume.code.service.DatabaseService;
import com.plume.code.service.GeneratorService;
import com.plume.code.service.impl.H2DatabaseService;
import com.plume.code.service.impl.MysqlDatabaseService;
import com.plume.code.service.impl.ServiceGeneratorService;
import org.apache.commons.lang3.NotImplementedException;

public class GeneratorServiceFactory {

    public static GeneratorService getGeneratorService(String type, GeneratorContext generatorContext) {
        switch (type.toLowerCase()) {
            case "service":
                return ServiceGeneratorService.instance(generatorContext);
            default:
                throw new NotImplementedException("not support:%s", type);
        }
    }
}
