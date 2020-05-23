package com.airgap.airgapagent.soar;

import com.airgap.airgapagent.domain.AirFile;
import com.airgap.airgapagent.domain.AirFolder;
import com.airgap.airgapagent.domain.FileCopy;
import com.airgap.airgapagent.service.FileService;
import com.airgap.airgapagent.service.RegexService;
import com.airgap.airgapagent.service.StoreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;

import java.time.Instant;
import java.util.*;

/**
 * com.airgap.airgapagent.soar
 * Created by Jacques Fontignie( on 5/22/2020.
 */
public class SoarTest {
    private FileService fileService;
    private StoreService storeService;
    private SyslogService syslogService;
    private RegexService regexService;

//    @Test
//    public void flow1(Folder folder, Folder targetFolder) {
//        folder.listFiles(file -> {
//            file = file.store("flow", Map.of("timestamp", Instant.now()));
//
//           if (file.get("timestamp").compareTo(file.lastModified()) < 0 &&
//                   (file.hasExtension("docx","p12") || file.getContent().contains("pwd","password"))) {
//               file.copy(targetFolder,TIMESTAMP);
//               syslog("suspicious file",file);
//           }
//        });
//    }

    @Test
    public void flow2(AirFolder sourceFolder, AirFolder targetFolder) {
        List<Disposable> disposables = new ArrayList<>();

        fileService.listFiles(sourceFolder, file -> {
            Optional<AirFile> previousFile = storeService.store("flow", file, Map.of("timestamp", Instant.now()));
            if (previousFile.isPresent() &&
                    storeService.get(previousFile.get(), "timestamp", Instant.MIN).compareTo(
                            fileService.getLastModified(file)) < 0) {
                if (fileService.hasExtension(file, "docx", "p12")) {
                    fileService.copy(sourceFolder, file, targetFolder, FileCopy.TIMESTAMP);
                    syslogService.syslog("suspicious file", file);
                }
                Disposable disposable = regexService.ifPresent(fileService.getContent(file), Arrays.asList("pwd", "password"), (match) -> {
                    fileService.copy(sourceFolder, file, targetFolder, FileCopy.TIMESTAMP);
                    syslogService.syslog("suspicious file", file);
                });
                disposables.add(disposable);
            }
        });
        disposables.forEach(Disposable::dispose);
        Assertions.assertTrue(true);
    }

//    @Test
//    public void checkURL(Map<URL, boolean> map){
//        map.entrySet().stream().forEach(e -> {
//            e.getKey().connect(result -> {
//                e.getKey().store("check", Map.of("timestamp", Instant.now(), "succesc", result));
//                if (result != e.getValue()) {
//                    syslog("Invalid connectivity",e.getKey());
//                }
//            }
//        });
//    }
//
//    @Test
//    public void mailCheck(Mailbox mailbox){
//        mailbox.remove(mail -> {
//            sandbox.scan(mail).whenSuspicous(scan -> {
//                mail.extractURL(url -> url
//                        .scan()
//                        .whenSuspicious(urlScan -> askAnalyst(urlScan,"susppicous","clean").whenAnswer(0,url -> firewall.blackList(url))));
//            })
//        })
//    }
}
