package org.heg.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "mail")
public interface MailServerConfig {

    String Hostname();

    int Port();

    String From();
}
