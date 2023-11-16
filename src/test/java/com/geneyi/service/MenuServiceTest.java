package com.geneyi.service;

import com.geneyi.domain.menu.Menu;
import com.geneyi.domain.menu.MenuRepository;
import com.geneyi.dto.menu.MenuRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MenuServiceTest {
    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @Transactional
    @Test
    public void add_menu() throws Exception {
        //given
        Menu parent = Menu.builder()
                .name("1depth menu1")
                .url("/menu1")
                .build();
        menuRepository.save(parent);

        MenuRequestDto childDto = MenuRequestDto.builder()
                .name("2depth menu2")
                .url("/menu2")
                .parent_id(parent.getId())
                .build();

        Long addedMenu = menuService.addMenu(childDto);
        Menu menu = menuRepository.findById(addedMenu).orElse(null);
        assert menu != null;
        menu.changeParent(parent);
        //when

        //then
        Menu parentMenu = menuRepository.findById(parent.getId()).orElse(null);
        assert parentMenu != null;
        Assertions.assertThat(parentMenu.getId()).isEqualTo(menu.getParent().getId());

    }

    @Transactional
    @Test
    public void menu_edit() throws Exception {
        //given

    }
}