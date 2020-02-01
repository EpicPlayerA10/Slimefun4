package me.mrCookieSlime.Slimefun.Lists;

import org.bukkit.Material;

import io.github.thebusybiscuit.cscorelib2.item.CustomItem;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.LockedCategory;
import me.mrCookieSlime.Slimefun.Objects.SeasonalCategory;

/**
 * Built-in categories.
 * 
 * @author TheBusyBiscuit
 * @since 4.0
 * @see Category
 */
public final class Categories {
	
	private Categories() {}
	
	public static final Category WEAPONS = new Category(new CustomItem(SlimefunItems.BLADE_OF_VAMPIRES, "&7Weapons"), 1);
	public static final Category TOOLS = new Category(new CustomItem(SlimefunItems.AUTO_SMELT_PICKAXE, "&7Tools"), 1);
	public static final Category PORTABLE = new Category(new CustomItem(SlimefunItems.BACKPACK_MEDIUM, "&7Items"), 1);
	public static final Category FOOD = new Category(new CustomItem(SlimefunItems.FORTUNE_COOKIE, "&7Food"), 2);
	public static final Category MACHINES_1 = new Category(new CustomItem(Material.SMITHING_TABLE, "&7Basic Machines"), 1);
	public static final LockedCategory ELECTRICITY = new LockedCategory(new CustomItem(SlimefunItems.NUCLEAR_REACTOR, "&bEnergy and Electricity"), 4, MACHINES_1);
	public static final LockedCategory GPS = new LockedCategory(new CustomItem(SlimefunItems.GPS_TRANSMITTER, "&bGPS-based Machines"), 4, MACHINES_1);
	public static final Category ARMOR = new Category(new CustomItem(SlimefunItems.DAMASCUS_STEEL_CHESTPLATE, "&7Armor"), 2);
	public static final Category LUMPS_AND_MAGIC = new Category(new CustomItem(SlimefunItems.RUNE_ENDER, "&7Magical Items"), 2);
	public static final Category MAGIC = new Category(new CustomItem(SlimefunItems.INFUSED_ELYTRA, "&7Magical Gadgets"), 3);
	public static final Category MISC = new Category(new CustomItem(SlimefunItems.CAN, "&7Miscellaneous"), 2);
	public static final Category TECH = new Category(new CustomItem(SlimefunItems.STEEL_JETPACK, "&7Technical Gadgets"), 3);
	public static final Category RESOURCES = new Category(new CustomItem(SlimefunItems.SYNTHETIC_SAPPHIRE, "&7Resources"), 1);
	public static final Category CARGO = new LockedCategory(new CustomItem(SlimefunItems.CARGO_MANAGER, "&cCargo Management"), 4, MACHINES_1);
	public static final Category TECH_MISC = new Category(new CustomItem(SlimefunItems.HEATING_COIL, "&7Technical Components"), 2);
	public static final Category MAGIC_ARMOR = new Category(new CustomItem(SlimefunItems.ENDER_HELMET, "&7Magical Armor"), 2);
	public static final Category TALISMANS_1 = new Category(new CustomItem(SlimefunItems.TALISMAN, "&7Talismans - &aTier I"), 2);
	public static final LockedCategory TALISMANS_2 = new LockedCategory(new CustomItem(SlimefunItems.ENDER_TALISMAN, "&7Talismans - &aTier II"), 3, TALISMANS_1);
	
	// Seasonal Categories
	public static final SeasonalCategory CHRISTMAS = new SeasonalCategory(12, 1, new CustomItem(Material.NETHER_STAR, ChatUtils.christmas("Christmas") + " &7(December)"));
	public static final SeasonalCategory VALENTINES_DAY = new SeasonalCategory(2, 2, new CustomItem(Material.POPPY, "&dValentine's Day" + " &7(February)"));
	public static final SeasonalCategory EASTER = new SeasonalCategory(4, 2, new CustomItem(Material.EGG, "&6Easter" + " &7(April)"));
	public static final SeasonalCategory BIRTHDAY = new SeasonalCategory(10, 1, new CustomItem(Material.FIREWORK_ROCKET, "&a&lTheBusyBiscuit's Birthday &7(26th October)"));
	
}
