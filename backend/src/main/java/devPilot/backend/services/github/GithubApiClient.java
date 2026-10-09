// the client which interact with the github api
// currently login user ke liye uske private and public repos fetch karega


// we are putting a wrapper on rest client
package devPilot.backend.services.github;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GithubApiClient{
    private static final String API_BASE = "https://api.github.com";

    //store the repo info
    private static final ParameterizedTypeReference<List<Map<String, Object>>> LIST_MAP = new ParameterizedTypeReference<>() {
    };

    // store the fie info
    private static final ParameterizedTypeReference<Map<String, Object>> MAP = new ParameterizedTypeReference<>() {
    };

    private final RestClient.Builder restClientBuilder;

    //currently login user ke repos ko list karke dedo  - it gives in json fromat
    public List<Map<String, Object>> listUserRepos(String accessToken) {
        //an array list which accepts token
        List<Map<String, Object>> all = new ArrayList<>();

        int page = 1;
        while (page <= 10) {
            final int currentPage = page;
            List<Map<String, Object>> pageRepos = client(accessToken)
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/user/repos")
                            // what we need
                            .queryParam("affiliation", "owner,collaborator,organization_member")
                            .queryParam("sort", "updated")
                            .queryParam("per_page", 100)
                            .queryParam("page", currentPage)
                            .build())
                            //retrieve the final value
                    .retrieve()
                    .body(LIST_MAP); // convert json response to java object
            if (pageRepos == null || pageRepos.isEmpty()) {
                break;
            }
            all.addAll(pageRepos);
            if (pageRepos.size() < 100) {
                break;
            }
            page++;
        }
        return all;
    }

// provide the structure
// ek bar mei ek repo tree
      public Map<String, Object> getRepoTree(String accessToken, String owner, String repo, String branch) {
        return client(accessToken)
                .get()
                .uri("/repos/{owner}/{repo}/git/trees/{branch}?recursive=1", owner, repo, branch)
                .retrieve()
                .body(MAP);
    }

    // provide file content
       public String getFileContent(String accessToken, String owner, String repo, String path) {
        Map<String, Object> body = client(accessToken)
                .get()
                .uri("/repos/{owner}/{repo}/contents/{path}", owner, repo, path)
                .retrieve()
                .body(MAP); // conversion
        if (body == null) {
            return null;
        }
        Object encoding = body.get("encoding");
        Object content = body.get("content");
        if (content == null) {
            return null;
        }
        if ("base64".equals(String.valueOf(encoding))) {
            String raw = String.valueOf(content).replaceAll("\\s", "");
            return new String(Base64.getDecoder().decode(raw), StandardCharsets.UTF_8);
        }
        return String.valueOf(content);
    }

    private RestClient client(String accessToken){
          return restClientBuilder
                .baseUrl(API_BASE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
                .defaultHeader(HttpHeaders.USER_AGENT, "DevPilot")
                .build();
    }
}