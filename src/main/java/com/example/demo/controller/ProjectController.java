package com.example.demo.controller;


import com.example.demo.model.Developer;
import com.example.demo.model.Message;
import com.example.demo.model.Project;
import com.example.demo.service.MessageService;
import com.example.demo.service.ProjectService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final MessageService messageService;

    public ProjectController(ProjectService projectService, UserService userService, MessageService messageService) {
        this.projectService = projectService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/")
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.findAll());
        Developer developer = userService.findCurrent();
        if (developer == null) {
            return "redirect:/login";
        }
        developer.setName("You logged as (" + developer.getName() + ")");
        model.addAttribute("user", developer);
        model.addAttribute("messages", messageService.findAll());
        model.addAttribute("message", new Message());
        return "projects";
    }

    @GetMapping("/projects")
    public String addProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "project-manager";
    }

    @GetMapping("/tasks")
    public String addTasksForm(Model model) {
        model.addAttribute("project", new Project());
        return "tasks";
    }

    @PostMapping("/add")
    public String saveProject(@ModelAttribute Project project) {
        projectService.save(project);
        return "redirect:/";
    }

    @PostMapping("/add_task")
    public String saveTask(@ModelAttribute Project project) {
        String tasks = project.getTasks();
        project = projectService.findByName(project.getName());
        if (project != null) {
            tasks = project.getTasks() + tasks + "; ";
            project.setTasks(tasks);
            projectService.save(project);
            return "redirect:/";
        } else {
            return "redirect:/tasks";
        }
    }

    @PostMapping("/finish_task")
    public String finishTask(@ModelAttribute Project project) {
        String completed_tasks = project.getTasks() + "; ";
        System.out.println(completed_tasks);
        project = projectService.findByName(project.getName());
        if (project != null) {
            String tasks = project.getTasks().replace(completed_tasks, "");
            completed_tasks = project.getFinished_tasks() + completed_tasks;
            project.setTasks(tasks);
            project.setFinished_tasks(completed_tasks);
            projectService.save(project);
            return "redirect:/";
        }
        return "redirect:/tasks";
    }
}
