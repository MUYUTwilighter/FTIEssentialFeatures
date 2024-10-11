package cool.muyucloud.fef.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import cool.muyucloud.fef.FtiEssentialFeatures;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class Config {
    private static final Gson GSON = new Gson();

    private Boolean modernDynamicsRenderItem = true;
    private Boolean extendedDrawerCompletelyOff = false;
    private Integer tlrMaxEnchantLevel = 10;

    public static Config load(Path path) {
        try (FileReader reader = new FileReader(path.toFile())) {
            return GSON.fromJson(reader, Config.class);
        } catch (Exception e) {
            FtiEssentialFeatures.LOGGER.warn("Failed to load config file: {}, using default config", path);
            FtiEssentialFeatures.LOGGER.warn(e.toString());
            return new Config();
        }
    }

    public Boolean shouldModernDynamicsRenderItem() {
        return modernDynamicsRenderItem;
    }

    public void setModernDynamicsRenderItem(Boolean modernDynamicsRenderItem) {
        this.modernDynamicsRenderItem = modernDynamicsRenderItem;
    }

    public void dump(Path path) {
        try (JsonWriter writer = new JsonWriter(new FileWriter(path.toFile()))) {
            writer.setIndent("    ");
            GSON.toJson(this, Config.class, writer);
        } catch (Exception e) {
            FtiEssentialFeatures.LOGGER.warn("Failed to dump config file");
            FtiEssentialFeatures.LOGGER.warn(e.toString());
        }
    }

    public Boolean getExtendedDrawerCompletelyOff() {
        return extendedDrawerCompletelyOff;
    }

    public void setExtendedDrawerCompletelyOff(Boolean extendedDrawerCompletelyOff) {
        this.extendedDrawerCompletelyOff = extendedDrawerCompletelyOff;
    }

    public Integer getTlrMaxEnchantLevel() {
        return tlrMaxEnchantLevel;
    }

    public void setTlrMaxEnchantLevel(Integer tlrMaxEnchantLevel) {
        this.tlrMaxEnchantLevel = tlrMaxEnchantLevel;
    }

    public boolean toggleModernDynamicsRenderItem() {
        this.setModernDynamicsRenderItem(!this.shouldModernDynamicsRenderItem());
        return this.shouldModernDynamicsRenderItem();
    }

    public boolean toggleExtendedDrawerCompletelyOff() {
        this.setExtendedDrawerCompletelyOff(!this.getExtendedDrawerCompletelyOff());
        return this.getExtendedDrawerCompletelyOff();
    }
}
