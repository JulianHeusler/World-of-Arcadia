package arcadia;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ItemArcadiaPotions extends ItemFood {

	private int meta = 22; 
	//public static final String[] names = new String[] {"normal", "extended", "upgraded"};
	public static final String[] names = new String[] 
	{	"normalHaste", "extendedHaste", "upgradedHaste",
		"normalMiningFatigue", "extendedMiningFatigue", "upgradedMiningFatigue",
		"normalJumpBoost", "extendedJumpBoost", "upgradedJumpBoost",
		"normalNausea", "extendedNausea",
		"normaWaterBreathing", "extendedWaterBreathing",
		"normalBlindness", "extendedBlindness",
		"normalHunger", "extendedHunger", "upgradedHunger",
		"normalWither", "extendedWither", "upgradedWither",
		"normalHealth"
		};
	public static final int[] potionId = new int[] 
	{	Potion.digSpeed.id, Potion.digSpeed.id, Potion.digSpeed.id,
		Potion.digSlowdown.id, Potion.digSlowdown.id, Potion.digSlowdown.id,
		Potion.jump.id, Potion.jump.id, Potion.jump.id,
		Potion.confusion.id, Potion.confusion.id,
		Potion.waterBreathing.id, Potion.waterBreathing.id,
		Potion.blindness.id, Potion.blindness.id,
		Potion.hunger.id, Potion.hunger.id, Potion.hunger.id,
		Potion.wither.id, Potion.wither.id, Potion.wither.id,
		Potion.heal.id, Potion.regeneration.id};
	public static final int[] potionDuration = new int[] 
	{	180, 480, 90,
		180, 480, 90,
		180, 480, 90,
		180, 480,
		180, 480,
		180, 480,
		45, 120, 22,
		45, 120, 22
		};
    public static final int[] potionAmplifier = new int[] 
	{	0, 0, 1,
    	0, 0, 1,
    	0, 0, 1,
    	0, 0,
    	0, 0,
    	0, 0,
    	0, 0, 1,
    	0, 0, 1
    	};
    //private float potionEffectProbability = 1F;
    
    
    
	public ItemArcadiaPotions(int par1) {
		super(par1, 0, false);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setAlwaysEdible();
		this.setCreativeTab(arcadia.tabArcadiaPotions);
		
	}
	
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	      
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
    {
    	icons = new Icon[meta];
    	
    	for(int i = 0; i < icons.length; i++)
          {
        	  String name = "";
        	  switch(i)
              {
                     case  0:{ name = "Haste"; break; }
                     case  1:{ name = "Haste"; break; }
                     case  2:{ name = "Haste"; break; }
                     case  3:{ name = "MiningFatigue"; break; }
                     case  4:{ name = "MiningFatigue"; break; }
                     case  5:{ name = "MiningFatigue"; break; }
                     case  6:{ name = "JumpBoost"; break; }
                     case  7:{ name = "JumpBoost"; break; }
                     case  8:{ name = "JumpBoost"; break; }
                     case  9:{ name = "Nausea"; break; }
                     case 10:{ name = "Nausea"; break; }
                     case 11:{ name = "WaterBreathing"; break; }
                     case 12:{ name = "WaterBreathing"; break; }
                     case 13:{ name = "Blindness"; break; }
                     case 14:{ name = "Blindness"; break; }
                     case 15:{ name = "Hunger"; break; }
                     case 16:{ name = "Hunger"; break; }
                     case 17:{ name = "Hunger"; break; }
                     case 18:{ name = "Wither"; break; }
                     case 19:{ name = "Wither"; break; }
                     case 20:{ name = "Wither"; break; }
                     
                     default: name = "Broken";
              }
        	  icons[i] = par1IconRegister.registerIcon(arcadia.modid + ":" + (this.getUnlocalizedName().substring(5)) + name);
          }
    }
	
	public Icon getIconFromDamage(int par1)
	{
		return icons[par1];
	}
	
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
	    int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, meta);
	    return super.getUnlocalizedName() + "." + names[i];
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
	    for (int x = 0; x < meta; x++)
	    {
	        par3List.add(new ItemStack(this, 1, x));
	    }
	}
	
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, meta);
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }
        if (!par2World.isRemote && potionId[i] > 0 && par2World.rand.nextFloat() < 1F)
        {
            par3EntityPlayer.addPotionEffect(new PotionEffect(potionId[i], potionDuration[i] * 20, potionAmplifier[i]));
        }
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            if (par1ItemStack.stackSize <= 0)
            {
                return new ItemStack(Item.glassBottle);
            }

            par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
        }

        return par1ItemStack;
    }
	
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
		}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.drink;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean i)
	{
		if (stack.getItemDamage() == 0) {
			list.add("Haste (3:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A79+20% Digging Speed");
		}
		if (stack.getItemDamage() == 1) {
			list.add("Haste (8:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A79+20% Digging Speed");
		}
		if (stack.getItemDamage() == 2) {
			list.add("Haste (1:30)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A79+40% Digging Speed");
		}
		if (stack.getItemDamage() == 3) {
			list.add("Mining Fatigue (3:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A7c-20% Digging Speed");
		}
		if (stack.getItemDamage() == 4) {
			list.add("Mining Fatigue (8:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A7c-20% Digging Speed");
		}
		if (stack.getItemDamage() == 5) {
			list.add("Mining Fatigue (1:30)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A7c-40% Digging Speed");
		}
		if (stack.getItemDamage() == 6) {
			list.add("Jump Boost (3:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A79+1/2 Block Jump Hight");
		}
		if (stack.getItemDamage() == 7) {
			list.add("Jump Boost (8:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A79+1/2 Block Jump Hight");
		}
		if (stack.getItemDamage() == 8) {
			list.add("Jump Boost (1:30)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A79+1 Block Jump Hight");
		}
		if (stack.getItemDamage() == 9) {
			list.add("Nausea (3:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A7cYou feel sick!");
		}
		if (stack.getItemDamage() == 10) {
			list.add("Nausea (8:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A7cYou feel sick!");
		}
		if (stack.getItemDamage() == 11) {
			list.add("Water Breathing (3:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A79+100% Air Regeneration");
		}
		if (stack.getItemDamage() == 12) {
			list.add("Water Breathing (8:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A79+100% Air Regeneration");
		}
		if (stack.getItemDamage() == 13) {
			list.add("Blindness (3:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A7cYou can't see anymore!");
		}
		if (stack.getItemDamage() == 14) {
			list.add("Blindness (8:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A7cYou can't see anymore!");
		}
		if (stack.getItemDamage() == 15) {
			list.add("Hunger (0:45)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A7cYou feel hungry!");
		}
		if (stack.getItemDamage() == 16) {
			list.add("Hunger (2:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A7cYou feel hungry!");
		}
		if (stack.getItemDamage() == 17) {
			list.add("Hunger (0:22)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A7cYou feel hungrier!");
		}
		if (stack.getItemDamage() == 18) {
			list.add("Wither (0:45)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A7c-0.25 Hearts per Second");
		}
		if (stack.getItemDamage() == 19) {
			list.add("Wither (2:00)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A7c-0.25 Hearts per Second");
		}
		if (stack.getItemDamage() == 20) {
			list.add("Wither (0:22)");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			list.add("\u00A7c-0.5 Hearts per Second");
		}
	}
}
