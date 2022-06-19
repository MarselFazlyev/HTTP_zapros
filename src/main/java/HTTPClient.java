import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class HTTPClient {
    public static final String REMOTE_SERVICE_URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient HTTPClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
        CloseableHttpResponse response = HTTPClient.execute(request);
//        System.out.println(response);
//        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
        String body = EntityUtils.toString(response.getEntity());
//        System.out.println(body);
        List<Cats> cats = mapper.readValue(body, new TypeReference<List<Cats>>() {
        });
        List<Cats> notNullUpvotes = cats.stream().filter(x -> x.getUpvotes() != null).collect(Collectors.toList());
        System.out.println(notNullUpvotes);

    }


}


