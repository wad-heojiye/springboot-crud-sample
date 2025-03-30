package com.wadcorp.spring.sample.crud.config;

import com.wadcorp.spring.sample.crud.repository.SampleEntityRepository;
import com.wadcorp.spring.sample.crud.entity.SampleEntity;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class SampleConfig {

  @Bean
  CommandLineRunner loadSampleData(SampleEntityRepository repository) {
    return args -> {
      Random random = new Random();

      String[] statuses = {"활성", "비활성", "삭제"};
      String[] employees = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank"};
      String[] categories = {"Category A", "Category B", "Category C"};

      for (int i = 1; i <= 500; i++) {
        String name = "Item " + i;
        String createdBy = employees[random.nextInt(employees.length)];
        // 최근 30일 이내의 랜덤 생성 날짜
        LocalDateTime createdDate = LocalDateTime.now().minusDays(random.nextInt(30));
        String status = statuses[random.nextInt(statuses.length)];
        String description = "Description for " + name;
        String category = categories[random.nextInt(categories.length)];

        SampleEntity entity = new SampleEntity(null, name, createdBy, createdDate, status, description, category);

        repository.save(entity);
      }
    };
  }
}
