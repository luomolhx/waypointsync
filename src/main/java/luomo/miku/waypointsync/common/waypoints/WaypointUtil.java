package luomo.miku.waypointsync.common.waypoints;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import journeymap.client.model.Waypoint;
import journeymap.client.waypoint.WaypointStore;

public class WaypointUtil {

    public static Collection<Waypoint> getAllWaypoints() {
        return WaypointStore.instance()
            .getAll();
    }

    public static List<Waypoint> getWaypointsByDimension(int dimension) {
        Collection<Waypoint> waypoints = getAllWaypoints();
        return waypoints.stream()
            .filter(
                waypoint -> waypoint.getDimensions()
                    .contains(dimension))
            .collect(Collectors.toList());
    }

    public static Waypoint getWaypointById(String id) {
        return getAllWaypoints().stream()
            .filter(
                waypoint -> waypoint.getId()
                    .equals(id))
            .findFirst()
            .orElse(null);
    }
}
