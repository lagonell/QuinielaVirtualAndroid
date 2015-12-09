package com.eoldsolutions.quinielavirtualandroid.domain.model;

import com.marcohc.helperoid.ParserHelper;

public abstract class BaseModel implements Model {

    public String toJsonString() {
        return ParserHelper.toJsonString(this);
    }

}
