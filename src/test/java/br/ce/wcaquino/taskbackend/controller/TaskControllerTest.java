package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;
import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.*;
import org.mockito.*;

public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;
    
    @InjectMocks
    private TaskController controller;

    @Before
    public void setupe(){
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void naoDeveSalvarTarefaSemDescricao() {
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());
        try {
            controller.save(todo);
            Assert.fail("Erro: Não deveria chegar nesse ponto");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the task description",e.getMessage());
        }
        
    }

    @Test
    public void naoDeveSalvarTarefaSemdata(){
        Task todo = new Task();
        todo.setTask("task");
        try {
            controller.save(todo);
            Assert.fail("Erro: Não deveria chegar nesse ponto");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the due date",e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada(){
        Task todo = new Task();
        todo.setTask("task");
        todo.setDueDate(LocalDate.of(2010, 01, 01));
        try {
            controller.save(todo);
            Assert.fail("Erro: Não deveria chegar nesse ponto");
        } catch (ValidationException e) {
            Assert.assertEquals("Due date must not be in past",e.getMessage());
        }
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws ValidationException {
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());
        todo.setTask("task");
        controller.save(todo);
        Mockito.verify(taskRepo).save(todo);
    }

}