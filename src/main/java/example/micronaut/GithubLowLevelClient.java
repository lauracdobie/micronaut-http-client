package example.micronaut;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import java.net.URI;
import java.util.List;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

@Singleton
public class GithubLowLevelClient {
    private final HttpClient httpClient;
    private final URI uri;

    public GithubLowLevelClient(@Client(id = "github") HttpClient httpClient,
                                GithubConfiguration configuration) {
        this.httpClient = httpClient;
        uri = UriBuilder.of("/repos")
                .path(configuration.organization())
                .path(configuration.repo())
                .path("releases")
                .build();
    }

    Publisher<List<GithubRelease>> fetchReleases() {
        HttpRequest<?> req = HttpRequest.GET(uri)
                .header(USER_AGENT, "Micronaut HTTP Client")
                .header(ACCEPT, "application/vnd.github.v3+json, application/json");
        return httpClient.retrieve(req, Argument.listOf(GithubRelease.class));
    }

}
