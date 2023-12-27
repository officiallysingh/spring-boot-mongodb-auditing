package com.ksoot.config;

import com.ksoot.common.CommonConstants;
import java.util.List;

public class ActuatorUtils {

    private ActuatorUtils() {
        throw new IllegalStateException("Just a utility class, not supposed to be instantiated");
    }

    public static String[] getPaths(final ActuatorEndpointProperties actuatorEndpointProperties) {
        if (actuatorEndpointProperties != null) {
            List<String> exposedEndpoints;
            if (actuatorEndpointProperties.getExposure().getInclude().contains("*")) {
                exposedEndpoints = ActuatorEndpointProperties.ALL_ENDPOINTS;
            } else {
                exposedEndpoints = actuatorEndpointProperties.getExposure().getInclude();
            }

            if (CommonConstants.SLASH.equals(actuatorEndpointProperties.getBasePath())) {
                return exposedEndpoints.stream()
                        .map(
                                path ->
                                        CommonConstants.SLASH
                                                + actuatorEndpointProperties.getPathMapping().getOrDefault(path, path)
                                                + "/**")
                        .toArray(String[]::new);
            } else {
                return new String[]{actuatorEndpointProperties.getBasePath() + "/**"};
            }
        } else {
            // TODO: Test if not creating any problem
            return new String[0];
        }
    }
}
