package io.student.rcc.config;

public interface Config {

    static Config getInstance() {
        return LocalConfig.INSTANCE;
    }
    String frontUrl();
    String spendJdbcUrl();
    String dbUsername();
    String dbPassword();

}
