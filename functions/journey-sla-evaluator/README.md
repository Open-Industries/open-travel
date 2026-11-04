# Journey SLA Evaluator (serverless function)

Quarkus Funqy function on Knative (scale to zero). Compares delay minutes and connection success against booking targets; one finding per breach.

Invoke: `POST /journeySlaEvaluator` with the KPI payload.
