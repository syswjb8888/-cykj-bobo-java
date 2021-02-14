package com.cykj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 启动程序
 *
 * @author cykj
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
// public class PosMachineApplication extends SpringBootServletInitializer
public class PosMachineApplication
{
    public static void main(String[] args)
    {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(PosMachineApplication.class, args);
        System.out.println("POS终端管理系统启动成功");
    }
}
