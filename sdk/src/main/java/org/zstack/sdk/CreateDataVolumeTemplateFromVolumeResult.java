package org.zstack.sdk;

import org.zstack.sdk.ImageInventory;

public class CreateDataVolumeTemplateFromVolumeResult {
    public ImageInventory inventory;
    public void setInventory(ImageInventory inventory) {
        this.inventory = inventory;
    }
    public ImageInventory getInventory() {
        return this.inventory;
    }

}
