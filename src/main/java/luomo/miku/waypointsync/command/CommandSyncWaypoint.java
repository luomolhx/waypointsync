package luomo.miku.waypointsync.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import journeymap.client.model.Waypoint;
import luomo.miku.waypointsync.common.waypoints.WaypointUtil;

public class CommandSyncWaypoint extends CommandBase {

    @Override
    public String getCommandName() {
        return "syncWaypoint";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/syncWaypoint <页码> <player> <dim>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        int page = 1;
        int pageSize = 5;

        if (args.length > 0) {
            try {
                page = Integer.parseInt(args[0]);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                sender.addChatMessage(new ChatComponentText("§c页码参数无效，使用第一页"));
            }
        }

        Collection<Waypoint> allWaypoints = WaypointUtil.getAllWaypoints();
        List<Waypoint> waypoints = new ArrayList<Waypoint>(allWaypoints);
        int totalWaypoints = waypoints.size();
        int totalPages = (int) Math.ceil((double) totalWaypoints / pageSize);

        if (page > totalPages && totalPages > 0) page = totalPages;

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalWaypoints);

        if (totalWaypoints == 0) {
            sender.addChatMessage(new ChatComponentText("§7暂无路径点数据"));

        } else {
            for (Waypoint waypoint : allWaypoints) {
                sender.addChatMessage(
                    new ChatComponentText(
                        String.format(
                            "§a- §6%s §7(§b%d§7, §b%d§7, §b%d§7)",
                            waypoint.getName(),
                            waypoint.getX(),
                            waypoint.getY(),
                            waypoint.getZ())));
            }
        }

        if (totalPages > 1) {
            sender.addChatMessage(new ChatComponentText("§e使用 /syncWaypoint <页码> 查看其他页"));
        }

    }
}
