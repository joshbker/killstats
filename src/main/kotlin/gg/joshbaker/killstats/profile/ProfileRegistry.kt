package gg.joshbaker.killstats.profile

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*
import kotlin.math.max

class ProfileRegistry(plugin: JavaPlugin) {
    private val profilesFile: File = File(plugin.dataFolder, "profiles.yml")
    private var profilesConfig: FileConfiguration

    init {
        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdirs()
        }

        if (!profilesFile.exists()) {
            profilesFile.createNewFile()
        }

        profilesConfig = YamlConfiguration.loadConfiguration(profilesFile)
    }

    fun getProfile(uuid: UUID): ProfileStatistics {
        return profilesConfig.getConfigurationSection(uuid.toString())?.let {
            ProfileStatistics(
                it.getInt("kills"),
                it.getInt("deaths"),
                it.getDouble("kdr"),
                it.getInt("killstreak"),
                it.getInt("bestKillstreak")
            )
        } ?: ProfileStatistics()
    }

    fun saveProfile(uuid: UUID, profileStatistics: ProfileStatistics) {
        val uuidString = uuid.toString()

        profilesConfig.set("$uuidString.kills", profileStatistics.kills)
        profilesConfig.set("$uuidString.deaths", profileStatistics.deaths)
        profilesConfig.set("$uuidString.kdr", profileStatistics.kdr)
        profilesConfig.set("$uuidString.killstreak", profileStatistics.killstreak)
        profilesConfig.set("$uuidString.bestKillstreak", profileStatistics.bestKillstreak)

        profilesConfig.save(profilesFile)
    }

    fun saveAllProfiles() {
        profilesConfig.save(profilesFile)
    }

    fun reloadProfiles() {
        profilesConfig = YamlConfiguration.loadConfiguration(profilesFile)
    }
}

data class ProfileStatistics(
    var kills: Int = 0,
    var deaths: Int = 0,
    var kdr: Double = 0.0,
    var killstreak: Int = 0,
    var bestKillstreak: Int = 0
) {
    fun handeKill() {
        kills++
        updateKdr()
        killstreak++
        if (killstreak > bestKillstreak) bestKillstreak = killstreak
    }

    fun handleDeath() {
        deaths++
        updateKdr()
        killstreak = 0
    }

    private fun updateKdr() {
        kdr = kills.toDouble() / max(deaths, 1)
    }
}