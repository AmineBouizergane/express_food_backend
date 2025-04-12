package com.example.expressfood.dto.response;


import com.example.expressfood.entities.FeedBack;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@ToString
public class FeedBackResponse {
    private String comment;
    private int rating;
    private ClientResponse client;

    public static FeedBackResponse fromEntity(FeedBack feedBack){
        FeedBackResponse feedBackResponse = new FeedBackResponse();
        BeanUtils.copyProperties(feedBack, feedBackResponse);
        feedBackResponse.setClient(ClientResponse.fromEntity(feedBack.getClient()));
        return feedBackResponse;
    }
}
