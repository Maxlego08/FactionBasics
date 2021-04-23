package fr.firepro.factionbasics.events;


import fr.firepro.factionbasics.FactionBasics;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MobDrop implements Listener {
    Random random = new Random();
    List<String> mobs = FactionBasics.getInstance().getConfig().getStringList("dropmodifier.mobs");

    @EventHandler
    public void mobEvent(EntityDeathEvent event) {


        for (String mob : mobs) {
            if (event.getEntity().getType().equals(EntityType.valueOf(mob))) {
                List<String> drops = FactionBasics.getInstance().getConfig().getStringList("dropmodifier." + mob);
                List<ItemStack> deject = new ArrayList<>();
                for (ItemStack drop : event.getDrops()) {
                    if (drops.contains(drop.getType().name())){
                        deject.add(drop);
                    }
                }
                for (ItemStack itemStack : deject) {
                    event.getDrops().remove(itemStack);
                }
            }
        }
    }
}
