package azmalent.backportedflora

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.config.Configuration

object ModConfig {
    abstract class FloraConfig(var enabled: Boolean, var generationChance: Float, var patchAttempts: Int, var plantAttempts: Int) {
        abstract val name: String

        open fun init(config: Configuration) {
            val lowerCaseName = name.toLowerCase()

            enabled = config.getBoolean("Enabled", name, enabled, "Whether $lowerCaseName is registered.")
            generationChance = config.getFloat("Generation Chance", name,
                    generationChance, 0.0f, 1.0f,
                    "The chance to attempt generating $lowerCaseName in a given chunk.")
            patchAttempts = config.getInt("Patch Generation Attempts", name,
                    patchAttempts, 0, 16,
                    "Attempts to generate a $lowerCaseName patch in a given chunk.")
            plantAttempts = config.getInt("Plant Generation Attempts", name,
                    plantAttempts, 0, 64,
                    "Attempts to generate a $lowerCaseName in every patch.")
        }
    }

    abstract class OverworldFloraConfig(enabled: Boolean, generationChance: Float, patchAttempts: Int, plantAttempts: Int) : FloraConfig(enabled, generationChance, patchAttempts, plantAttempts) {
        var dimensionBlacklist = emptyArray<String>()
        var dimensionBlacklistIsWhitelist = false

        var biomeBlacklist = emptyArray<String>()
        var biomeBlacklistIsWhitelist = false

        override fun init(config: Configuration) {
            super.init(config)

            val lowerCaseName = name.toLowerCase()

            dimensionBlacklist = config.getStringList("Dimension Blacklist", name, dimensionBlacklist,
                    "Dimension names (i.e. 'twilight_forest') where $lowerCaseName will not generate.")
            dimensionBlacklistIsWhitelist = config.getBoolean("Dimension Blacklist Is Whitelist", name, dimensionBlacklistIsWhitelist,
                    "Whether dimension blacklist is a whitelist.")

            biomeBlacklist = config.getStringList("Biome Blacklist", name, biomeBlacklist,
                    "Biome IDs (i.e. 'minecraft:savanna') where $lowerCaseName will not generate.")
            biomeBlacklistIsWhitelist = config.getBoolean("Biome Blacklist Is Whitelist", name, biomeBlacklistIsWhitelist,
                    "Whether biome blacklist is a whitelist.")
        }

        fun canGenerate(world: World, pos: BlockPos): Boolean {
            val dimensionId = world.provider.dimensionType.getName()
            if (dimensionId in dimensionBlacklist) {
                return dimensionBlacklistIsWhitelist
            } else if (dimensionBlacklistIsWhitelist) {
                return false
            }

            val biomeId = world.getBiome(pos).registryName.toString()
            return when {
                biomeId in biomeBlacklist -> biomeBlacklistIsWhitelist
                biomeBlacklistIsWhitelist -> false
                else -> true
            }
        }
    }

    abstract class SeaweedConfig(enabled: Boolean, generationChance: Float, patchAttempts: Int, plantAttempts: Int) : OverworldFloraConfig(enabled, generationChance, patchAttempts, plantAttempts) {
        var generatesUnderground = false

        override fun init(config: Configuration) {
            super.init(config)

            generatesUnderground = config.getBoolean("Generates Underground", name, generatesUnderground,
                "Whether ${name.toLowerCase()} can generate underground. " +
                "This is mostly useful if you have mods like Yung's Better Caves which add flooded caverns.")
        }
    }

    object Kelp : SeaweedConfig(true, 0.5f, 4, 64) {
        override val name  = "Kelp"

        var growthEnabled = true
        var driedKelpEnabled = true
        var kelpSoupEnabled = true

        override fun init(config: Configuration) {
            super.init(config)

            growthEnabled = config.getBoolean("Growth Enabled", name, growthEnabled,
                    "Should kelp grow on its own")
            driedKelpEnabled = config.getBoolean("Dried Kelp", name, driedKelpEnabled,
                    "Whether dried kelp is enabled.\n" +
                            "Dried kelp is a food item that can be eaten quickly.\n" +
                            "You can also craft it into blocks that can be used as fuel.")
            kelpSoupEnabled = config.getBoolean("Kelp Soup", name, kelpSoupEnabled,
                    "Whether kelp can be cooked into delicious soup")
        }

    }

    object Seagrass : SeaweedConfig(true, 1.0f, 4, 64) {
        override val name = "Seagrass"
    }

    object Cornflower : OverworldFloraConfig(true, 1/8f, 4, 64) {
        override val name = "Cornflower"
    }

    object LilyOfTheValley : OverworldFloraConfig(true, 1/8f, 4, 64) {
        override val name = "Lily of the Valley"
    }

    object WitherRose : FloraConfig(true, 0.5f, 8, 64) {
        override val name = "Wither Rose"

        var causesWither = true
        var dropFromWitherKills = true

        override fun init(config: Configuration) {
            super.init(config)

            causesWither = config.getBoolean("Wither Roses Cause Wither", name, causesWither,
                    "Should wither roses apply wither to touching entities?")

            dropFromWitherKills = config.getBoolean("Drop From Wither Kills", name, dropFromWitherKills, "Should wither roses drop from wither kills?")
        }
    }

    fun sync() {
        val config = BackportedFlora.config

        try {
            config.load()
            Kelp.init(config)
            Seagrass.init(config)
            Cornflower.init(config)
            LilyOfTheValley.init(config)
            WitherRose.init(config)
        } catch(e: Exception) {
            BackportedFlora.LOGGER.error("Error: failed to load config!")
        } finally {
            if (config.hasChanged()) {
                config.save()
            }
        }
    }
}
