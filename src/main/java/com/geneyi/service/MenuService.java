package com.geneyi.service;

import com.geneyi.domain.menu.Menu;
import com.geneyi.domain.menu.MenuRepository;
import com.geneyi.dto.menu.MenuRequestDto;
import com.geneyi.dto.menu.MenuResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public List<MenuResponseDto> getAllMenus(){
        return menuRepository.findAllByParentIsNull(
                Sort.by(Sort.Direction.ASC, "sortOrder")
        ).stream()
                .map(MenuResponseDto::new)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveMenu(ArrayList<MenuRequestDto> dto){
        Menu parent;
        Menu child;
        for (MenuRequestDto menuRequestDto : dto){
            //get menu using id
            parent = menuRepository.findById(menuRequestDto.getId())
                        .orElse(null);
            if (parent != null){
                if (parent.getParent() != null){
                    parent.changeParent(null);
                }
                parent.update(menuRequestDto);
            }
            else{
                parent = new Menu(menuRequestDto);
            }
            menuRepository.save(parent);

            //2depth
            if (!menuRequestDto.getChildren().isEmpty()){
                for (MenuRequestDto childDto : menuRequestDto.getChildren()){
                    child = menuRepository.findById(childDto.getId())
                                .orElse(null);
                    if (child != null){
                        if (parent.getId() != (child.getParent() != null ? child.getParent().getId() : 0)){
                            child.changeParent(parent);
                        }
                        child.update(childDto);}
                    else{
                        child = new Menu(childDto);
                        child.changeParent(parent);
                    }
                    menuRepository.save(child);
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void addMenu(MenuRequestDto dto){
        Menu menu = new Menu(dto);
        menuRepository.save(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editMenu(Long menuId, MenuRequestDto dto){
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("menu not found"));
        menu.update(dto);
        menuRepository.save(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long menuId){
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("menu not found"));
        menuRepository.deleteById(menuId);
        if (!menu.getChildren().isEmpty()){
            for (Menu child : menu.getChildren()){
                menuRepository.deleteById(child.getId());
            }
        }
    }


}
