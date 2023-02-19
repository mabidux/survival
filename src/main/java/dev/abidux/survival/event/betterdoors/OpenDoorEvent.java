package dev.abidux.survival.event.betterdoors;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OpenDoorEvent implements Listener {

    @EventHandler
    void open(PlayerInteractEvent event) {
        if (event.isCancelled() || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block block = event.getClickedBlock();
        if (block.getBlockData() instanceof Door door) {
            Door.Hinge hinge = door.getHinge();
            BlockFace face = door.getFacing();

            int modX = face.getModZ();
            int modZ = face.getModX();
            if (hinge == Door.Hinge.RIGHT) modZ *= -1;
            else modX *= -1;

            Block neighbor = block.getLocation().clone().add(modX, 0, modZ).getBlock();
            open(neighbor, door);
        }
    }

    private void open(Block block, Door openedDoor) {
        if (block.getBlockData() instanceof Door door) {
            if (openedDoor.getHinge() == door.getHinge()) return;
            door.setOpen(!openedDoor.isOpen());
            block.setBlockData(door);
        }
    }

}