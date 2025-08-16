package com.training.expenseTracker.mapper.categories;

import com.training.expenseTracker.dto.categories.CategoriesUpdateDTO;
import com.training.expenseTracker.entity.Categories;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoriesUpdateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(CategoriesUpdateDTO categoriesUpdateDTO, @MappingTarget Categories categories);

}

