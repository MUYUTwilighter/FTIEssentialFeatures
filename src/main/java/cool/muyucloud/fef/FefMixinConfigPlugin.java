package cool.muyucloud.fef;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FefMixinConfigPlugin implements IMixinConfigPlugin {
    private final Map<String, Boolean> targets;

    public FefMixinConfigPlugin() {
        List<String> targetPackages = List.of(
            "dev.technici4n.moderndynamics",
            "io.github.mattidragon.extendeddrawers",
            "ins.tlr"
        );
        List<String> targetClasses = List.of(
            "dev.technici4n.moderndynamics.ModernDynamics",
            "io.github.mattidragon.extendeddrawers.ExtendedDrawers",
            "ins.tlr.TLR"
        );
        Map.Entry<String, Boolean>[] entries = new Map.Entry[targetPackages.size()];
        for (int i = 0; i < targetPackages.size(); i++) {
            String targetClass = targetClasses.get(i);
            Map.Entry<String, Boolean> entry;
            try {
                Class.forName(targetClass, false, getClass().getClassLoader());
                entry = Map.entry(targetPackages.get(i), true);
            } catch (Throwable e) {
                entry = Map.entry(targetPackages.get(i), false);
            }
            entries[i] = entry;
        }
        targets = Map.ofEntries(entries);
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        for (Map.Entry<String, Boolean> entry : targets.entrySet()) {
            if (targetClassName.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return true;
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
