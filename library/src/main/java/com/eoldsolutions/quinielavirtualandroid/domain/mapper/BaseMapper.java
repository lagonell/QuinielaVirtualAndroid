package com.eoldsolutions.quinielavirtualandroid.domain.mapper;

import com.eoldsolutions.quinielavirtualandroid.domain.entity.Entity;
import com.eoldsolutions.quinielavirtualandroid.domain.model.BaseModel;
import com.marcohc.helperoid.MapperHelper;

public abstract class BaseMapper {

    public static <E extends Entity> E transform(BaseModel model, Class<E> type) {
        return MapperHelper.getMapper().map(model, type);
    }

    public static <M extends BaseModel> M transform(Entity entity, Class<M> type) {
        return MapperHelper.getMapper().map(entity, type);
    }

}
