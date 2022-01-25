package io.agile.ppmtool.repositories;

import io.agile.ppmtool.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllById(List<Long> ids);
}
