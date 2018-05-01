package ru.eleron.osa.lris.schedule.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({JpaConfigurations.class})
@ComponentScan(basePackages = {"ru.eleron.osa.lris.schedule"})
public class SpringContextConfiguration {
}
