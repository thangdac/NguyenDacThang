package com.hutech.NguyenDacThang.controller;
import com.hutech.NguyenDacThang.model.Product;
import com.hutech.NguyenDacThang.service.BrandService;
import com.hutech.NguyenDacThang.service.CartService;
import com.hutech.NguyenDacThang.service.CategoryService;
import com.hutech.NguyenDacThang.service.ManufacturerService;
import com.hutech.NguyenDacThang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;
    private final ManufacturerService manufacturerService;
    private final BrandService brandService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, ManufacturerService manufacturerService, BrandService brandService, CartService cartService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
        this.cartService = cartService;
        this.brandService = brandService;
    }

    @GetMapping("/listproduct")
    public String getList(Model model) {
        model.addAttribute("listProduct", productService.getList());
        return "products/listproduct";
    }

    @GetMapping("/listproductUser")
    public String getListForUser(Model model) {
        model.addAttribute("listProductUser", productService.getList());
        return "products/listproductUser";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getList());
        model.addAttribute("manufacturers", manufacturerService.getList());
        model.addAttribute("brands", brandService.getList());
        return "products/add";
    }

    @PostMapping("/add")
    public String confirmAdd(@ModelAttribute Product product, @RequestParam("image") MultipartFile image, RedirectAttributes redirectAttributes) {
        try {
            productService.editOrAddProduct(product, image);
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error uploading image: " + e.getMessage());
            return "redirect:/products/add";
        }
        return "redirect:/products/listproduct";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam Long id, Model model) {
        Product prod = productService.getProduct(id);
        model.addAttribute("product", prod);
        model.addAttribute("categories", categoryService.getList());
        model.addAttribute("manufacturers", manufacturerService.getList());
        model.addAttribute("brands", brandService.getList());
        return "products/edit";
    }

    @PostMapping("/edit")
    public String confirmEdit(@ModelAttribute Product product, @RequestParam("image") MultipartFile image, Model model) {
        try {
            productService.editOrAddProduct(product, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/products/listproduct";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id, Model model) {
        Product prod = productService.getProduct(id);
        model.addAttribute("product", prod);
        return "products/delete";
    }

    @PostMapping("/delete")
    public String confirmDelete(Product product, Model model) {
        productService.deleteProduct(product.getId());
        model.addAttribute("listProduct", productService.getList());
        return "redirect:/products/listproduct";
    }

    @GetMapping("/searchbook")
    public String searchProductByName(String name, Model model) {
        model.addAttribute("listProduct", productService.getList().stream().filter(product -> product.getName().contains(name)).collect(Collectors.toList()));
        return "products/searchbook";
    }

    @PostMapping("/addToCart/{productId}")
    public String addToCart(@PathVariable Long productId, @RequestParam("quantity") int quantity, RedirectAttributes redirectAttributes) {
        cartService.addToCart(productId, quantity);
        redirectAttributes.addFlashAttribute("addedToCart", true);
        return "redirect:/products";
    }
}