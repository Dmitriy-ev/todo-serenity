import data.Priority
import data.Task
import data.TasksRepositoryMemory
import net.serenitybdd.junit5.SerenityJUnit5Extension
import net.thucydides.core.annotations.Step
import net.thucydides.core.annotations.Steps
import net.thucydides.core.steps.ScenarioSteps
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(SerenityJUnit5Extension::class)
class SerenityTest {

    @Steps
    private lateinit var actionSteps: ActionSteps

    @Test
    fun `get all task`() {
        actionSteps.checkTask(5)
    }

    @Test
    fun `get task with false parameter`() {
        actionSteps.checkTaskWithFalseParameter(3)
    }

    @Test
    fun `add task`() {
        val newTask = Task(6, "Task6", Priority.LOW)
        actionSteps.apply {
            addTask(newTask)
            checkAddTask(newTask)
        }
    }

    @Test
    fun `delete task`() {
        actionSteps.apply {
            checkTask(5)
            deleteTask(4)
            checkTask(4)
        }
    }

    @Test
    fun `complete task`() {
        actionSteps.apply {
            checkTaskWithFalseParameter(3)
            completeTask(2)
            checkTaskWithFalseParameter(2)
        }
    }

    @Test
    fun `uncomplete task`() {
        actionSteps.apply {
            checkTaskWithFalseParameter(3)
            uncompleteTask(1)
            checkTaskWithFalseParameter(4)
        }
    }
}

open class ActionSteps : ScenarioSteps() {

    private var repository = TasksRepositoryMemory().apply {
        addTask(Task(1, "Task1", Priority.HIGH, true))
        addTask(Task(2, "Task2", Priority.LOW))
        addTask(Task(3, "Task3", Priority.MEDIUM, true))
        addTask(Task(4, "Task4", Priority.HIGH))
        addTask(Task(5, "Task5", Priority.MEDIUM))
    }

    @Step
    open fun checkTask(taskId: Int) {
        assertEquals(taskId, repository.getTasks().size)
    }

    @Step
    open fun checkTaskWithFalseParameter(taskId: Int) {
        assertEquals(taskId, repository.getTasks(false).size)
    }

    @Step
    open fun addTask(task: Task) {
        repository.addTask(task)
    }

    @Step
    open fun deleteTask(taskId: Int) {
        repository.deleteTask(taskId)
    }

    @Step
    open fun completeTask(taskId: Int) {
        repository.completeTask(taskId)
    }

    @Step
    open fun uncompleteTask(taskId: Int) {
        repository.uncompleteTask(taskId)
    }

    @Step
    open fun checkAddTask(task: Task) {
        assertEquals(task, repository.getTasks().first { it.id == 6 })
    }
}
