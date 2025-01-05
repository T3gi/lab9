package com.example.demo.service;


import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findByName(String name) {
        for (Project project : projectRepository.findAll()) {
            if (project.getName().equals(name)) {
                return project;
            }
        }
        return null;
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }
}
