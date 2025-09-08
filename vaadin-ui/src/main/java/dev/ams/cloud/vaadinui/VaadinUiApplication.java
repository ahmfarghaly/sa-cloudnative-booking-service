package dev.ams.cloud.vaadinui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.page.AppShellConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Theme("my-theme")
@ComponentScan(basePackages = {
    "dev.ams.cloud.vaadinui.views",
    "dev.ams.cloud.vaadinui.service"
})
public class VaadinUiApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(VaadinUiApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
