package co.edu.uniquindio.ingesis.restful.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class SystemHealthCheck implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        OperatingSystemMXBean health = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuload = health.getCpuLoad()*100; //(%)
        double freeMemory = health.getFreeMemorySize() / (1024.0 * 1024.0); // MB
        double totalMemory = health.getTotalMemorySize() / (1024.0 * 1024.0); // MB

        return HealthCheckResponse.named("System").up()
                .withData("CPU load (%)", (long) cpuload)
                .withData("Free memory (MB)",(long) freeMemory)
                .withData("Total memory (MB)",(long) totalMemory)
                .build();
    }
}
