package com.highd120.endstart.advancements;
import static net.minecraft.advancements.CriteriaTriggers.register;

public class EndStartAdvancements {
	public static TestAdvancementTrigger TEST;
	
	public static void init() {
		TEST = new TestAdvancementTrigger();
		register(TEST);
	}
}
