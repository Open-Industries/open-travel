package org.openindustries.travel.functions.journey;

import io.quarkus.funqy.Funq;

import java.util.ArrayList;
import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Compares delay minutes and connection success against booking targets; one finding per breach.
 * Compares measured values with targets and returns a verdict with one finding per
 * breach — machine-checkable assurance evidence.
 */
public class Function {

    public record Kpi(String bookingId, int measuredDelayMinutes, int targetDelayMinutes,
                      double measuredConnectionSuccessPct, double targetConnectionSuccessPct) {
    }

    public record Verdict(String bookingId, boolean compliant, List<String> findings) {
    }

    @Funq
    public Verdict journeySlaEvaluator(Kpi kpi) {
        List<String> findings = new ArrayList<>();
        if (kpi.measuredDelayMinutes() > kpi.targetDelayMinutes()) {
            findings.add("delayMinutes " + kpi.measuredDelayMinutes() + " exceeds target " + kpi.targetDelayMinutes());
        }
        if (kpi.measuredConnectionSuccessPct() < kpi.targetConnectionSuccessPct()) {
            findings.add("connectionSuccessPct " + kpi.measuredConnectionSuccessPct() + " below target " + kpi.targetConnectionSuccessPct());
        }
        return new Verdict(kpi.bookingId(), findings.isEmpty(), findings);
    }
}
