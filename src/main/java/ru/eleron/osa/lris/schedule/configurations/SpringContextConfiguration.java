package ru.eleron.osa.lris.schedule.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Application configuration class
 * @author Reyzor
 * @version 1.0
 * @since 05.05.2018
 * */

@Configuration
@Import({JpaConfigurations.class})
@ComponentScan(basePackages = {"ru.eleron.osa.lris.schedule"})
public class SpringContextConfiguration
{

}
