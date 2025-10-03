package com.natamus.configurabledespawntimer.mixin;

import com.natamus.collective.functions.TaskFunctions;
import com.natamus.configurabledespawntimer.config.ConfigHandler;
import com.natamus.configurabledespawntimer.util.Reference;
import com.natamus.configurabledespawntimer.util.Util;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemEntity.class, priority = 1001)
public abstract class ItemEntityMixin {
	@Shadow private static @Final int LIFETIME;
	@Shadow private static @Final int INFINITE_LIFETIME;
	@Shadow private int age;

	@Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V", at = @At(value = "TAIL"))
	public void ItemEntity(EntityType<?> entityType, Level level, CallbackInfo ci) {
		TaskFunctions.enqueueImmediateTask(level, () -> {
			ItemEntity itemEntity = (ItemEntity)(Object)this;
			if (shouldSetAge(itemEntity)) {
				this.age = getAgeToSet(itemEntity);
			}
		}, true);
	}

	@Inject(method = "<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;DDD)V", at = @At(value = "TAIL"))
	public void ItemEntity(Level level, double x, double y, double z, ItemStack itemStack, double xd, double yd, double zd, CallbackInfo ci) {
		TaskFunctions.enqueueImmediateTask(level, () -> {
			ItemEntity itemEntity = (ItemEntity)(Object)this;
			if (shouldSetAge(itemEntity)) {
				this.age = getAgeToSet(itemEntity);
			}
		}, true);
	}

	@Inject(method = "tick()V", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/item/ItemEntity;updateInWaterStateAndDoFluidPushing()Z"))
	public void tick(CallbackInfo ci) {
		if (this.age == INFINITE_LIFETIME) {
			if (!((ItemEntity)(Object)this).getTags().contains(Reference.MOD_ID + ".infinite_lifetime")) {
				++this.age;
			}
		}
	}

	@Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
	public void addAdditionalSaveData(ValueOutput valueOutput, CallbackInfo ci) {
		valueOutput.putInt("ActualAge", this.age);
	}

	@Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
	public void readAdditionalSaveData(ValueInput valueInput, CallbackInfo ci) {
		int actualAge = valueInput.getIntOr("ActualAge", Integer.MIN_VALUE);
		if (actualAge != Integer.MIN_VALUE) {
			this.age = actualAge;
		}
	}

	@Unique
	private static boolean shouldSetAge(ItemEntity itemEntity) {
		if (itemEntity.getAge() == 5999 && itemEntity.hasPickUpDelay()) {
			return false;
		}

		if (itemEntity.getTags().contains(Reference.MOD_ID + ".set")) {
			return false;
		}
		itemEntity.addTag(Reference.MOD_ID + ".set");

		if (itemEntity.getAge() == INFINITE_LIFETIME) {
			itemEntity.addTag(Reference.MOD_ID + ".infinite_lifetime");
			return false;
		}

		return true;
	}

	@Unique
	private static int getAgeToSet(ItemEntity itemEntity) {
		if (ConfigHandler.preventDespawnForPlayerItems) {
			if (itemEntity.getTags().contains(Reference.MOD_ID + ".player_drop")) {
				itemEntity.addTag(Reference.MOD_ID + ".infinite_lifetime");
				return INFINITE_LIFETIME;
			}
		}

		Item item = itemEntity.getItem().getItem();
		if (Util.itemSpecificDespawnTime.containsKey(item)) {
			int despawnTime = Util.itemSpecificDespawnTime.get(item);
			if (despawnTime == 0) {
				itemEntity.addTag(Reference.MOD_ID + ".infinite_lifetime");
				return INFINITE_LIFETIME;
			}
			return (despawnTime * -1) + LIFETIME;
		}

		return (ConfigHandler.globalItemDespawnTimeInTicks * -1) + LIFETIME;
	}
}
