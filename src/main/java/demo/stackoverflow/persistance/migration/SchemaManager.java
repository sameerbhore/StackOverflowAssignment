package demo.stackoverflow.persistance.migration;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.flywaydb.core.internal.configuration.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Properties;

@Service
public class SchemaManager {

    private Properties flywayProperties = new Properties();
    private String flywayLocation;

    @Autowired
    public SchemaManager(@Value("${spring.flyway.locations}") String flywayLocation,
                         @Value("${spring.datasource.url}") String url,
                         @Value("${spring.datasource.username}") String username,
                         @Value("${spring.datasource.password}") String password) {
        this.flywayLocation = flywayLocation;
        this.flywayProperties.setProperty(ConfigUtils.URL, url);
        this.flywayProperties.setProperty(ConfigUtils.USER, username);
        this.flywayProperties.setProperty(ConfigUtils.PASSWORD, password);
    }

    private Flyway flywayForSchema(String schemaName) {
        this.flywayProperties.setProperty(ConfigUtils.SCHEMAS, schemaName);
        this.flywayProperties.setProperty("flyway.placeholders.schemaName", schemaName);
        this.flywayProperties.setProperty(ConfigUtils.BASELINE_ON_MIGRATE, "true");
        if(!StringUtils.isEmpty(this.flywayLocation)) {
            this.flywayProperties.setProperty(ConfigUtils.LOCATIONS, this.flywayLocation);
        }
        ClassicConfiguration flywayConfig = new ClassicConfiguration();
        flywayConfig.configure(flywayProperties);
        return new Flyway(flywayConfig);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void migrate(){
        flywayForSchema("stackoverflow").migrate();
    }
}
