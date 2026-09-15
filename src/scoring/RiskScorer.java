package scoring;

public class RiskScorer {

    public double calculateRisk(
            int changeCount,
            int totalChanges,
            int couplingCount,
            int contributorCount) {

        double changeScore = Math.min(changeCount / 10.0, 1.0);
        double sizeScore = Math.min(totalChanges / 500.0, 1.0);
        double couplingScore = Math.min(couplingCount / 10.0, 1.0);
        double ownershipScore = contributorCount <= 1 ? 1.0 : 0.5;

        double risk =
                (changeScore * 0.35)
                + (sizeScore * 0.20)
                + (couplingScore * 0.30)
                + (ownershipScore * 0.15);

        return Math.round(risk * 100.0) / 100.0;
    }

    public String getRiskLevel(double score) {

        if (score >= 0.75) {
            return "HIGH";
        }

        if (score >= 0.45) {
            return "MEDIUM";
        }

        return "LOW";
    }
}