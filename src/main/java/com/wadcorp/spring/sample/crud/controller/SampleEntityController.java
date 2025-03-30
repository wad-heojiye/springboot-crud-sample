package com.wadcorp.spring.sample.crud.controller;

import com.wadcorp.spring.sample.crud.entity.SampleEntity;
import com.wadcorp.spring.sample.crud.repository.SampleEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/samples")
@Tag(name = "Sample API", description = "CRUD API for SampleEntity")
public class SampleEntityController {

  @Autowired
  private SampleEntityRepository repository;

  @Operation(summary = "전체 데이터 조회", description = "페이지 단위로 전체 데이터를 조회합니다.")
  @GetMapping
  public Page<SampleEntity> getAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return repository.findAll(PageRequest.of(page, size));
  }

  @Operation(summary = "단건 조회", description = "특정 id의 데이터를 조회합니다.")
  @GetMapping("/{id}")
  public SampleEntity getById(@PathVariable Long id) {
    Optional<SampleEntity> sample = repository.findById(id);
    return sample.orElse(null);
  }

  @Operation(summary = "새 데이터 생성", description = "새로운 데이터를 생성합니다.")
  @PostMapping
  public SampleEntity create(@RequestBody SampleEntity sample) {
    return repository.save(sample);
  }

  @Operation(summary = "데이터 수정", description = "기존 데이터를 수정합니다.")
  @PutMapping("/{id}")
  public SampleEntity update(@PathVariable Long id, @RequestBody SampleEntity updatedSample) {
    return repository.findById(id)
        .map(sample -> {
          sample.setName(updatedSample.getName());
          sample.setCreatedBy(updatedSample.getCreatedBy());
          sample.setCreatedDate(updatedSample.getCreatedDate());
          sample.setStatus(updatedSample.getStatus());
          sample.setDescription(updatedSample.getDescription());
          sample.setCategory(updatedSample.getCategory());
          return repository.save(sample);
        })
        .orElseGet(() -> {
          updatedSample.setId(id);
          return repository.save(updatedSample);
        });
  }

  @Operation(summary = "데이터 삭제", description = "특정 id의 데이터를 삭제합니다.")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    repository.deleteById(id);
  }
}

