package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "task.exchange";
    private static final String ROUTING_KEY = "task.created";

    @CacheEvict(value = "tasks", allEntries = true)
    public Task create(Task task) {
        task.setCreateAt(LocalDateTime.now());
        task.setCompleted(false);
        Task saved = taskRepository.save(task);

        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, "Nova tarefa criada: " + saved.getTitle());

        return saved;
    }

    @Cacheable(value = "tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @CacheEvict(value = "tasks" , allEntries = true)
    public Task complete(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompleted(true);

        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, "Tarefa completa: " +  task.getTitle());

        return taskRepository.save(task);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void delete(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
