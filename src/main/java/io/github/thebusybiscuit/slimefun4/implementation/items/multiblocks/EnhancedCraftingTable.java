package io.github.thebusybiscuit.slimefun4.implementation.items.multiblocks;

import java.util.List;
import java.util.UUID;

import io.github.thebusybiscuit.slimefun4.utils.PatternUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.cscorelib2.inventory.ItemUtils;
import io.github.thebusybiscuit.cscorelib2.item.CustomItem;
import io.github.thebusybiscuit.slimefun4.implementation.listeners.BackpackListener;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunBackpack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.multiblocks.MultiBlockMachine;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.api.PlayerProfile;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class EnhancedCraftingTable extends MultiBlockMachine {

	public EnhancedCraftingTable() {
		super(
				Categories.MACHINES_1, 
				(SlimefunItemStack) SlimefunItems.ENHANCED_CRAFTING_TABLE, 
				new ItemStack[] {null, null, null, null, new ItemStack(Material.CRAFTING_TABLE), null, null, new ItemStack(Material.DISPENSER), null}, 
				new ItemStack[0], 
				BlockFace.SELF
		);
	}
	
	@Override
	public void onInteract(Player p, Block b) {
		// Objects dispBlock and disp have been split up, in order to add the output chest functionallity, which is the only functionallity
		// that is dependant on the dispenser's block methods.
		// the Dispenser disp still remains the same though, and as such doesn't break any existing code which involves said object.
		Block dispBlock = b.getRelative(BlockFace.DOWN);
		Dispenser disp = (Dispenser) dispBlock.getState();
		Inventory inv = disp.getInventory();

		List<ItemStack[]> inputs = RecipeType.getRecipeInputList(this);

		for (int i = 0; i < inputs.size(); i++) {
			if (isCraftable(inv, inputs.get(i))) {
				ItemStack adding = RecipeType.getRecipeOutputList(this, inputs.get(i)).clone();
				if (Slimefun.hasUnlocked(p, adding, true)) {
					Inventory inv2 = Bukkit.createInventory(null, 9, "test");
					
					for (int j = 0; j < inv.getContents().length; j++) {
						inv2.setItem(j, inv.getContents()[j] != null ? (inv.getContents()[j].getAmount() > 1 ? new CustomItem(inv.getContents()[j], inv.getContents()[j].getAmount() - 1): null): null);
					}

					Inventory outputInv = findOutputInventory(adding, dispBlock, inv, inv2);

					if (outputInv != null) {
						SlimefunItem sfItem = SlimefunItem.getByItem(adding);
						
						if (sfItem instanceof SlimefunBackpack) {
							ItemStack backpack = null;

							for (int j = 0; j < 9; j++) {
								if (inv.getContents()[j] != null && inv.getContents()[j].getType() != Material.AIR && SlimefunItem.getByItem(inv.getContents()[j]) instanceof SlimefunBackpack) {
									backpack = inv.getContents()[j];
									break;
								}
							}
							
							String id = "";
							int size = ((SlimefunBackpack) sfItem).getSize();

							if (backpack != null) {
								for (String line : backpack.getItemMeta().getLore()) {
									if (line.startsWith(ChatColor.translateAlternateColorCodes('&', "&7ID: ")) && line.contains("#")) {
										id = line.replace(ChatColor.translateAlternateColorCodes('&', "&7ID: "), "");
										String[] idSplit = PatternUtils.HASH.split(id);
										PlayerProfile.fromUUID(UUID.fromString(idSplit[0])).getBackpack(Integer.parseInt(idSplit[1])).setSize(size);
										break;
									}
								}
							}

							if (id.equals("")) {
								for (int line = 0; line < adding.getItemMeta().getLore().size(); line++) {
									if (adding.getItemMeta().getLore().get(line).equals(ChatColor.translateAlternateColorCodes('&', "&7ID: <ID>"))) {
										int backpackID = PlayerProfile.get(p).createBackpack(size).getID();

                                        BackpackListener.setBackpackId(p, adding, line, backpackID);
                                    }
								}
							}
							else {
								for (int line = 0; line < adding.getItemMeta().getLore().size(); line++) {
									if (adding.getItemMeta().getLore().get(line).equals(ChatColor.translateAlternateColorCodes('&', "&7ID: <ID>"))) {
										ItemMeta im = adding.getItemMeta();
										List<String> lore = im.getLore();
										lore.set(line, lore.get(line).replace("<ID>", id));
										im.setLore(lore);
										adding.setItemMeta(im);
										break;
									}
								}
							}
						}


						for (int j = 0; j < 9; j++) {
							ItemStack item = inv.getContents()[j];
							
							if (item != null && item.getType() != Material.AIR) {
								ItemUtils.consumeItem(item, true);
							}
						}
						p.getWorld().playSound(b.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 1);

						outputInv.addItem(adding);

					}
					else SlimefunPlugin.getLocal().sendMessage(p, "machines.full-inventory", true);
				}
				
				return;
			}
		}
		SlimefunPlugin.getLocal().sendMessage(p, "machines.pattern-not-found", true);
	}

	private boolean isCraftable(Inventory inv, ItemStack[] recipe) {
		for (int j = 0; j < inv.getContents().length; j++) {
			if (!SlimefunManager.isItemSimilar(inv.getContents()[j], recipe[j], true)) {
				if (SlimefunItem.getByItem(recipe[j]) instanceof SlimefunBackpack) {
					if (!SlimefunManager.isItemSimilar(inv.getContents()[j], recipe[j], false)) {
						return false;
					}
				}
				else {
					return false;
				}
			}
		}
		
		return true;
	}

}
