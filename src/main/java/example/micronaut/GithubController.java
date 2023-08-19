package example.micronaut;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.reactivestreams.Publisher;
import java.util.List;

@Controller("/github")
public class GithubController {
    private final GithubLowLevelClient githubLowLevelClient;
    private final GithubApiClient githubApiClient;
    public GithubController(GithubLowLevelClient githubLowLevelClient,
                            GithubApiClient githubApiClient) {
        this.githubLowLevelClient = githubLowLevelClient;
        this.githubApiClient = githubApiClient;
    }

    @Get("/releases-lowlevel")
    @SingleResult
    Publisher<List<GithubRelease>> releasesWithLowLevelClient() {
        return githubLowLevelClient.fetchReleases();
    }

    @Get("/releases")
    @SingleResult
    Publisher<List<GithubRelease>> fetchReleases() {
        return githubApiClient.fetchReleases();
    }
}
