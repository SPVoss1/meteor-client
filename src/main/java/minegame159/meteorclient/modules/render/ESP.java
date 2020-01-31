package minegame159.meteorclient.modules.render;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import minegame159.meteorclient.events.RenderEvent;
import minegame159.meteorclient.modules.Category;
import minegame159.meteorclient.modules.Module;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.builders.BoolSettingBuilder;
import minegame159.meteorclient.settings.builders.ColorSettingBuilder;
import minegame159.meteorclient.settings.builders.EnumSettingBuilder;
import minegame159.meteorclient.utils.Color;
import minegame159.meteorclient.utils.EntityUtils;
import minegame159.meteorclient.utils.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

public class ESP extends Module {
    public enum Mode {
        Lines,
        Sides,
        Both
    }

    private Setting<Mode> mode = addSetting(new EnumSettingBuilder<Mode>()
            .name("mode")
            .description("Rendering mode.")
            .defaultValue(Mode.Both)
            .build()
    );

    private Setting<Boolean> players = addSetting(new BoolSettingBuilder()
            .name("players")
            .description("See players.")
            .defaultValue(true)
            .build()
    );

    private Setting<Color> playersColor = addSetting(new ColorSettingBuilder()
            .name("players-color")
            .description("Players color.")
            .defaultValue(new Color(255, 255, 255, 255))
            .consumer((color1, color2) -> recalculateColor())
            .build()
    );

    private Setting<Boolean> animals = addSetting(new BoolSettingBuilder()
            .name("animals")
            .description("See animals.")
            .defaultValue(true)
            .build()
    );

    private Setting<Color> animalsColor = addSetting(new ColorSettingBuilder()
            .name("animals-color")
            .description("Animals color.")
            .defaultValue(new Color(145, 255, 145, 255))
            .consumer((color1, color2) -> recalculateColor())
            .build()
    );

    private Setting<Boolean> mobs = addSetting(new BoolSettingBuilder()
            .name("mobs")
            .description("See mobs.")
            .defaultValue(true)
            .build()
    );

    private Setting<Color> mobsColor = addSetting(new ColorSettingBuilder()
            .name("mobs-color")
            .description("Mobs color.")
            .defaultValue(new Color(255, 145, 145, 255))
            .consumer((color1, color2) -> recalculateColor())
            .build()
    );

    private Setting<Boolean> items = addSetting(new BoolSettingBuilder()
            .name("items")
            .description("See items.")
            .defaultValue(true)
            .build()
    );

    private Setting<Color> itemsColor = addSetting(new ColorSettingBuilder()
            .name("items-color")
            .description("Items color.")
            .defaultValue(new Color(145, 145, 145, 255))
            .consumer((color1, color2) -> recalculateColor())
            .build()
    );

    private Setting<Boolean> crystals = addSetting(new BoolSettingBuilder()
            .name("crystals")
            .description("See crystals.")
            .defaultValue(true)
            .build()
    );

    private Setting<Color> crystalsColor = addSetting(new ColorSettingBuilder()
            .name("crystals-color")
            .description("Crystals color.")
            .defaultValue(new Color(160, 40, 235, 255))
            .consumer((color1, color2) -> recalculateColor())
            .build()
    );

    private Setting<Boolean> vehicles = addSetting(new BoolSettingBuilder()
            .name("vehicles")
            .description("See vehicles.")
            .defaultValue(true)
            .build()
    );

    private Setting<Color> vehiclesColor = addSetting(new ColorSettingBuilder()
            .name("vehicles-color")
            .description("Vehicles color.")
            .defaultValue(new Color(100, 100, 100, 255))
            .consumer((color1, color2) -> recalculateColor())
            .build()
    );

    private Color playersLineColor = new Color();
    private Color playersSideColor = new Color();
    private Color animalsLineColor = new Color();
    private Color animalsSideColor = new Color();
    private Color mobsLineColor = new Color();
    private Color mobsSideColor = new Color();
    private Color itemsLineColor = new Color();
    private Color itemsSideColor = new Color();
    private Color crystalsLineColor = new Color();
    private Color crystalsSideColor = new Color();
    private Color vehiclesLineColor = new Color();
    private Color vehiclesSideColor = new Color();

    public ESP() {
        super(Category.Render, "esp", "See entities through walls.");
        recalculateColor();
    }

    private void recalculateColor() {
        // Players
        playersLineColor.set(playersColor.value());

        playersSideColor.set(playersLineColor);
        playersSideColor.a -= 225;
        if (playersSideColor.a < 0) playersSideColor.a = 0;

        // Animals
        animalsLineColor.set(animalsColor.value());

        animalsSideColor.set(animalsLineColor);
        animalsSideColor.a -= 225;
        if (animalsSideColor.a < 0) animalsSideColor.a = 0;

        // Mobs
        mobsLineColor.set(mobsColor.value());

        mobsSideColor.set(mobsLineColor);
        mobsSideColor.a -= 225;
        if (mobsSideColor.a < 0) mobsSideColor.a = 0;

        // Items
        itemsLineColor.set(itemsColor.value());

        itemsSideColor.set(itemsLineColor);
        itemsSideColor.a -= 225;
        if (itemsSideColor.a < 0) itemsSideColor.a = 0;

        // Crystals
        crystalsLineColor.set(crystalsColor.value());

        crystalsSideColor.set(crystalsLineColor);
        crystalsSideColor.a -= 225;
        if (crystalsSideColor.a < 0) crystalsSideColor.a = 0;

        // Vehicles
        vehiclesLineColor.set(vehiclesColor.value());

        vehiclesSideColor.set(vehiclesLineColor);
        vehiclesSideColor.a -= 225;
        if (vehiclesSideColor.a < 0) vehiclesSideColor.a = 0;
    }

    private void render(Entity entity, Color lineColor, Color sideColor) {
        switch (mode.value()) {
            case Lines: {
                Box box = entity.getBoundingBox();
                RenderUtils.boxEdges(box.x1, box.y1, box.z1, box.x2, box.y2, box.z2, lineColor);
                break;
            }
            case Sides: {
                Box box = entity.getBoundingBox();
                RenderUtils.boxSides(box.x1, box.y1, box.z1, box.x2, box.y2, box.z2, sideColor);
                break;
            }
            case Both: {
                Box box = entity.getBoundingBox();
                RenderUtils.boxEdges(box.x1, box.y1, box.z1, box.x2, box.y2, box.z2, lineColor);
                RenderUtils.boxSides(box.x1, box.y1, box.z1, box.x2, box.y2, box.z2, sideColor);
                break;
            }
        }
    }

    @EventHandler
    private Listener<RenderEvent> onRender = new Listener<>(event -> {
        for (Entity entity : mc.world.getEntities()) {
            if (players.value() && EntityUtils.isPlayer(entity) && entity != mc.player) render(entity, playersLineColor, playersSideColor);
            else if (animals.value() && EntityUtils.isAnimal(entity)) render(entity, animalsLineColor, animalsSideColor);
            else if (mobs.value() && EntityUtils.isMob(entity)) render(entity, mobsLineColor, mobsSideColor);
            else if (items.value() && EntityUtils.isItem(entity)) render(entity, itemsLineColor, itemsSideColor);
            else if (crystals.value() && EntityUtils.isCrystal(entity)) render(entity, crystalsLineColor, crystalsSideColor);
            else if (vehicles.value() && EntityUtils.isVehicle(entity)) render(entity, vehiclesLineColor, vehiclesSideColor);
        }
    });
}