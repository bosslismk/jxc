package com.inventory.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class ConvertPwdPropertyConfigurer extends PropertyPlaceholderConfigurer {
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {

        if("jdbc.username".equals(propertyName)){
            return "root";
        }
        if("jdbc.password".equals(propertyName)){
            return "wuhuibuai321.";
        }
        if("jdbc.url".equals(propertyName)){
            return "jdbc:mysql://localhost:3306/inventory?useUnicode=true&amp;characterEncoding=UTF-8";
        }
        System.out.println("=================="+propertyName+":"+propertyValue);
        return propertyValue;
    }

}
