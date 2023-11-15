package com.geneyi.domain.menu;


import com.geneyi.domain.BaseTimeEntity;
import com.geneyi.dto.menu.MenuRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Menu extends BaseTimeEntity {

    @Id
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private int sortOrder;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private List<Menu> children = new ArrayList<>();


    @Builder
    public Menu(String name, String url, int sortOrder, Menu parent) {
        this.name = name;
        this.url = url;
        this.sortOrder = sortOrder;
        this.parent = parent;
    }

    public Menu(MenuRequestDto dto){
        this.name = dto.getName();
        this.url = dto.getUrl();
        this.sortOrder = dto.getSortOrder();
        if (dto.getChildren() != null){
            this.children = dto.getChildren().stream()
                    .map(Menu::new)
                    .toList();
        }
    }

    public void update(MenuRequestDto dto){
        this.name = dto.getName();
        this.url = dto.getUrl();
        if (dto.getSortOrder() != 0){
            this.sortOrder = dto.getSortOrder();
        }
    }

    public void changeParent(Menu parent){
        this.parent = parent;
        if (parent != null){
            parent.getChildren().add(this);
        }
    }

}
