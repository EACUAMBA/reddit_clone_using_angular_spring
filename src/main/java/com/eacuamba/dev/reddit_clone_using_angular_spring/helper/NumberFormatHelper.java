/*
 * Copyright (c) 2022
 * EXI Limitada
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (edils) on 15/9/2022
 */

package com.eacuamba.dev.reddit_clone_using_angular_spring.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

@Component
@Slf4j
public final class NumberFormatHelper {
    private static final Locale locale = new Locale("pt", "MZ");
    private static final NumberFormat numberFormat = NumberFormat.getInstance(locale);
    private static final NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(locale);
    private static final NumberFormat integerInstance = NumberFormat.getIntegerInstance(locale);
    private static final NumberFormat percentInstance = NumberFormat.getPercentInstance(locale);

    /**
     * This method convert or format a BigDecimal value to a Currency value, using the locale pt_MZ
     * @param decimal the bigDecimal value to format
     * @return String the formatted string value
     * @author Edilson Alexandre Cuamba
     */
    public String BigDecimalToCurrencyString(BigDecimal decimal){
        if(Objects.isNull(decimal))
            return null;
        return currencyInstance.format(decimal);
    }

    /**
     * This method convert or format a BigDecimal value to a Currency value, using the passed locale as argument
     * @param decimal the bigDecimal value to format
     * @return String the formatted string value
     * @author Edilson Alexandre Cuamba
     */
    public String BigDecimalToCurrencyString(BigDecimal decimal, Locale locale){
        if(Objects.isNull(decimal))
            return null;
        return NumberFormat.getCurrencyInstance(locale).format(decimal);
    }

    public BigDecimal StringCurrencyToBigDecimal(String value){
        BigDecimal bigDecimal;
        try {
            bigDecimal =  BigDecimal.valueOf(currencyInstance.parse(value).doubleValue());
        } catch (ParseException e) {
            log.error("Error ao fazer parse do valor em string  para big decimal.", e);
            throw new RuntimeException(e);
        }
        return bigDecimal;
    }
}
