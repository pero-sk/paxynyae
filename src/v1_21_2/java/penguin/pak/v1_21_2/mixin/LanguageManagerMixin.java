package penguin.pak.v1_21_2.mixin;

import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.resource.ResourceManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Mixin(LanguageManager.class)
public class LanguageManagerMixin {

    @Inject(method = "getAllLanguages", at = @At("RETURN"), cancellable = true)
    private void pak$addLanguage(
            CallbackInfoReturnable<SortedMap<String, LanguageDefinition>> cir
    ) {
        SortedMap<String, LanguageDefinition> languages =
                new TreeMap<>(cir.getReturnValue());

        languages.put(
                "pk_px",
                new LanguageDefinition(
                        "dahme paxə",
                        "Paxəɲæ",
                        false
                )
        );

        cir.setReturnValue(languages);
    }

    @Inject(
        method = "reload",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/resource/language/TranslationStorage;load(Lnet/minecraft/resource/ResourceManager;Ljava/util/List;Z)Lnet/minecraft/client/resource/language/TranslationStorage;",
            shift = At.Shift.BEFORE
        ),
        locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void pak$inject(ResourceManager manager, CallbackInfo ci, List<String> list) {

        LanguageManagerAccessor accessor = (LanguageManagerAccessor) (Object) this;

        if (!"pk_px".equals(accessor.getCurrentLanguageCode())) {
            return;
        }

        list.add("pk_px");
    }
}