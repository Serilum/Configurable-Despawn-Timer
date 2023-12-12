package com.natamus.configurabledespawntimer.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveItemEvents;
import com.natamus.configurabledespawntimer.config.ConfigHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
	public ItemEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Shadow private int age;

	@Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;updateInWaterStateAndDoFluidPushing()Z"), cancellable = true)
	public void ItemEntity_tick(CallbackInfo ci) {
		boolean processedImpulse = false;
		if (this.age >= ConfigHandler.despawnTimeInTicks) {
			processedImpulse = processImpulse();

			if (!this.level().isClientSide) {
				ItemEntity itemEntity = (ItemEntity)(Object)this;
				CollectiveItemEvents.ON_ITEM_EXPIRE.invoker().onItemExpire(itemEntity, itemEntity.getItem());

				this.discard();
			}
			ci.cancel();
		}

		if (!processedImpulse && this.age >= 6000 && ConfigHandler.despawnTimeInTicks >= 6000) {
			processImpulse();
			ci.cancel();
		}
	}

	private boolean processImpulse() {
		this.hasImpulse |= this.updateInWaterStateAndDoFluidPushing();
		if (!this.level().isClientSide) {
			double d = this.getDeltaMovement().subtract(this.getDeltaMovement()).lengthSqr();
			if (d > 0.01) {
				this.hasImpulse = true;
			}
		}
		return true;
	}
}
