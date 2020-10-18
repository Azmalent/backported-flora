package azmalent.backportedflora

import net.minecraftforge.common.config.Configuration

object ModConfig {
    abstract class FloraConfig(var enabled: Boolean, var generationChance: Float, var patchAttempts: Int, var plantAttempts: Int) {
        abstract val name: String

        open fun init(config: Configuration) {
            val lowerCaseName = name.toLowerCase()

            config.addCustomCategoryComment(name, "Features related to $lowerCaseName.")
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

    object Kelp : FloraConfig(true, 0.5f, 4, 64) {
        override val name  = "Kelp"

        var growthEnabled = true
        var driedKelpEnabled = true
        var kelpSoupEnabled = true

        override fun init(config: Configuration) {
            super.init(config)

            growthEnabled = config.getBoolean("Growth Enabled", name, growthEnabled,
                    "Should kelp grow on its own?")
            driedKelpEnabled = config.getBoolean("Dried Kelp", name, driedKelpEnabled,
                    "Whether dried kelp is enabled.\n" +
                            "Dried kelp is a food item that can be eaten quickly.\n" +
                            "You can also craft it into blocks that can be used as fuel.")
            kelpSoupEnabled = config.getBoolean("Kelp Soup", name, kelpSoupEnabled,
                    "Whether kelp can be cooked into delicious soup")
        }

    }

    object Seagrass : FloraConfig(true, 1.0f, 4, 64) {
        override val name = "Seagrass"
    }

    object Cornflower : FloraConfig(true, 1/8f, 4, 64) {
        override val name = "Cornflower"
    }

    object LilyOfTheValley : FloraConfig(true, 1/8f, 4, 64) {
        override val name = "Lily of the Valley"
    }

    object WitherRose : FloraConfig(true, 0.5f, 8, 64) {
        override val name = "Wither Rose"

        var causesWither = true

        override fun init(config: Configuration) {
            super.init(config)

            causesWither = config.getBoolean("Wither roses cause wither", name, causesWither,
                    "Should wither roses apply wither to touching entities?")
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
            BackportedFlora.logger.error("Error: failed to load config!")
        } finally {
            if (config.hasChanged()) {
                config.save()
            }
        }
    }
}
