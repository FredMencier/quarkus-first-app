package org.heg.manager;

import io.quarkus.logging.Log;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.configuration.ConfigUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.heg.config.MailServerConfig;

import java.util.List;

@ApplicationScoped
public class StartUpManager {

    @Inject
    MailServerConfig mailServerConfig;

    @Startup
    public void init() {
        Log.info("Start Person Quarkus application");
        Log.info(List.of("Use Profile : " + ConfigUtils.getProfiles()));
        Log.info("Use Profile : " + LaunchMode.current().getProfileKey());
        Log.info("Mail server hostname : " + mailServerConfig.Hostname());
        Log.info("Mail server port : " + mailServerConfig.Port());
        Log.info("Mail server from : " + mailServerConfig.From());
    }

}
