package com.duytoan.imajicoffee.imaji_coffee_be.services.impl.common;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.common.NewDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.common.News;
import com.duytoan.imajicoffee.imaji_coffee_be.exceptions.ResourceNotFoundException;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.common.NewRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.services.common.INewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NewServiceImpl implements INewService {

    private final NewRepository newRepository;
    @Override
    @Cacheable("news")
    public List<NewDto> getPageNews(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return newRepository.findAll(pageable).stream().map(this::mapToNewDto).collect(Collectors.toList());
    }

    @Override
    public NewDto getNewById(Long newId) {
        return newRepository
                .findById(newId)
                .map(this::mapToNewDto)
                .orElseThrow(() -> new ResourceNotFoundException("New", "NewId", newId.toString()));
    }

    private NewDto mapToNewDto(News news){
        NewDto newDto = new NewDto();
        BeanUtils.copyProperties(news, newDto);
        return newDto;
    }
}
