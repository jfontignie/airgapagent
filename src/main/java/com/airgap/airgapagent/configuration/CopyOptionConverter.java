package com.airgap.airgapagent.configuration;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

import java.util.HashSet;
import java.util.Set;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
public class CopyOptionConverter implements IStringConverter<Set<CopyOption>> {
    @Override
    public Set<CopyOption> convert(String value) {
        String[] values = value.split(",");
        Set<CopyOption> set = new HashSet<>();
        for (String current : values) {
            try {
                set.add(CopyOption.valueOf(current));
            } catch (IllegalArgumentException e) {
                throw new ParameterException("Impossible to parse " + current);
            }
        }
        return set;
    }
}
