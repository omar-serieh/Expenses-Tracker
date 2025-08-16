package com.training.expenseTracker.mapper.categories;

import com.training.expenseTracker.dto.categories.CategoriesReadDTO;
import com.training.expenseTracker.entity.Categories;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriesReadMapper {
    CategoriesReadDTO toDto(Categories category);
}
