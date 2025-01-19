package com.holiday.request.workers;

import com.holiday.request.service.MailService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SendEmailJobWorker {

    private final MailService mailService;

    @JobWorker(type = "send-mail")
    public Map<String, Object> sendEmailJob(final ActivatedJob job) {
        var jobResultVariables = job.getVariablesAsMap();

        String email = (String) jobResultVariables.get("email");
        System.out.println("email "+email);
        mailService.sendEmail(email);
        return jobResultVariables;
    }
}
