package gg.joshbaker.killstats

import gg.joshbaker.killstats.listener.KillListener
import gg.joshbaker.killstats.placeholder.KillStatsPlaceholders
import gg.joshbaker.killstats.profile.ProfileRegistry
import org.bukkit.plugin.java.JavaPlugin

class KillStats : JavaPlugin() {
    private lateinit var profileRegistry: ProfileRegistry
    private lateinit var activeWorldNames: List<String>

    fun isTrackedWorld(worldName: String) = activeWorldNames.contains(worldName)

    override fun onEnable() {
        saveDefaultConfig()

        activeWorldNames = config.getStringList("activeWorlds")
        logger.info("Active worlds: $activeWorldNames")

        profileRegistry = ProfileRegistry(this)

        if (server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            KillStatsPlaceholders(this, profileRegistry).register()
            logger.info("Successfully registered placeholders with PlaceholderAPI")
        } else {
            logger.warning("PlaceholderAPI not found! Placeholders won't work.")
        }

        server.pluginManager.registerEvents(KillListener(this, profileRegistry), this)
    }

    override fun onDisable() {
        profileRegistry.saveAllProfiles()
    }
}