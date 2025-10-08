package luomo.miku.waypointsync.mixins;

import journeymap.client.model.Waypoint;
import journeymap.client.waypoint.WaypointStore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WaypointStore.class)
public interface WaypointStoreAccessor {

    @Invoker("writeToFile")
    boolean invokeWriteToFile(Waypoint waypoint);


}
