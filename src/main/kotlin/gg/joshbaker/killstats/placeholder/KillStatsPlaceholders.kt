package gg.joshbaker.killstats.placeholder

import gg.joshbaker.killstats.profile.ProfileRegistry
import org.bukkit.plugin.java.JavaPlugin
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

class KillStatsPlaceholders(private val plugin: JavaPlugin, private val profileRegistry: ProfileRegistry) : PlaceholderExpansion() {
    override fun getIdentifier(): String {
        return "killstats"
    }

    override fun getAuthor() = plugin.description.authors.joinToString()

    override fun getVersion() = plugin.description.version

    override fun persist() = true

    override fun onPlaceholderRequest(player: Player?, identifier: String): String? {
        if (player == null) return null

        val profile = profileRegistry.getProfile(player.uniqueId)

        return when (identifier.lowercase()) {
            "kills" -> profile.kills.toString()
            "deaths" -> profile.deaths.toString()
            "kdr" -> String.format("%.2f", profile.kdr)
            "killstreak" -> profile.killstreak.toString()
            "best_killstreak" -> profile.bestKillstreak.toString()
            else -> null
        }
    }
}