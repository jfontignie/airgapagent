package com.airgap.airgapagent.service;

import com.cloudbees.syslog.Facility;
import com.cloudbees.syslog.MessageFormat;
import com.cloudbees.syslog.Severity;
import com.cloudbees.syslog.sender.UdpSyslogMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 6/6/2020.
 */
@Service
public class SyslogService {


    public static final String SYSLOG_SERVER = "syslog.server";
    private static final Logger log = LoggerFactory.getLogger(SyslogService.class);
    private final UdpSyslogMessageSender messageSender;


    public SyslogService(Environment environment) {
        messageSender = new UdpSyslogMessageSender();
        messageSender.setDefaultAppName("airgap");
        messageSender.setDefaultFacility(Facility.USER);
        messageSender.setDefaultSeverity(Severity.INFORMATIONAL);
        messageSender.setSyslogServerHostname(environment.getRequiredProperty(SYSLOG_SERVER));
        messageSender.setSyslogServerPort(514);
        messageSender.setMessageFormat(MessageFormat.RFC_3164); // optional, default is RFC 3164
    }

    public void send(String message) throws IOException {
        log.info("Sending syslog: {}", message);
        messageSender.sendMessage(message);
    }

}
