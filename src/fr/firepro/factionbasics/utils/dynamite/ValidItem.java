package fr.firepro.factionbasics.utils.dynamite;

import org.bukkit.inventory.ItemStack;

public class ValidItem {
    private boolean b = false;

    public ValidItem(ItemStack item) {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            this.b = false;
            return;
        }
        this.b = true;
    }

    public boolean b() {
        return this.b;
    }
}
