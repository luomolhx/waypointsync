package luomo.miku.waypointsync;

import com.myname.mymodid.Tags;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import luomo.miku.waypointsync.data.DBLink;
import luomo.miku.waypointsync.loader.CommandLoader;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        MyMod.LOG.info(Config.greeting);
        MyMod.LOG.info("I am MyMod at version " + Tags.VERSION);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {}

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        CommandLoader.registerAllCommand(event);
        try {
            DBLink.connectDB();
        } catch (Exception e) {
            MyMod.LOG.error("MOD初始化过程中数据库连接失败，但MOD将继续加载", e);
        }
    }
}
