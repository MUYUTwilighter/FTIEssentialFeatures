package cool.muyucloud.fef.refection;

import cool.muyucloud.fef.FtiEssentialFeatures;
import ins.tlr.network.TNet;

import java.lang.reflect.Field;

public class TNetReflection {
    private static final Class<?> TNET_CLASS;

    static {
        Class<?> tNetClass;
        try {
            tNetClass = Class.forName("ins.tlr.network.TNet");
        } catch (ClassNotFoundException e) {
            tNetClass = null;
        }
        TNET_CLASS = tNetClass;
    }

    private final Object instance;

    public TNetReflection(Object tNet) {
        if (TNET_CLASS == null) {
            throw new IllegalStateException("Class ins.tlr.network.TNet is not found, probably TLR is not present");
        }
        if (TNET_CLASS.isAssignableFrom(tNet.getClass())) {
            this.instance = tNet;
        } else {
            throw new IllegalArgumentException("Class %s is not an instance of %s".formatted(tNet.getClass(), TNET_CLASS));
        }
    }

    public static TNetReflection of(TNet tNet) {
        return new TNetReflection(tNet);
    }

    public int getValue() {
        try {
            Field field = instance.getClass().getDeclaredField("level");
            field.setAccessible(true);
            return (int) field.get(instance);
        } catch (Throwable e) {
            FtiEssentialFeatures.LOGGER.error(e);
            return 0;
        }
    }
}
