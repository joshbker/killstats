package gg.joshbaker.killstats.listener

import gg.joshbaker.killstats.KillStats
import gg.joshbaker.killstats.profile.ProfileRegistry
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class KillListener(private val plugin: KillStats, private val profileRegistry: ProfileRegistry) : Listener {
    @EventHandler
    fun onKill(event: PlayerDeathEvent) {
        val dead = event.entity

        if (plugin.isTrackedWorld(dead.world.name)) {
            profileRegistry.getProfile(dead.uniqueId).apply {
                handleDeath()
                profileRegistry.saveProfile(dead.uniqueId, this)
            }
        }

        event.entity.killer?.let { killer ->
            profileRegistry.getProfile(killer.uniqueId).apply {
                handeKill()
                profileRegistry.saveProfile(killer.uniqueId, this)
            }
        }
    }
}