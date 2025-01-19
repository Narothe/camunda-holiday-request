package com.holiday.request.workers;


import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class ManagerWorker {

    @JobWorker(type = "consideration")
    public Map<String, Object> consideration(final ActivatedJob job) {

        var jobResultVariables = job.getVariablesAsMap();
        String startDate = (String) jobResultVariables.get("startDate");
        String endDate = (String) jobResultVariables.get("endDate");

        jobResultVariables.put("decision", 0);

        return jobResultVariables;

    }



}