package com.sptwin.apchy.web.model;

import com.sptwin.apchy.web.entity.Resource;
import com.sptwin.spchy.model.enums.Available;
import com.sptwin.spchy.model.enums.ResourceType;

public class ResourceCustom extends Resource {
    private ResourceType resourceTypeEnum;

    private Available availableEnum;

    public Available getAvailableEnum() {
        return availableEnum;
    }

    public void setAvailableEnum(Available availableEnum) {
        this.availableEnum = availableEnum;
    }

    public ResourceType getResourceTypeEnum() {
        return resourceTypeEnum;
    }

    public void setResourceTypeEnum(ResourceType resourceTypeEnum) {
        this.resourceTypeEnum = resourceTypeEnum;
    }
}
