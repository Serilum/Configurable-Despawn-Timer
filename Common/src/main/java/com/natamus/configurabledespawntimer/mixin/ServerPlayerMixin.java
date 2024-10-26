package com.natamus.configurabledespawntimer.mixin;

import com.natamus.configurabledespawntimer.util.Reference;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = ServerPlayer.class, priority = 1001)
public abstract class ServerPlayerMixin {
	@Inject(method = "createItemStackToDrop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;setPickUpDelay(I)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
	public void drop(ItemStack droppedItem, boolean dropAround, boolean includeThrowerName, CallbackInfoReturnable<ItemEntity> cir, double d, ItemEntity itemEntity) {
		itemEntity.addTag(Reference.MOD_ID + ".player_drop");
	}
}
