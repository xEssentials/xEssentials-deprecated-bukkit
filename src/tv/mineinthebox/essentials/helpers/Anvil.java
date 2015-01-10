package tv.mineinthebox.essentials.helpers;

import org.bukkit.block.Block;


public class Anvil {
	
	private final Block block;
	
	public Anvil(Block block) {
		this.block = block;
	}
	
	public enum AnvilDamageType {
		NON_DAMAGED(),
		SLIGHT_DAMAGED(),
		HEAVY_DAMAGED();
	}
	
	@SuppressWarnings("deprecation")
	public AnvilDamageType getAnvilDamageType() {
		byte data = block.getData();
		switch(data) {
			case 0: return AnvilDamageType.NON_DAMAGED;
			case 1: return AnvilDamageType.NON_DAMAGED;
			case 2: return AnvilDamageType.NON_DAMAGED;
			case 3: return AnvilDamageType.NON_DAMAGED;
			case 4: return AnvilDamageType.SLIGHT_DAMAGED;
			case 5: return AnvilDamageType.SLIGHT_DAMAGED;
			case 6: return AnvilDamageType.SLIGHT_DAMAGED;
			case 7: return AnvilDamageType.SLIGHT_DAMAGED;
			case 8: return AnvilDamageType.HEAVY_DAMAGED;
			case 9: return AnvilDamageType.HEAVY_DAMAGED;
			case 10: return AnvilDamageType.HEAVY_DAMAGED;
			case 11: return AnvilDamageType.HEAVY_DAMAGED;
			default: return AnvilDamageType.NON_DAMAGED;
		}
	}
	
	@SuppressWarnings("deprecation")
	public void setAnvilDamageType(AnvilDamageType type) {
		byte data = block.getData();
		switch(data) {
		case 0:
			if(type == AnvilDamageType.NON_DAMAGED) {
				block.setData((byte)0);
			} else if(type == AnvilDamageType.SLIGHT_DAMAGED) {
				block.setData((byte)4);
			} else if(type == AnvilDamageType.HEAVY_DAMAGED) {
				block.setData((byte)8);
			}
			break;
		case 1:
			if(type == AnvilDamageType.NON_DAMAGED) {
				block.setData((byte)1);
			} else if(type == AnvilDamageType.SLIGHT_DAMAGED) {
				block.setData((byte)5);
			} else if(type == AnvilDamageType.HEAVY_DAMAGED) {
				block.setData((byte)9);
			}
			break;
		case 2:
			if(type == AnvilDamageType.NON_DAMAGED) {
				block.setData((byte)2);
			} else if(type == AnvilDamageType.SLIGHT_DAMAGED) {
				block.setData((byte)7);
			} else if(type == AnvilDamageType.HEAVY_DAMAGED) {
				block.setData((byte)11);
			}
			break;
		case 3:
			if(type == AnvilDamageType.NON_DAMAGED) {
				block.setData((byte)3);
			} else if(type == AnvilDamageType.SLIGHT_DAMAGED) {
				block.setData((byte)6);
			} else if(type == AnvilDamageType.HEAVY_DAMAGED) {
				block.setData((byte)10);
			}
			break;
		case 4:
			if(type == AnvilDamageType.NON_DAMAGED) {
				block.setData((byte)0);
			} else if(type == AnvilDamageType.SLIGHT_DAMAGED) {
				block.setData((byte)4);
			} else if(type == AnvilDamageType.HEAVY_DAMAGED) {
				block.setData((byte)8);
			}
			break;
		case 5:
			if(type == AnvilDamageType.NON_DAMAGED) {
				block.setData((byte)1);
			} else if(type == AnvilDamageType.SLIGHT_DAMAGED) {
				block.setData((byte)5);
			} else if(type == AnvilDamageType.HEAVY_DAMAGED) {
				block.setData((byte)9);
			}
			break;
		case 6:
			if(type == AnvilDamageType.NON_DAMAGED) {
				block.setData((byte)3);
			} else if(type == AnvilDamageType.SLIGHT_DAMAGED) {
				block.setData((byte)6);
			} else if(type == AnvilDamageType.HEAVY_DAMAGED) {
				block.setData((byte)10);
			}
			break;
		case 7:
			if(type == AnvilDamageType.NON_DAMAGED) {
				block.setData((byte)2);
			} else if(type == AnvilDamageType.SLIGHT_DAMAGED) {
				block.setData((byte)7);
			} else if(type == AnvilDamageType.HEAVY_DAMAGED) {
				block.setData((byte)11);
			}
			break;
		case 8:
			if(type == AnvilDamageType.NON_DAMAGED) {
				block.setData((byte)0);
			} else if(type == AnvilDamageType.SLIGHT_DAMAGED) {
				block.setData((byte)4);
			} else if(type == AnvilDamageType.HEAVY_DAMAGED) {
				block.setData((byte)8);
			}
			break;
		case 9:
			if(type == AnvilDamageType.NON_DAMAGED) {
				block.setData((byte)1);
			} else if(type == AnvilDamageType.SLIGHT_DAMAGED) {
				block.setData((byte)5);
			} else if(type == AnvilDamageType.HEAVY_DAMAGED) {
				block.setData((byte)9);
			}
			break;
		case 10:
			if(type == AnvilDamageType.NON_DAMAGED) {
				block.setData((byte)3);
			} else if(type == AnvilDamageType.SLIGHT_DAMAGED) {
				block.setData((byte)6);
			} else if(type == AnvilDamageType.HEAVY_DAMAGED) {
				block.setData((byte)10);
			}
			break;
		case 11:
			if(type == AnvilDamageType.NON_DAMAGED) {
				block.setData((byte)2);
			} else if(type == AnvilDamageType.SLIGHT_DAMAGED) {
				block.setData((byte)7);
			} else if(type == AnvilDamageType.HEAVY_DAMAGED) {
				block.setData((byte)11);
			}
			break;
		}
	}
}
