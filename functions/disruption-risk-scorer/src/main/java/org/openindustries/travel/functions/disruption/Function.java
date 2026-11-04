package org.openindustries.travel.functions.disruption;

import io.quarkus.funqy.Funq;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Scores disruption signals (weather, congestion, industrial action) with an auditable rationale.
 * Transparent, auditable rules: NORMAL (< 35), WARNING (35-69), CRITICAL (>= 70);
 * the rationale is returned with the score for audit (ISO/IEC 27002 A.8.15).
 * Stateless by design (12-Factor VI): all state arrives in the request.
 */
public class Function {

    public record Event(String bookingId, int weatherIndex, int congestionIndex, int strikeIndex) {
    }

    public record Score(String bookingId, int score, String decision, String rationale) {
    }

    @Funq
    public Score disruptionRiskScorer(Event event) {
        int score = 0;
        StringBuilder why = new StringBuilder();
        if (event.weatherIndex() >= 70) {
            score += 40;
            why.append("weatherIndex >= 70; ");
        }
        if (event.congestionIndex() >= 60) {
            score += 30;
            why.append("congestionIndex >= 60; ");
        }
        if (event.strikeIndex() >= 50) {
            score += 30;
            why.append("strikeIndex >= 50; ");
        }
        String decision = score >= 70 ? "CRITICAL" : score >= 35 ? "WARNING" : "NORMAL";
        return new Score(event.bookingId(), score, decision,
                why.length() == 0 ? "no signals" : why.toString().trim());
    }
}
