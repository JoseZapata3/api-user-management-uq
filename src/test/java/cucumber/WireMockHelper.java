package cucumber;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockHelper {
    private static WireMockServer wireMockServer;

    public static void startWireMockIfNotRunning() {
        if (wireMockServer == null || !wireMockServer.isRunning()) {
            wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8089));
            wireMockServer.start();

            ////////////////// AUTH //////////////////
            wireMockServer.stubFor(post(urlEqualTo("/auth/login"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"token\": \"mocked-token\"}")
                            .withStatus(200)));

            //////////////////// USERS ////////////////
            wireMockServer.stubFor(get(urlEqualTo("/users"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("[{\"id\":1,\"nombre\":\"Mocked User\"}]")
                            .withStatus(200)));

            wireMockServer.stubFor(get(urlEqualTo("/users/1"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"id\": 1, \"username\": \"MockerUser\", \"firstName\": \"MockerName\", \"lastName\": \"MockerLastName\", \"email\": \"mock@email.com\", \"country\": \"colombia\", \"phonePrefix\": \"+57\", \"phone\": \"1234567890\", \"dateBirth\": \"1-2-2000\", \"role\": \"student\"}")
                            .withStatus(200)));

            wireMockServer.stubFor(put(urlEqualTo("/users/1"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"id\": 1, \"role\": \"student\"}")
                            .withStatus(200)));

            wireMockServer.stubFor(patch(urlEqualTo("/users/1"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"id\": 1, \"username\": \"MockerUser\", \"firstName\": \"MockerName\", \"lastName\": \"MockerLastName\", \"email\": \"mock@email.com\", \"country\": \"colombia\", \"phonePrefix\": \"+57\", \"phone\": \"1234567890\", \"dateBirth\": \"1-2-2000\", \"role\": \"student\"}")
                            .withStatus(200)));

            wireMockServer.stubFor(delete(urlEqualTo("/users/1"))
                    .willReturn(aResponse().withStatus(204)));

            /// //////////// PROJECTS ///////////////
            wireMockServer.stubFor(post(urlEqualTo("/projects"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"id\": \"123e4567-e89b-12d3-a456-426614174000\", \"name\": \"Created Project\"}")
                            .withStatus(201)));

            wireMockServer.stubFor(get(urlEqualTo("/projects"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("[{\"id\": \"123e4567-e89b-12d3-a456-426614174000\", \"name\": \"Mocked Project\"}]")
                            .withStatus(200)));

            wireMockServer.stubFor(get(urlEqualTo("/projects/1"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"id\": \"1\", \"name\": \"Mocked Project\"}")
                            .withStatus(200)));

            wireMockServer.stubFor(put(urlEqualTo("/projects/1"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"id\": \"1\", \"name\": \"Updated Project\"}")
                            .withStatus(200)));

            wireMockServer.stubFor(delete(urlEqualTo("/projects/1"))
                    .willReturn(aResponse().withStatus(204)));
        }
    }

    public static String getMockBaseUrl() {
        return "http://localhost:8089";
    }
}