package com.natamus.configurabledespawntimer.mixin;

import com.natamus.collective.functions.TaskFunctions;
import com.natamus.configurabledespawntimer.config.ConfigHandler;
import com.natamus.configurabledespawntimer.util.Reference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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
			if (!experienceOrb.entityTags().contains(Reference.MOD_ID + ".set")) {
				this.age = (ConfigHandler.globalExperienceOrbDespawnTimeInTicks * -1) + LIFETIME;
				experienceOrb.addTag(Reference.MOD_ID + ".set");
			}
		}, true);
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
}
