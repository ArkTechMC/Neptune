package com.iafenvoy.neptune.fabric.component;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

public class ModComponentEntry implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(NeptunePlayerComponent.NEPTUNE_PLAYER_COMPONENT, NeptunePlayerComponent::new, RespawnCopyStrategy.INVENTORY);
    }
}
