package com.geneyi.service;

import com.geneyi.domain.menu.Menu;
import com.geneyi.domain.menu.MenuRepository;
import com.geneyi.dto.menu.MenuRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MenuServiceTest {
    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @BeforeEach
    public void setUp() throws Exception {
        menuRepository.deleteAll();
    }

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
        assertThat(parentMenu.getId()).isEqualTo(menu.getParent().getId());

    }

    @Transactional
    @Test
    public void menu_edit() throws Exception {
        //given
        MenuRequestDto menu = MenuRequestDto.builder()
                .name("menu")
                .url("/menu")
                .build();
        Long addedMenu = menuService.addMenu(menu);

        //when
        Menu menu1 = menuRepository.findById(addedMenu).orElse(null);
        menu.setName("menu111");
        assert menu1 != null;
        menuService.editMenu(menu1.getId(), menu);

        //then
        assertThat(menu1.getName()).isEqualTo("menu111");

    }
}