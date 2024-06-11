package org.crimsoncrips.pufferviner.mixins;

import com.ael.viner.Viner;
import com.ael.viner.util.MiningUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.puffish.skillsmod.api.SkillsAPI;
import net.puffish.skillsmod.experience.source.builtin.MineBlockExperienceSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;


@Mixin(MiningUtils.class)
public abstract class PufferfishIntegration {

    @Inject(method = "mineBlocks", at = @At(value = "INVOKE",target = "Lcom/ael/viner/util/MiningUtils;protectStorage(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"),remap = false)
    private static void yes(ServerPlayer player, List<BlockPos> blocksToMine, CallbackInfo ci){
        SkillsAPI.updateExperienceSources(player, experienceSource -> {
            if (experienceSource instanceof MineBlockExperienceSource mineBlockExperienceSource) {
                return mineBlockExperienceSource.getValue(player, (BlockState) blocksToMine, player.getItemInHand(InteractionHand.MAIN_HAND));
            }
            return 0;
        });
    }

}
