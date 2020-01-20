package org.lu.hypervisor.config;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DistanceCalcConfig {
    @Bean
    public DistanceMeasure distanceMeasureFactory() {
        return new DistanceMeasure() {
            private EuclideanDistance euclideanDistance = new EuclideanDistance();

            @Override
            public double compute(double[] doubles, double[] doubles1) throws DimensionMismatchException {
                return Math.pow(this.euclideanDistance.compute(doubles,
                        doubles1), 2);
            }
        };
    }
}
