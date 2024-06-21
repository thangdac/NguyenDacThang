package com.hutech.NguyenDacThang.controller;

import com.hutech.NguyenDacThang.model.Category;
import com.hutech.NguyenDacThang.model.Product;
import com.hutech.NguyenDacThang.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/listcategory")
    public String getList(Model model){
        model.addAttribute("listCategory", categoryService.getList());
        return "categories/listcategory";
    }

    @GetMapping("/add")
    public String addProduct(Model model){
        model.addAttribute("category", new Category());
        return "categories/add";
    }

    @PostMapping("/add")
    public String confirmAdd(@ModelAttribute Category category, Model model){
        categoryService.EditOrAddCategory(category);
        model.addAttribute("listCategory", categoryService.getList());
        return "redirect:/categories/listcategory";
    }


    @GetMapping("/edit")
    public String edit(@RequestParam Long id, Model model){
        Category category = categoryService.GetCategory(id);
        model.addAttribute("category", category);
        return "categories/edit";
    }

    @PostMapping("/edit")
    public String confirmEdit (Category category, Model model){
        categoryService.EditOrAddCategory(category);
        model.addAttribute("listCategory", categoryService.getList());
        return "redirect:/categories/listcategory";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam Long id, Model model){
        Category category = categoryService.GetCategory(id);
        model.addAttribute("category", category);
        return "categories/delete";
    }
    @PostMapping("/delete")
    public String confirmDelete (Product product, Model model){
        categoryService.DeleteCategory(product.getId());
        model.addAttribute("listCategory", categoryService.getList());
        return "redirect:/categories/listcategory";
    }

    @GetMapping("/searchcategory")
    public String searchCategory(Model model, @RequestParam String name){
        model.addAttribute("listCategory", categoryService.getList().stream().filter(category -> category.getName().contains(name)).collect(Collectors.toList()));
        return "categories/searchcategory";
    }
}
