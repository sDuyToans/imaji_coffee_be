package com.duytoan.imajicoffee.imaji_coffee_be.services.impl.common;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.common.SpaceDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.common.Space;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.common.SpaceRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.services.common.ISpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpaceServiceImpl implements ISpaceService {
    private final SpaceRepository spaceRepository;

    /**
     * @return list of SpaceDto objects
     */
    @Override
    @Cacheable(value = "spaces")
    public List<SpaceDto> getSpaces() {
        return spaceRepository.findAll()
                .stream().map(this::mapToSpaceDto)
                .collect(Collectors.toList());
    }

    private SpaceDto mapToSpaceDto(Space space) {
        SpaceDto spaceDto = new SpaceDto();
        BeanUtils.copyProperties(space, spaceDto);
        return spaceDto;
    }
}
