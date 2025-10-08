package luomo.miku.waypointsync.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import journeymap.client.model.Waypoint;
import journeymap.client.waypoint.WaypointStore;

@Mixin(WaypointStore.class)
public interface WaypointStoreAccessor {

    @Invoker("writeToFile")
    boolean invokeWriteToFile(Waypoint waypoint);

}
