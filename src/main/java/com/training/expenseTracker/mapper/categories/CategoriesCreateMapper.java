package com.training.expenseTracker.mapper.categories;

import com.training.expenseTracker.dto.categories.CategoriesCreateDTO;
import com.training.expenseTracker.entity.Categories;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriesCreateMapper {
    @Mapping(target = "id", ignore = true)
    Categories toEntity(CategoriesCreateDTO categoriesCreateDTO);
}
