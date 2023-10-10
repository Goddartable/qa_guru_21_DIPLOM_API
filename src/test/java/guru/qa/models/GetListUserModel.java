package guru.qa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class GetListUserModel {
    int page;
    @JsonProperty("per_page")
    int perPage;
    @JsonIgnoreProperties(ignoreUnknown = true)
    int total;
    @JsonProperty("total_pages")
    int totalPages;
    List<GetUserDataList> data;
    GetUserSupportModel support;
}
