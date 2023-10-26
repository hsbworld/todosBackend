package com.husqvarna.todoBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.husqvarna.todoBackend.models.Todos;

public interface TodosRepository extends JpaRepository<Todos, Long> {
}
