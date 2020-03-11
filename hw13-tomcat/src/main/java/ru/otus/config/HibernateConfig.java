package ru.otus.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import ru.otus.cache.api.model.AddressDataSet;
import ru.otus.cache.api.model.PhoneDataSet;
import ru.otus.cache.api.model.User;
import ru.otus.cache.hibernate.HibernateUtils;

@Configuration
@ImportResource({"classpath:WEB-INF/config/hibernate.cfg.xml"})
public class HibernateConfig {

    private static final String HIBERNATE_CONFIG = "WEB-INF/config/hibernate.cfg.xml";

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory(HIBERNATE_CONFIG, User.class, PhoneDataSet.class, AddressDataSet.class);
    }
}
