package azmalent.backportedflora

import net.minecraftforge.common.config.Configuration

object ModConfig {
    object Seaweed {
        private const val NAME = "Seaweed"
        var seagrassEnabled = true
        var kelpEnabled = true
        var kelpGrowthEnabled = true
        var driedKelpEnabled = true
        var kelpSoupEnabled = true

        fun init(cfg: Configuration) {
            cfg.addCustomCategoryComment(NAME, "Features related to kelp.")

            seagrassEnabled = cfg.getBoolean("Seagrass", NAME, seagrassEnabled,
                    "Whether seagrass is registered")
            kelpEnabled = cfg.getBoolean("Kelp", NAME, kelpEnabled,
                    "Whether kelp is registered")
            kelpGrowthEnabled = cfg.getBoolean("Kelp grows", NAME, kelpGrowthEnabled,
                    "Should kelp grow on its own?")
            driedKelpEnabled = cfg.getBoolean("Dried kelp", NAME, driedKelpEnabled,
                    "Whether dried kelp is enabled.\n" +
                            "Dried kelp is a food item that can be eaten quickly.\n" +
                            "You can also craft it into blocks that can be used as fuel.")
            kelpSoupEnabled = cfg.getBoolean("Kelp soup", NAME, kelpSoupEnabled,
                    "Whether kelp can be cooked into delicious soup")

        }
    }

    object Flowers {
        private const val NAME = "Flowers"
        var cornflowerEnabled = true
        var lilyOfTheValleyEnabled = true
        var witherRoseEnabled = true
        var witherRoseCausesWither = true

        fun init(cfg: Configuration) {
            cfg.addCustomCategoryComment(NAME, "Features related to flowers added by this mod.")

            cornflowerEnabled = cfg.getBoolean("Cornflower", NAME, cornflowerEnabled,
                    "Whether cornflowers are enabled.")

            lilyOfTheValleyEnabled = cfg.getBoolean("Lily of the valley", NAME, lilyOfTheValleyEnabled,
                    "Whether lilies of the valley are enabled.")

            witherRoseEnabled = cfg.getBoolean("Wither rose", NAME, witherRoseEnabled,
                    "Whether wither roses are enabled.\n" +
                            "Unlike wither roses in vanilla 1.14, they will spawn on soul sand in the Nether.")
            witherRoseCausesWither = cfg.getBoolean("Wither roses cause wither", NAME, witherRoseCausesWither,
                    "Should wither roses apply wither?")
        }
    }

    fun sync() {
        val config = BackportedFlora.config

        try {
            config.load()
            Seaweed.init(config)
            Flowers.init(config)
        } catch(e: Exception) {
            BackportedFlora.logger.error("Error: failed to load config!")
        } finally {
            if (config.hasChanged()) {
                config.save()
            }
        }
    }
}
