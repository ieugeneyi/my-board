package com.geneyi.dto.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MenuRequestDto {
    private Long id;
    private Long parent_id;
    private String name;
    private String url;
    private int sortOrder;
    private List<MenuRequestDto> children;
}
