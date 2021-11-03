package com.airgap.airgapagent.configuration;

import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.BaseConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 11/3/2021.
 */
public class DateConverter extends BaseConverter<Date> {
    public static final String FORMAT = "yyyy:MM:dd-hh:mm:ss";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT);

    public DateConverter(String optionName) {
        super(optionName);
    }

    @Override
    public Date convert(String value) {
        try {
            return simpleDateFormat.parse(value);
        } catch (ParseException e) {
            throw new ParameterException(getErrorString(value, "a date. Format should be: " + FORMAT));
        }
    }

}
