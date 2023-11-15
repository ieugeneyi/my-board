package com.geneyi.dto.menu;

import com.geneyi.domain.menu.Menu;
import lombok.Getter;

import java.util.List;

@Getter
public class MenuResponseDto {
    private Long id;
    private String name;
    private String url;
    private int sortOrder;
    private String delFlag;
    private List<MenuResponseDto> children;

    public MenuResponseDto(Menu menu){
        this.id = menu.getId();
        this.name = menu.getName();
        this.url = menu.getUrl() == null ? "" : menu.getUrl();
        this.sortOrder = menu.getSortOrder();
        this.children = menu.getChildren().stream()
                .map(MenuResponseDto::new).toList();
    }
}
