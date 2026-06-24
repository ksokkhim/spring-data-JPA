package co.istad.ecommerce.features.category;


import co.istad.ecommerce.features.category.dto.CategoryResponse;
import co.istad.ecommerce.features.category.dto.CreateCategoryRequest;
import co.istad.ecommerce.features.dto.RequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

//    @GetMapping("/search")
//    public List<CategoryResponse> findCategoryName(
//           @RequestParam String name ){
//               return  categoryService.findByCategoryName(name);
//    }


    @GetMapping("/search")
public List<CategoryResponse> searchCategory( @RequestParam String name){
        return categoryService.findByCategoryNameContains(name);
}


    //specification
    @PostMapping("/search")
    public ResponseEntity<Page<CategoryResponse>> searchCategories(
            @RequestBody RequestDto requestDto,
            @PageableDefault(size = 10) Pageable pageable

    ){
        Page<CategoryResponse> responsePage = categoryService.findByCriteria(requestDto, pageable);
        return ResponseEntity.ok(responsePage);
    }




}
