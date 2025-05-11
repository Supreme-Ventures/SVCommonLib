package not.savage.example;

import lombok.Getter;
import not.savage.example.menus.SimpleMenu;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

/**
 * Config-As-Code with hard references.
 * Automatically serializes the following out of the box, with custom adaptor support.
 * - Primitives, string, int, double, float, byte, etc (numbers perseveres precision)
 * - Lists, Maps, Sets
 * - {@link org.bukkit.Location}
 * - {@link org.bukkit.NamespacedKey}
 * - {@link org.bukkit.inventory.meta.trim.ArmorTrim}
 * - {@link org.bukkit.potion.PotionEffect}
 * - {@link not.savage.bukkit.messages.Message}
 * - {@link not.savage.bukkit.config.ConfigItem}
 * - {@link net.kyori.adventure.text.Component}
 * - {@link java.awt.Color}
 */
@ConfigSerializable @Getter
@SuppressWarnings("FieldsMayBeFinal") // Configurate requires this to be mutable
public class SimpleConfig {

    private String databaseUri = "jdbc:mysql://localhost:3306/mydb"; // becomes "database-uri: jdbc:mysql://localhost:3306/mydb" in the config
    private String username = "root";
    private String password = "password";

    /**
     * Creates a "Config SubSection" as we've included another @ConfigSerializable class.
     * database-uri: ""
     * username: ""
     * password: ""
     * menu-configuration:
     *     layout:
     *         title: "<red>Example Menu"
     *         rows: 4
    */
    private SimpleMenu.Config menuConfiguration = new SimpleMenu.Config();

}
