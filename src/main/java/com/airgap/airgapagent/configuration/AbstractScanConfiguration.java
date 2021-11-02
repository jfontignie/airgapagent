package com.airgap.airgapagent.configuration;

import com.airgap.airgapagent.algo.AlgoType;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.BaseConverter;
import com.beust.jcommander.validators.PositiveInteger;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/3/2020.
 */
public abstract class AbstractScanConfiguration<T> {

    @Parameter(
            names = "-minHit",
            description = "number of minimum hits to match",
            validateWith = PositiveInteger.class)
    private int minHit = 5;

    @Parameter(
            names = "-syslog",
            description = "Enable syslog to send to send, parameters must be set in application.properties"
    )
    private boolean syslog;

    @Parameter(
            names = "-corpus",
            description = "corpus file: This file must be in CSV format and needs to contain an header. " +
                    "The algorithm used is Aho Corasick to retrieve the file",
            required = true,
            validateWith = FileExistsValidator.class)
    private File corpusLocation;


    @Parameter(
            names = "-state",
            description = "file containing the state to resume in case of error")
    private File stateLocation = new File("state.csv");

    @Parameter(
            names = "-schedule",
            description = "How many second interval between two state persistence")
    private long schedule = 5;

    @Parameter(
            names = "-earlierThan",
            description = "The file must be earlier than: Format is YYYY:MM:DD hh:mm:ss",
            converter = DateConverter.class
    )
    private Date earlierThan;

    @Parameter(
            names = "-algo",
            description = "Specify the algorithm that can be used for matching patterns")
    private AlgoType algo = AlgoType.AHO_CORASICK;

    protected AbstractScanConfiguration() {
    }


    public void setCorpus(File corpusLocation) {
        this.corpusLocation = corpusLocation;
    }

    public void setInterval(long schedule) {
        this.schedule = schedule;
    }

    public long getSchedule() {
        return schedule;
    }

    public int getMinHit() {
        return minHit;
    }

    public void setMinHit(int minHit) {
        this.minHit = minHit;
    }

    public File getCorpusLocation() {
        return corpusLocation;
    }

    public File getStateLocation() {
        return stateLocation;
    }

    public void setStateLocation(File stateLocation) {
        this.stateLocation = stateLocation;
    }

    public boolean isSyslog() {
        return syslog;
    }

    public void setSyslog(boolean syslog) {
        this.syslog = syslog;
    }

    public Duration getSaveInterval() {
        return Duration.ofSeconds(schedule);
    }

    public AlgoType getAlgo() {
        return algo;
    }

    public void setAlgo(AlgoType algo) {
        this.algo = algo;
    }

    public abstract T getRootLocation();

    public static class DateConverter extends BaseConverter<Date> {
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
}
