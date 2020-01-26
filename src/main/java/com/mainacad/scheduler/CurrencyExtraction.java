package com.mainacad.scheduler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class CurrencyExtraction {

    private List<String> list = Collections.synchronizedList(new ArrayList<>());
    private AtomicLong extractionTime = new AtomicLong(new Date().getTime());

    //    @Scheduled(cron = "0-59/2 * * * * *")
    public void getUkrNetResponse() {
        list.add(String.valueOf(new Date().getTime()));
    }

    //    @Scheduled(initialDelay = 1000, fixedDelay = 20000)
    public void writeData() {
        if (list.isEmpty()) {
            return;
        }

        List<String> elementsToRemove  = new ArrayList<>();
        for (String text : list) {
            if (Long.parseLong(text) >= extractionTime.get()) {
                log.info(new Date(Long.parseLong(text)).toString());
            }
            else {
                elementsToRemove.add(text);
            }
        }

        list.removeAll(elementsToRemove);
        extractionTime.set(new Date().getTime());
    }

}