package com.holiday.request.workers;

import com.holiday.request.service.MailService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailWorker {
    private final MailService mailService;
    @JobWorker(type = "send_mail")
    public void sendMail(final ActivatedJob job) {
        System.out.println("E-mail wysłany do: ");

        Map<String, Object> jobVariables = job.getVariablesAsMap();
        String toEmail = (String) jobVariables.get("email");

        mailService.sendEmail(toEmail);

        System.out.println("E-mail wysłany do: " + toEmail);
    }
}
