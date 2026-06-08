package co.istad.ecommerce.controller;

import co.istad.ecommerce.dto.CategoryResponse;
import co.istad.ecommerce.dto.CreateCategoryRequest;
import co.istad.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vi/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createNew(@Valid @RequestBody CreateCategoryRequest createCategoryRequest){
     return  categoryService.createNew(createCategoryRequest);

    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "25") int pageSize
    ) {
        Page<CategoryResponse> categories = categoryService.getAllCategory(pageNumber, pageSize);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById( @Valid @PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }
@DeleteMapping("/{id}")
    public CategoryResponse deleteCategoryById(@Valid @PathVariable Integer id){
        return categoryService.deleteCategoryById(id);
    }

    @PatchMapping("/{id}")
    public CategoryResponse updateCategoryById(
            @PathVariable Integer id,
            @RequestBody CreateCategoryRequest createCategoryRequest
    ) {
        return categoryService.updateCategoryById(id, createCategoryRequest);
    }

    @GetMapping("/{id}/subcategories")
    public List<CategoryResponse> getSubcategories(@PathVariable Integer id){
        return categoryService.getSubcategories(id);
    }







}
