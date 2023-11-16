package com.geneyi.dto.menu;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {
    private Long id;
    private Long parent_id;
    private String name;
    private String url;
    private int sortOrder;
    private List<MenuRequestDto> children;

}
