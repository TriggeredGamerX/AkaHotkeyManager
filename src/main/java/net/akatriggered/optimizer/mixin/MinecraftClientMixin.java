package akatriggered.optimizer.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import akatriggered.optimizer.AkaCrystalOptimizerMod;
import akatriggered.optimizer.command.EnableOptimizerCommand;

import static akatriggered.optimizer.AkaCrystalOptimizerMod.limitPackets;
import static akatriggered.optimizer.AkaCrystalOptimizerMod.mc;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(at = @At("HEAD"), method = "doItemUse", cancellable = true)
    private void onDoItemUse(CallbackInfo ci) {
        if (EnableOptimizerCommand.fastCrystal) {
            ItemStack mainHand = mc.player.getMainHandStack();
            if (mainHand.isOf(Items.END_CRYSTAL))
                //ensures only are packets are being sent through
                if (AkaCrystalOptimizerMod.hitCount != limitPackets())
                    ci.cancel();
        }
    }
}