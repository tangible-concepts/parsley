package de.tngbl.parsley.acceptance;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class AcceptanceTestScenarioProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                new AcceptanceTestScenarioBuilder("src/test/resources/tea-shop")
                        .withMapTitle("Tea Shop")
                        .withAnchor("Business", "0.95", "0.63")
                        .withAnchor("Public", "0.95", "0.78")
                        .withComponent("Cup of Tea", "0.79", "0.61")
                        .withComponent("Cup", "0.73", "0.78")
                        .withComponent("Tea", "0.63", "0.81")
                        .withComponent("Hot Water", "0.52", "0.80")
                        .withComponent("Water", "0.38", "0.82")
                        .withComponent("Kettle", "0.43", "0.35")
                        .withEvolution("Kettle", "0.62")
                        .withComponent("Power", "0.1", "0.7")
                        .withEvolution("Power", "0.89")
                        .withRelationship("Business", "Cup of Tea")
                        .withRelationship("Public", "Cup of Tea")
                        .withRelationship("Cup of Tea", "Cup")
                        .withRelationship("Cup of Tea", "Tea")
                        .withRelationship("Cup of Tea", "Hot Water")
                        .withRelationship("Hot Water", "Water")
                        .withRelationship("Hot Water", "Kettle")
                        .withRelationship("Kettle", "Power")
                        .build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/agriculture/additions/SW-FoodTracing").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/agriculture/agriculture-food-traceability and security").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/agriculture/agriculture-food-waste").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/agriculture/agriculture-regenerative-farming").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/construction/construction-alternative-construction").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/construction/construction-green").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/construction/construction-supply-chain").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/defence/additions/defence-general").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/defence/additions/multi-domain operating model").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/defence/defence - doctrine").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/defence/defence - grey zone").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/defence/defence-cybersecurity").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/defence/defence-intelligence").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/defence/defence-supply-chain").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/education/education-async- sync").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/education/education-cognitive-flexibility").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/education/education-equity.txt").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/education/education-lifelong (General)").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/education/education-power-struggle").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/education/education-purpose").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/education/Education_and_Social_Mobility").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/education/Education_IndividualLearning").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/education/education_inequality-physical-access").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/energy/additions/cni-energy").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/energy/energy-centralisation").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/energy/energy-disruption").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/energy/energy-disruption-example").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/energy/energy-regulation").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/energy/energy-reliability-supply").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/finance/finance - risk management").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/finance/finance-crypto").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/finance/finance-embedded").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/finance/finance-sovereign-risk").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/government/government-adopting-emerging-practices").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/government/government-digital-identity").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/government/government-energy-security").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/government/government-open").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/government/government-procedure-principles").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/government/government-sovereignty").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/healthcare/Healthcare - clinical-trial-management").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/healthcare/healthcare-AI").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/healthcare/healthcare-clinical-decisions").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/healthcare/healthcare-data-metrics-baseline").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/healthcare/healthcare-ethics").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/healthcare/healthcare-health-value-chain").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/manufacturing/additions/ manufacturing-skills-Operations skills-in-the-plant").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/manufacturing/additions/manufacturing-automation-quality").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/manufacturing/additions/manufacturing-supply-chains-dynamics-between -IP-&-safety").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/manufacturing/manufacturing - agile").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/manufacturing/manufacturing - automation").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/manufacturing/manufacturing - supply chain").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/personal/carbon-report").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/personal/CNI-Energy").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/personal/ESG - Sustainability").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/personal/FILO - FIFO").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/personal/financial-inclusion").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/personal/gender").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/personal/multi-domain defence").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/personal/workplace").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/retail/retail - connected journey").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/retail/retail - logistics").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/retail/retail-automation").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/retail/retail-sustainability").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/retail/retail-virtual-shopping").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/telecoms/additions/telecom-network").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/telecoms/basic_focus/core.map").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/telecoms/basic_focus/metadata.map").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/telecoms/basic_focus/services.map").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/telecoms/telecom - business models").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/telecoms/telecom-network").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/telecoms/telecoms-sovereignty.owm").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/telecoms/telecoms-space").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/transportation/addons/transportation-good").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/transportation/coherence city transport and micromobility").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/transportation/transportation - changing behaviour").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/transportation/transportation - changing consumer behaviour and demand").build(),
                new AcceptanceTestScenarioBuilder("src/test/resources/transportation/transportation - logistics").build()

        ).map(Arguments::of);
    }
}
