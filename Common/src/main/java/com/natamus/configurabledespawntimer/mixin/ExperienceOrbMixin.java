package com.natamus.configurabledespawntimer.mixin;

import com.natamus.collective.functions.TaskFunctions;
import com.natamus.configurabledespawntimer.config.ConfigHandler;
import com.natamus.configurabledespawntimer.util.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ExperienceOrb.class, priority = 1001)
public abstract class ExperienceOrbMixin {
	@Shadow private static @Final int LIFETIME;
	@Shadow private int age;

	@Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V", at = @At(value = "TAIL"))
	public void ExperienceOrb(EntityType<?> entityType, Level level, CallbackInfo ci) {
		TaskFunctions.enqueueImmediateTask(level, () -> {
			ExperienceOrb experienceOrb = (ExperienceOrb) (Object) this;
			if (!experienceOrb.getTags().contains(Reference.MOD_ID + ".set")) {
				this.age = (ConfigHandler.globalExperienceOrbDespawnTimeInTicks * -1) + LIFETIME;
				experienceOrb.addTag(Reference.MOD_ID + ".set");
			}
		}, true);
    }

	@Inject(method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", at = @At(value = "TAIL"))
    public void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
		compoundTag.putInt("ActualAge", this.age);
    }

	@Inject(method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", at = @At(value = "TAIL"))
    public void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
		if (compoundTag.contains("ActualAge")) {
			this.age = compoundTag.getInt("ActualAge");
		}
    }
}
