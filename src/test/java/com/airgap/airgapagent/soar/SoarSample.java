package com.airgap.airgapagent.soar;

/**
 * com.airgap.airgapagent.soar
 * Created by Jacques Fontignie( on 5/22/2020.
 */
public class SoarSample {
//    private static final File SOURCE = new File("c:/");
//    private SyslogService syslogService;
//    AtomicInteger counter = new AtomicInteger();
//    AtomicLong last = new AtomicLong(System.currentTimeMillis());

//    @Disabled
//    @Test
//    public void simple() throws InterruptedException {
//        SuffixFileFilter suffix = new SuffixFileFilter(Arrays.asList(".p12", ".crt"), IOCase.INSENSITIVE);
//
//        FileWalkerService fileWalkerService = new FileWalkerService();
//
//        FileWalkerContext walkerQuery = new FileWalkerContext(SOURCE);
//        Disposable disposable = fileWalkerService.listFiles(walkerQuery)
//                .doOnEach(f -> {
//                    counter.incrementAndGet();
//                    if (System.currentTimeMillis() - last.get() > 1000) {
//                        last.set(System.currentTimeMillis());
//
//                    }
//                })
//                .filter(f -> !new SuffixFileFilter(Arrays.asList(".exe", ".dll"), IOCase.INSENSITIVE).accept(f))
//                .parallel()
//                .runOn(Schedulers.parallel())
//                .filter(file -> reader(file).map(this::matches).orElse(false))
//                .doOnEach(f -> System.out.println("found: " + f.get()))
//                .sequential()
//                .delaySubscription(Duration.ofMillis(10))
//                .subscribe();
//
//
//        while (!disposable.isDisposed()) {
//            Thread.sleep(1000);
//        }
////        disposable.dispose();
//    }

//    private boolean matches(Reader reader) {
//        Scanner scanner = new Scanner(reader);
//        return scanner.findWithinHorizon(Pattern.compile("password="), 0) != null;
//    }
//
//    private Optional<Reader> reader(File file) {
//        Tika tika = new Tika();
//        try {
//            return Optional.of(tika.parse(file));
//        } catch (IOException e) {
//            return Optional.empty();
//        }
//    }

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

//    @Test
//    public void flow2(File sourceFolder, File targetFolder) {
//        List<Disposable> disposables = new ArrayList<>();
//
//        fileService.listFiles(sourceFolder).doOnEach(file -> {
//            Optional<AirFile> previousFile = storeService.store("flow", file, Map.of("timestamp", Instant.now()));
//            if (previousFile.isPresent() &&
//                    storeService.get(previousFile.get(), "timestamp", Instant.MIN).compareTo(
//                            fileService.getLastModified(file)) < 0) {
//                if (fileService.hasExtension(file, "docx", "p12")) {
//                    fileService.copy(sourceFolder, file, targetFolder, FileCopy.TIMESTAMP);
//                    syslogService.syslog("suspicious file", file);
//                }
//                Disposable disposable = regexService.ifPresent(fileService.getContent(file), Arrays.asList("pwd", "password"), (match) -> {
//                    fileService.copy(sourceFolder, file, targetFolder, FileCopy.TIMESTAMP);
//                    syslogService.syslog("suspicious file", file);
//                });
//                disposables.add(disposable);
//            }
//        }).blockLast();
//        disposables.forEach(Disposable::dispose);
//        Assertions.assertTrue(true);
//    }

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
