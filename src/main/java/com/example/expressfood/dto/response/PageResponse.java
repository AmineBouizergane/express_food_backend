package com.example.expressfood.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PageResponse<T> {

    private int size;
    private int page;
    private int totalPage;
    private List<T> content = new ArrayList<>();

}
