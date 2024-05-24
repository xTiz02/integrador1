package com.example.libreria.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HomeService {

    /*@Autowired
    private MemberService memberService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;*/

    public Map<String, Long> getMapParametros() {
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("totalMiembros", 1L);
        map.put("totalEstudiantes", 1L);
        map.put("totalPadres", 1L);
        map.put("totalCategorias", 1L);
        map.put("totalLibros", 1L);
        map.put("totalLibrosPrestados", 1L);
        return map;
    }
}
