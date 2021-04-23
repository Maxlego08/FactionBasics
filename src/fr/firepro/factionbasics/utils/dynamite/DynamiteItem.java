package fr.firepro.factionbasics.utils.dynamite;


import fr.firepro.factionbasics.FactionBasics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class DynamiteItem {
    FactionBasics main;
    private ItemStack dynamite;

    public DynamiteItem(FactionBasics main) {
        this.main =main;
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
        skullMeta.setOwner("MHF_TNT");
        skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("dynamite.name")));
        skullMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        skullMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        String loreS = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("dynamite.lore"));

        List<String> list = Arrays.asList(loreS.split("@"));

        skullMeta.setLore(list);
        skull.setItemMeta((ItemMeta)skullMeta);
        this.dynamite = skull;

    }

    public String name() {
        return this.dynamite.getItemMeta().getDisplayName();
    }

    public Material type() {
        return this.dynamite.getType();
    }

    public ItemStack item() {
        return this.dynamite;
    }
}
