package com.elibrary.rshelfstore.pojos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterOptions {

    private String filterColumn;
    private String filterValue;
    private String sortOrder;
    private String sortBy;
    
    //For the filters on book pages
    private List<Integer> list;
}
