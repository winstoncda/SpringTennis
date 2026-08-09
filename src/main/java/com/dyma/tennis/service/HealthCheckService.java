package com.dyma.tennis.service;

import org.springframework.stereotype.Service;

import com.dyma.tennis.ApplicationStatus;
import com.dyma.tennis.HealthCheck;

@Service
public class HealthCheckService {
    public HealthCheck healthCheck() {
        return new HealthCheck(ApplicationStatus.OK, "Welcome to the Tennis application!");
    }
}
