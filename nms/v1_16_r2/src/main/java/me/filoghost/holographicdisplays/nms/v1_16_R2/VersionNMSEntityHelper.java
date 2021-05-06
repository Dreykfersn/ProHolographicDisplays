/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.nms.v1_16_R2;

import me.filoghost.holographicdisplays.core.nms.entity.NMSEntityHelper;
import net.minecraft.server.v1_16_R2.Entity;
import net.minecraft.server.v1_16_R2.Packet;
import net.minecraft.server.v1_16_R2.PlayerChunkMap.EntityTracker;
import net.minecraft.server.v1_16_R2.WorldServer;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class VersionNMSEntityHelper extends NMSEntityHelper<EntityTracker> {

    private final Entity entity;

    public VersionNMSEntityHelper(Entity entity) {
        this.entity = entity;
    }
    
    @Override
    protected EntityTracker getTracker0() {
        return ((WorldServer) entity.world).getChunkProvider().playerChunkMap.trackedEntities.get(entity.getId());
    }

    @Override
    public boolean isTrackedBy(Player bukkitPlayer) {
        EntityTracker tracker = getTracker();
        if (tracker != null) {
            return tracker.trackedPlayers.contains(((CraftPlayer) bukkitPlayer).getHandle());
        } else {
            return false;
        }
    }

    public void broadcastPacket(Packet<?> packet) {
        EntityTracker tracker = getTracker();
        if (tracker != null) {
            tracker.broadcast(packet);
        }
    }

}
